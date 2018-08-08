package com.github.danilopereira.verticles;

import com.github.danilopereira.entities.TravelCompany;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.stream.Collectors;

public class TravelCompanyVerticle extends AbstractVerticle {

    private JDBCClient mysqlClient;

    public TravelCompanyVerticle() {
        JsonObject mysqlClientConfig = new JsonObject()
                .put("url", "jdbc:mysql://local.clickbus.com/vertx")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("user", "root")
                .put("password", "root")
                .put("max_poll_size", 10);
        mysqlClient = JDBCClient.createNonShared(Vertx.vertx(), mysqlClientConfig);
    }

    @Override
    public void start() throws Exception {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        router.get("/api/travelcompanies").handler(this::getAll);
        router.get("/api/travelcompany/:id").handler(this::getById);
        router.post("/api/travelcompany").handler(this::createOne);
        server.requestHandler(router::accept).listen(8089);
    }

    private void createOne(RoutingContext routingContext) {

    }

    private void getById(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            final Integer idAsInteger = Integer.valueOf(id);
            mysqlClient.getConnection(ar -> {
                if (ar.succeeded()) {
                    SQLConnection connection = ar.result();
                    JsonArray params = new JsonArray().add(idAsInteger);
                    connection.queryWithParams("select * from travel_company where id = ?", params, result -> {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .end(Json.encodePrettily(result.result().getRows().stream().map(TravelCompany::new).findFirst().get()));
                        connection.close();
                    });
                }
            });
        }

    }

    private void getAll(RoutingContext routingContext) {
        mysqlClient.getConnection(ar -> {
            if (ar.succeeded()) {
                SQLConnection connection = ar.result();
                connection.query("select * from travel_company", result -> {
                    List<TravelCompany> travelCompanies = result.result().getRows().stream().map(TravelCompany::new).collect(Collectors.toList());
                    HttpServerResponse response = routingContext.response();
                    response.putHeader("content-type", "application/json");
                    response.end(Json.encodePrettily(travelCompanies));
                    connection.close();
                });
            } else {
                System.out.println("Errouu!!");
            }
        });
    }
}
