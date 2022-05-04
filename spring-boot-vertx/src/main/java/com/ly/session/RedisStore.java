package com.ly.session;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisStore {
    /**
     * Default of how often, in ms, to check for expired sessions
     */
    long DEFAULT_REAPER_INTERVAL = 1000;

    /**
     * Default name for map used to store sessions
     */
    String DEFAULT_SESSION_MAP_NAME = "vertx-web.sessions";

    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @return the session store
     */
    static RedisSessionStore create(Vertx vertx, RedisTemplate<String,Object> redisTemplate) {
        RedisSessionStore store = new RedisSessionStore();
        store.setRedisTemplate(redisTemplate);
        store.init(vertx, new JsonObject()
                .put("reaperInterval", DEFAULT_REAPER_INTERVAL)
                .put("mapName", DEFAULT_SESSION_MAP_NAME));
        return store;
    }

    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @param sessionMapName  name for map used to store sessions
     * @return the session store
     */
    static RedisSessionStore create(Vertx vertx, String sessionMapName) {
        RedisSessionStore store = new RedisSessionStore();
        store.init(vertx, new JsonObject()
                .put("reaperInterval", DEFAULT_REAPER_INTERVAL)
                .put("mapName", sessionMapName));
        return store;
    }

    /**
     * Create a session store
     *
     * @param vertx  the Vert.x instance
     * @param sessionMapName  name for map used to store sessions
     * @param reaperInterval  how often, in ms, to check for expired sessions
     * @return the session store
     */
    static RedisSessionStore create(Vertx vertx, String sessionMapName, long reaperInterval) {
        RedisSessionStore store = new RedisSessionStore();
        store.init(vertx, new JsonObject()
                .put("reaperInterval", reaperInterval)
                .put("mapName", sessionMapName));
        return store;
    }
}
