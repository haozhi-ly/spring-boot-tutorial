/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package com.ly.session;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.VertxContextPRNG;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.AbstractSession;
import io.vertx.ext.web.sstore.SessionStore;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class RedisSessionStore implements SessionStore {

    /**
     * Default of how often, in ms, to check for expired sessions
     */
    private static final long DEFAULT_REAPER_INTERVAL = 1000;

    /**
     * Default name for map used to store sessions
     */
    private static final String DEFAULT_SESSION_MAP_NAME = "vertx-web.sessions";


    private VertxContextPRNG random;


    protected Vertx vertx;

    private SaveMode saveMode = SaveMode.ON_IMMEDIATE;

    public void setSaveMode(SaveMode saveMode) {
        this.saveMode = saveMode;
    }

    private RedisTemplate<String,Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Session createSession(long timeout) {
        return new RedisDataSessionImpl(random, timeout, DEFAULT_SESSIONID_LENGTH);
    }

    @Override
    public Session createSession(long timeout, int length) {
        return new RedisDataSessionImpl(random, timeout, length);
    }

    @Override
    public SessionStore init(Vertx vertx, JsonObject options) {
        // initialize a secure random
        this.random = VertxContextPRNG.current(vertx);
        this.vertx = vertx;

        return this;
    }

    @Override
    public long retryTimeout() {
        return 0;
    }

    @Override
    public void get(String id, Handler<AsyncResult<Session>> resultHandler) {
       Map<Object,Object> sessionInfoMap = redisTemplate.boundHashOps(id).entries();
        if(sessionInfoMap != null && !sessionInfoMap.isEmpty()){
            RedisDataSessionImpl redisDataSession = new RedisDataSessionImpl(sessionInfoMap);
            resultHandler.handle(Future.succeededFuture(redisDataSession));
        }else{
            resultHandler.handle(Future.succeededFuture());
        }
    }

    @Override
    public void delete(String id, Handler<AsyncResult<Void>> resultHandler) {
        redisTemplate.delete(id);
        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void put(Session session, Handler<AsyncResult<Void>> resultHandler) {

        Map<Object,Object> sessionMap =  ((RedisDataSessionImpl)session).writeToMap();

        redisTemplate.boundHashOps(session.id()).putAll(sessionMap);
        redisTemplate.expire(session.id(),session.timeout(),TimeUnit.SECONDS);

        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void clear(Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void size(Handler<AsyncResult<Integer>> resultHandler) {
    }

    @Override
    public synchronized void close() {

    }



    public class RedisDataSessionImpl extends AbstractSession {
        private final String ID_FIELD_NAME= "id";

        private final String EXPIRED_NAME_TIME_NAME = "expiredAtTime";

        private final String LAST_ACCESS_TIME_NAME = "lastAccessTime";

        private final String CREATE_TIME_NAME = "createTime";
        /**
         * 其他session 属性 key的前缀
         */
        private final String ATTR_PREFIX = "attr_";
        private final String TIME_OUT = "timeout";

        /**
         * 保存session中更改的属性
         */
        private Map<String, Object> deltaMap = new HashMap<>();

        public RedisDataSessionImpl(Map<Object, Object> sessionInfoMap) {
            super();
            Map<String,Object> data = new HashMap<>();
            for(Map.Entry<Object,Object> entry:sessionInfoMap.entrySet()){
                String key = (String) entry.getKey();
                Object val = entry.getValue();
                if(key.equals(ID_FIELD_NAME)){
                    setId((String) val);
                }else if(key.equals(TIME_OUT)){
                    setTimeout((Long) val);
                }else if(key.startsWith(ATTR_PREFIX)){
                    data.put(key.substring(ATTR_PREFIX.length()),val);
                }
            }
            deltaMap.put(LAST_ACCESS_TIME_NAME,System.currentTimeMillis());
            setLastAccessed(System.currentTimeMillis());
            setData(data);
        }


        @Override
        protected void setId(String id) {
            super.setId(id);
        }

        @Override
        protected void setData(Map<String, Object> data) {
            super.setData(data);
        }

        @Override
        protected void setData(JsonObject data) {
            super.setData(data);
        }

        /**
         * Important note: This constructor (even though not referenced anywhere) is required for serialization purposes. Do
         * not remove.
         */
        public RedisDataSessionImpl() {
            super();
        }

        public RedisDataSessionImpl(VertxContextPRNG random) {
            super(random);
        }

        public RedisDataSessionImpl(VertxContextPRNG random, long timeout, int length) {
            super(random, timeout, length);
        }

        @Override
        public <T> T get(String key) {
            return super.get(key);
        }

        /**
         * put 是否立即刷新到redis中
         *
         * @param key
         * @param obj
         * @return
         */
        @Override
        public Session put(String key, Object obj) {
            Session result = super.put(key, obj);
            putAndFlush(key,obj);
            return result;
        }

        void putAndFlush(String key,Object val){
            deltaMap.put(ATTR_PREFIX+key, val);
            saveIfRequired();
        }

        private void saveIfRequired() {
            if (saveMode.equals(SaveMode.ON_IMMEDIATE)){
                redisTemplate.boundHashOps(this.id()).putAll(deltaMap);
                deltaMap = new HashMap<>();
            }
        }

        @Override
        public Session putIfAbsent(String key, Object obj) {
            return super.putIfAbsent(key, obj);
        }

        @Override
        public Session computeIfAbsent(String key, Function<String, Object> mappingFunction) {
            return super.computeIfAbsent(key, mappingFunction);
        }

        @Override
        public <T> T remove(String key) {
            putAndFlush(key,null);
            return super.remove(key);
        }


        public void writeToSessionEntity(SessionEntity sessionEntity) {
            // 需要对id 做hmac签名
            sessionEntity.setId(id());
            sessionEntity.setTimeOut(timeout());
            sessionEntity.setLastAccessed(lastAccessed());
            sessionEntity.setVersion(version());
            sessionEntity.setData(this.data());
        }

        public void readFromSessionEntity(SessionEntity sessionEntity) {
            // 需要对id 做hmac签名
            this.setId(sessionEntity.getId());
            setTimeout(sessionEntity.getTimeOut());
            setLastAccessed(sessionEntity.getLastAccessed());
            setVersion(sessionEntity.getVersion());
            setData(sessionEntity.getData());
        }

        public Map<Object, Object> writeToMap() {
            Map<Object,Object> sessionMap = new HashMap<>();
            sessionMap.put(ID_FIELD_NAME,id());
            sessionMap.put(LAST_ACCESS_TIME_NAME,lastAccessed());
            sessionMap.put(TIME_OUT,timeout());

            Map<String,Object> data = data();
            for(Map.Entry<String,Object> entry:data.entrySet()){
                sessionMap.put(ATTR_PREFIX+entry.getKey(),entry.getValue());
            }
            return sessionMap;
        }
    }
}
enum SaveMode{
    ON_IMMEDIATE,
    ON_SAVE
}
