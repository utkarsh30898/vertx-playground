package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(ctx -> {
            ctx.request().response().end("Hello World form vert.x");
        });
        router.get("/api/v1/hello/:name").handler(ctx -> {
            String name = ctx.pathParam("name");
            ctx.request().response()
                    .end(String.format("Hello vert.x world from %s", name));
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
