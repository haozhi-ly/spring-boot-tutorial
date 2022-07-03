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

import com.ly.ProtoStuffUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import io.vertx.ext.auth.VertxContextPRNG;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.impl.Utils;
import io.vertx.ext.web.sstore.AbstractSession;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.sstore.redis.RedisSessionStore;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static io.vertx.redis.client.Command.*;
import static io.vertx.redis.client.Request.cmd;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class ReactiveRedisSessionStore implements SessionStore {
    private Redis redis;
    private VertxContextPRNG random;
    private long retryTimeout;


    @Override
    public SessionStore init(Vertx vertx, JsonObject options) {
        Objects.requireNonNull(options, "options are required");
        long timeout = options.getLong("retryTimeout", RedisSessionStore.DEFAULT_RETRY_TIMEOUT_MS);
        Redis redis = Redis.createClient(vertx, new RedisOptions(options));
        return init(vertx, timeout, redis);
    }

    public SessionStore init(Vertx vertx, long retryTimeout, Redis redis) {
        random = VertxContextPRNG.current(vertx);
        this.retryTimeout = retryTimeout;
        this.redis = Objects.requireNonNull(redis, "redis is required");
        return this;
    }

    @Override
    public long retryTimeout() {
        return retryTimeout;
    }

    @Override
    public Session createSession(long timeout) {
        return createSession(timeout, DEFAULT_SESSIONID_LENGTH);
    }


    @Override
    public Session createSession(long timeout, int length) {
        return new RedisShardDataSessionImpl(random, timeout, length);
    }

    public ReactiveRedisSessionStore() {
        super();
    }

    @Override
    public void get(String id, Handler<AsyncResult<Session>> resultHandler) {
        redis.send(cmd(GET).arg(id), resGet -> {
            if (resGet.failed()) {
                resultHandler.handle(Future.failedFuture(resGet.cause()));
                return;
            }

            Response response = resGet.result();
            if (response != null) {
                RedisShardDataSessionImpl session = new RedisShardDataSessionImpl(random);
                session.readFromBuffer(0, response.toBuffer());
                // postpone expiration time, this cannot be done in a single frame with GET cmd
//                redis.send(cmd(PEXPIRE).arg(id).arg(session.timeout()), resExpire -> {
//                    if (resExpire.failed()) {
//                        resultHandler.handle(Future.failedFuture(resExpire.cause()));
//                    } else {
//                        resultHandler.handle(Future.succeededFuture(session));
//                    }
//                });
                resultHandler.handle(Future.succeededFuture(session));
            } else {
                resultHandler.handle(Future.succeededFuture());
            }
        });
    }

    @Override
    public void delete(String id, Handler<AsyncResult<Void>> resultHandler) {
        redis.send(cmd(DEL).arg(id), res -> {
            if (res.failed()) {
                resultHandler.handle(Future.failedFuture(res.cause()));
            } else {
                resultHandler.handle(Future.succeededFuture());
            }
        });
    }

    @Override
    public void put(Session session, Handler<AsyncResult<Void>> resultHandler) {
        Buffer buffer = Buffer.buffer();
        RedisShardDataSessionImpl sessionImpl = (RedisShardDataSessionImpl) session;
        sessionImpl.writeToBuffer(buffer);

        // submit with all session data & expiration TO in ms
        Request rq = cmd(SET)
                .arg(session.id()).arg(buffer)
                .arg("PX").arg(session.timeout());

        redis.send(rq, res -> {
            if (res.failed()) {
                resultHandler.handle(Future.failedFuture(res.cause()));
            } else {
                resultHandler.handle(Future.succeededFuture());
            }
        });
    }


    @Override
    public void clear(Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void size(Handler<AsyncResult<Integer>> resultHandler) {

    }

    @Override
    public void close() {

    }

    public static class RedisShardDataSessionImpl extends AbstractSession{
        private static final Charset UTF8 = StandardCharsets.UTF_8;

        private static final byte TYPE_LONG = 1;
        private static final byte TYPE_INT = 2;
        private static final byte TYPE_SHORT = 3;
        private static final byte TYPE_BYTE = 4;
        private static final byte TYPE_DOUBLE = 5;
        private static final byte TYPE_FLOAT = 6;
        private static final byte TYPE_CHAR = 7;
        private static final byte TYPE_BOOLEAN = 8;
        private static final byte TYPE_STRING = 9;
        private static final byte TYPE_BUFFER = 10;
        private static final byte TYPE_BYTES = 11;
        private static final byte TYPE_CLUSTER_SERIALIZABLE = 13;
        private static final byte TYPE_PB_SERIALIZABLE = 14;

        /**
         * Important note: This constructor (even though not referenced anywhere) is required for serialization purposes. Do
         * not remove.
         */
        public RedisShardDataSessionImpl() {
            super();
        }

        public RedisShardDataSessionImpl(VertxContextPRNG random) {
            super(random);
        }

        public RedisShardDataSessionImpl(VertxContextPRNG random, long timeout, int length) {
            super(random, timeout, length);
        }

        public void writeToBuffer(Buffer buff) {
            byte[] bytes = id().getBytes(UTF8);
            buff.appendInt(bytes.length).appendBytes(bytes);
            buff.appendLong(timeout());
            buff.appendLong(lastAccessed());
            buff.appendInt(version());
            // use cache
            Buffer dataBuf = writeDataToBuffer();
            buff.appendBuffer(dataBuf);
        }

        public int readFromBuffer(int pos, Buffer buffer) {
            int len = buffer.getInt(pos);
            pos += 4;
            byte[] bytes = buffer.getBytes(pos, pos + len);
            pos += len;
            setId(new String(bytes, UTF8));
            setTimeout(buffer.getLong(pos));
            pos += 8;
            setLastAccessed(buffer.getLong(pos));
            pos += 8;
            setVersion(buffer.getInt(pos));
            pos += 4;
            pos = readDataFromBuffer(pos, buffer);
            return pos;
        }

        private Buffer writeDataToBuffer() {
            Buffer buffer = Buffer.buffer();
            if (isEmpty()) {
                buffer.appendInt(0);
            } else {
                final Map<String, Object> data = data();
                buffer.appendInt(data.size());
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String key = entry.getKey();
                    byte[] keyBytes = key.getBytes(UTF8);
                    buffer.appendInt(keyBytes.length).appendBytes(keyBytes);
                    Object val = entry.getValue();
                    if (val instanceof Long) {
                        buffer.appendByte(TYPE_LONG).appendLong((long) val);
                    } else if (val instanceof Integer) {
                        buffer.appendByte(TYPE_INT).appendInt((int) val);
                    } else if (val instanceof Short) {
                        buffer.appendByte(TYPE_SHORT).appendShort((short) val);
                    } else if (val instanceof Byte) {
                        buffer.appendByte(TYPE_BYTE).appendByte((byte) val);
                    } else if (val instanceof Double) {
                        buffer.appendByte(TYPE_DOUBLE).appendDouble((double) val);
                    } else if (val instanceof Float) {
                        buffer.appendByte(TYPE_FLOAT).appendFloat((float) val);
                    } else if (val instanceof Character) {
                        buffer.appendByte(TYPE_CHAR).appendShort((short) ((Character) val).charValue());
                    } else if (val instanceof Boolean) {
                        buffer.appendByte(TYPE_BOOLEAN).appendByte((byte) ((boolean) val ? 1 : 0));
                    } else if (val instanceof String) {
                        byte[] bytes = ((String) val).getBytes(UTF8);
                        buffer.appendByte(TYPE_STRING).appendInt(bytes.length).appendBytes(bytes);
                    } else if (val instanceof Buffer) {
                        Buffer buff = (Buffer) val;
                        buffer.appendByte(TYPE_BUFFER).appendInt(buff.length()).appendBuffer(buff);
                    } else if (val instanceof byte[]) {
                        byte[] bytes = (byte[]) val;
                        buffer.appendByte(TYPE_BYTES).appendInt(bytes.length).appendBytes(bytes);
                    } else if (val instanceof ClusterSerializable) {
                        buffer.appendByte(TYPE_CLUSTER_SERIALIZABLE);
                        String className = val.getClass().getName();
                        byte[] classNameBytes = className.getBytes(UTF8);
                        buffer.appendInt(classNameBytes.length).appendBytes(classNameBytes);
                        ((ClusterSerializable) val).writeToBuffer(buffer);
                    } else {
                        // 默认序列化成pb
                        buffer.appendByte(TYPE_PB_SERIALIZABLE);
                        String className = val.getClass().getName();
                        byte[] classNameBytes = className.getBytes(UTF8);
                        buffer.appendInt(classNameBytes.length).appendBytes(classNameBytes);
                        byte[] serializedBytes = ProtoStuffUtil.serializer(val);
                        buffer.appendInt(serializedBytes.length).appendBytes(serializedBytes);
                    }
                }
            }
            return buffer;
        }

        private int readDataFromBuffer(int pos, Buffer buffer) {
            try {
                int entries = buffer.getInt(pos);
                pos += 4;
                if (entries > 0) {
                    final Map<String, Object> data = new ConcurrentHashMap<>(entries);

                    for (int i = 0; i < entries; i++) {
                        int keylen = buffer.getInt(pos);
                        pos += 4;
                        byte[] keyBytes = buffer.getBytes(pos, pos + keylen);
                        pos += keylen;
                        String key = new String(keyBytes, UTF8);
                        byte type = buffer.getByte(pos++);
                        Object val;
                        switch (type) {
                            case TYPE_LONG:
                                val = buffer.getLong(pos);
                                pos += 8;
                                break;
                            case TYPE_INT:
                                val = buffer.getInt(pos);
                                pos += 4;
                                break;
                            case TYPE_SHORT:
                                val = buffer.getShort(pos);
                                pos += 2;
                                break;
                            case TYPE_BYTE:
                                val = buffer.getByte(pos);
                                pos++;
                                break;
                            case TYPE_FLOAT:
                                val = buffer.getFloat(pos);
                                pos += 4;
                                break;
                            case TYPE_DOUBLE:
                                val = buffer.getDouble(pos);
                                pos += 8;
                                break;
                            case TYPE_CHAR:
                                short s = buffer.getShort(pos);
                                pos += 2;
                                val = (char) s;
                                break;
                            case TYPE_BOOLEAN:
                                byte b = buffer.getByte(pos);
                                pos++;
                                val = b == 1;
                                break;
                            case TYPE_STRING:
                                int len = buffer.getInt(pos);
                                pos += 4;
                                byte[] bytes = buffer.getBytes(pos, pos + len);
                                val = new String(bytes, UTF8);
                                pos += len;
                                break;
                            case TYPE_BUFFER:
                                len = buffer.getInt(pos);
                                pos += 4;
                                bytes = buffer.getBytes(pos, pos + len);
                                val = Buffer.buffer(bytes);
                                pos += len;
                                break;
                            case TYPE_BYTES:
                                len = buffer.getInt(pos);
                                pos += 4;
                                val = buffer.getBytes(pos, pos + len);
                                pos += len;
                                break;
                            case TYPE_CLUSTER_SERIALIZABLE:
                                int classNameLen = buffer.getInt(pos);
                                pos += 4;
                                byte[] classNameBytes = buffer.getBytes(pos, pos + classNameLen);
                                pos += classNameLen;
                                String className = new String(classNameBytes, UTF8);
                                Class<?> clazz = Utils.getClassLoader().loadClass(className);
                                if (!ClusterSerializable.class.isAssignableFrom(clazz)) {
                                    throw new ClassCastException(new String(classNameBytes) + " is not assignable from ClusterSerializable");
                                }
                                ClusterSerializable obj = (ClusterSerializable) clazz.getDeclaredConstructor().newInstance();
                                pos = obj.readFromBuffer(pos, buffer);
                                val = obj;
                                break;
                            case TYPE_PB_SERIALIZABLE:
                                classNameLen = buffer.getInt(pos);
                                pos += 4;
                                classNameBytes = buffer.getBytes(pos, pos + classNameLen);
                                pos += classNameLen;
                                className = new String(classNameBytes, UTF8);
                                Class<?> clazzByPb = Utils.getClassLoader().loadClass(className);
                                int serializeObjLength = buffer.getInt(pos);
                                pos+=4;
                                byte[] serializeObjBytes = buffer.getBytes(pos,pos+serializeObjLength);
                                pos+=serializeObjLength;
                                val = ProtoStuffUtil.deserializerToObj(serializeObjBytes,clazzByPb);
                                break;
                            default:
                                throw new IllegalStateException("Invalid serialized type: " + type);
                        }
                        data.put(key, val);
                    }
                    setData(data);
                }
                return pos;
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new VertxException(e);
            }
        }

    }
}


