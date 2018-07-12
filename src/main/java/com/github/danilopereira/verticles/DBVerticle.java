package com.github.danilopereira.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public abstract class DBVerticle extends AbstractVerticle {
    MongoClient client;
    @Override
    public void start() {
        JsonObject config = new JsonObject()
                .put("db_name", "platform")
                .put("useOjectId", true)
                .put("connection_string", "mogodb://localhost:27017")
                .put("maxPoolSize", 100)
                .put("username", "root")
                .put("password", "root");

        client = MongoClient.createShared(Vertx.vertx(), config);
    }

    public <T extends String> T create(T t, String collectionName){
        JsonObject jsonObject = new JsonObject(Json.encode(t));

        client.insert(collectionName, jsonObject, res->{
           if(res.succeeded()){
               return res.result();
           }
        });
    }
}
