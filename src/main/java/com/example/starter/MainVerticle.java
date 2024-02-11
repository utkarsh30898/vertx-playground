package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloNameVertx);

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    public void helloVertx (RoutingContext context) {
        context.request().response().end("Hello World form vert.x");
    }
    public void helloNameVertx (RoutingContext context) {
        String name = context.pathParam("name");
        context.request().response().end(String.format("Hello vert.x world from %s", name));
    }
}
