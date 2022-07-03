package com.ly;

import com.ly.entity.Good;
import com.ly.session.SessionEntity;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.redis.RedisSessionStore;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.impl.RedisClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class VertxServer implements ApplicationListener<ContextRefreshedEvent> {
    //@Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            VertxOptions vertxOptions = new VertxOptions();
            vertxOptions.setMaxEventLoopExecuteTime(10000*60);
            vertxOptions.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);
            Vertx vertx = Vertx.vertx(vertxOptions);
            HttpServerOptions options = new HttpServerOptions();
            options.setIdleTimeout(3600);
            options.setTcpKeepAlive(true);
            HttpServer server = vertx.createHttpServer(options);
            Router router = Router.router(vertx);
            //SessionStore sessionStore = RedisStore.create(vertx,this.redisTemplate);
            RedisOptions redisOptions = new RedisOptions();
            redisOptions.addConnectionString("redis://192.168.72.8:6379");

            Redis redis = new RedisClient(vertx,redisOptions);
            //ReactiveRedisSessionStore sessionStore = RedisStore.create(vertx,redis);
            RedisSessionStore redisSessionStore = RedisSessionStore.create(vertx,redis);

            //SessionStore sessionStore = SessionStore.create(vertx);
            router.routeWithRegex("/static.*").handler(StaticHandler.create());
            router.routeWithRegex(".*service.*")
                    .handler(SessionHandler.create(redisSessionStore)).handler(ctx->{
                        if(ctx.request().path().contains("initSession")){
                            ctx.request().response().setStatusCode(401)
                            .putHeader("Content-Type","application/json").end((new JsonObject().put("msg","illegal request").encode()));
                        }else{
                            ctx.next();
                        }
            }).handler(ctx->{
                HttpServerResponse response = ctx.response();
                Session session =ctx.session();
                if(session.get("wow") ==null){
                    session.put("wow","wow");
                    System.err.println((String) session.get("wow"));
                    ctx.request().remoteAddress();
                    List<Good> goodList = new ArrayList<>();
                    goodList.add(Good.builder().id(1).stockNumber(100).build());
                    session.put("goods",goodList);
                }else{
                    System.out.println("goods:"+session.get("goods"));
                }
                SessionEntity sessionEntity = new SessionEntity();
                sessionEntity.setId("28947208947");
                session.put("typeTest",sessionEntity);
                String path = ctx.request().path();
                if(ctx.request().path().contains("service/removeCookie")){


                    Cookie cookie = Cookie.cookie("vertx-web.session",new Date().getTime()+"");

                    cookie.setPath("/");
                    cookie.setSameSite(CookieSameSite.NONE);
                    ctx.addCookie(cookie);
                    ctx.request().response().addCookie(cookie);

                }else if(path.contains("clearSession")){
                    ctx.session().destroy();
                }


                response.putHeader("content-type","text/plain");
                response.end("hello word!");
            }).failureHandler((ctx)->{
                System.out.println(ctx.failure());
            });
            server.requestHandler(router).listen(8081);
            System.err.println("vertxServer start success");
        }
    }
}
