package com.github.danilopereira.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class ServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Vertx.vertx().createHttpServer().requestHandler(request->{
            request.response().end();
        }).listen(8080);

    }
}
