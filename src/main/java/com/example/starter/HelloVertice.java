package com.example.starter;

import io.vertx.core.AbstractVerticle;

public class HelloVertice extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("hello.vertx.addr", msg -> {
            msg.reply("Hello World form vert.x");
        });

        vertx.eventBus().consumer("hello.named.addr", msg -> {
            String name = (String) msg.body();
            msg.reply(String.format("Hello vert.x world from %s", name));
        });
    }
}
