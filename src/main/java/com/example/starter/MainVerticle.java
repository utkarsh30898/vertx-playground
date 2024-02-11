package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true)
                .setInstances(8);
        vertx.deployVerticle("com.example.starter.HelloVertice", options);
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloNameVertx);

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    public void helloVertx (RoutingContext context) {
        vertx.eventBus().request("hello.vertx.addr", "", reply -> {
           context.request().response().end((String) reply.result().body());
        });
    }
    public void helloNameVertx (RoutingContext context) {
        String name = context.pathParam("name");
        vertx.eventBus().request("hello.vertx.addr", name, reply -> {
           context.request().response().end((String) reply.result().body());
        });
    }
}
