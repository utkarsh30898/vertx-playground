package com.example.starter;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;

import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(new HelloVertice());
        Router router = Router.router(vertx);
        router.get("/api/v1/hello").handler(this::helloVertx);
        router.get("/api/v1/hello/:name").handler(this::helloNameVertx);
        router.route().handler(StaticHandler.create("staticContent"));

//        vertx.executeBlocking(promise -> {
//            try {
//                //code
//                promise.complete();
//            } catch (Exception e) {
//                promise.fail(e);
//            }
//        }, result -> {
//            if ((result.failed())) { // used this instead of result.succeded() as startPromise also used in below code
//                startPromise.fail(result.cause());
//            }
//        });
// modularizing the above code below

//        Handler<AsyncResult<Void>> doBlockingCode = result -> this.handleResult(startPromise, result);
//        vertx.executeBlocking(this::doBlockingCode, doBlockingCode);

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
                startPromise.complete();
            } else {
                startPromise.fail("Unable to load configuration");
            }
        });
    }

//    void handleResult(Promise<Void> start, AsyncResult<Void> result) {
//        if (result.failed()) {
//            start.fail(result.cause());
//        }
//    }
//    void doBlockingCode(Promise<Void> promise) {
//        try {
//                //code
//                promise.complete();
//            } catch (Exception e) {
//                promise.fail(e);
//            }
//    }

//    Future<HttpServer> startHttpServer(Router router) {
//        JsonObject http = config().getJsonObject("http");
//        int port = http.getInteger("port");
////        vertx.createHttpServer().requestHandler(router).listen(port);
//        HttpServer server = vertx.createHttpServer().requestHandler(router);
//
//        //when we go to server actually listening we're passing in port & promise that will get resolved
//        // as the async handler...So when server is succesfully up & listening it'll successfully resolve the
//        //promise and we are returning the result promise wrapped in a future.
//        return Future.<HttpServer>future(promise -> server.listen(port, promise));
//    }


//    Future<Void> deployOtherVerticles(HttpServer server) {
//        Future<String> helloA = Future.future(promise -> vertx.deployVerticle("Hello.A", promise));
//        Future<String> helloB = Future.future(promise -> vertx.deployVerticle("Hello.B", promise));
//
//        // have two futures and wanna make sure that both of them are done before we move on two the next step
//        // use CompositeFuture will help you to resolve any/all of them
//
//        return CompositeFuture.all(helloA, helloB).mapEmpty();
            // it is gonna deploy them in parallel
//    }

    public void helloVertx (RoutingContext context) {
        vertx.eventBus().request("hello.vertx.addr", "", reply -> {
           context.request().response().end((String) reply.result().body());
        });
    }
    public void helloNameVertx (RoutingContext context) {
        String name = context.pathParam("name");
        vertx.eventBus().request("hello.named.addr", name, reply -> {
           context.request().response().end((String) reply.result().body());
        });
    }
}
