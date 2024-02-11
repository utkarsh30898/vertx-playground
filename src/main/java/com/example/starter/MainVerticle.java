package com.example.starter;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) {
        vertx.deployVerticle(new HelloVertice());
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloNameVertx);

        // We want a config file in format of json and path to it is config.json
        ConfigStoreOptions configStoreOptions = new ConfigStoreOptions()
                        .setType("file")
                        .setFormat("json")
                        .setConfig(new JsonObject().put("path", "config.json"));
        // we can have multiple config stores, and we can load multiple config store to config retriever
        // but the first one loaded will be the base configuration and every subsequent one will override
        // the matching parameters
        ConfigRetrieverOptions configRetrieverOptions = new ConfigRetrieverOptions()
                .addStore(configStoreOptions);

        ConfigRetriever configRetriever = ConfigRetriever.create(vertx, configRetrieverOptions);
        configRetriever.getConfig(asyncResult -> {  // start method is going to go through here & this is async method
            // so this may or may not immediately execute
            // so declare Promise in start method argument
            if (asyncResult.succeeded()) {
                JsonObject config = asyncResult.result();
                JsonObject http = config.getJsonObject("http");
                Integer httpPort = http.getInteger("port");
                vertx.createHttpServer().requestHandler(router).listen(httpPort);
                promise.complete();
            } else {
                promise.fail("Unable to load configuration");
            }
        });
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
