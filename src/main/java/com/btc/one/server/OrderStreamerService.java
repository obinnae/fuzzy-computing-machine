package com.btc.one.server;

import com.btc.one.exchange.ExchangeListener;
import com.btc.one.exchange.Subscription;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class OrderStreamerService extends AbstractVerticle {

    private final Vertx vertx;
    private final ExchangeListener exchangeListener;
    private final OrderStreamerRequestHandler orderStreamerRequestHandler;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Future<HttpServer> verticleDeployment = vertx.deployVerticle(exchangeListener)
                .compose(st -> vertx.deployVerticle(orderStreamerRequestHandler))
                .compose(st -> {
                    HttpServer httpServer = vertx.createHttpServer();
                    httpServer.requestHandler(orderStreamerRequestHandler);
                    return httpServer.listen(9090);
                });

        verticleDeployment.onComplete(ar -> {
            log.info(String.format("Deployed server dependencies. Listening on port number %d", ar.result().actualPort()));
            startPromise.complete();
        });
        verticleDeployment.onFailure(ar -> {
            log.severe("Error while deploying a server dependency");
            log.severe(ar.getMessage());
            startPromise.fail(ar);
        });
    }

    void deployServer() {
        vertx.deployVerticle(this, ar -> {
            if (ar.failed()) {
                log.severe("Error while deploying server");
                log.severe(ar.cause().getMessage());
                vertx.close();
                System.exit(-1);
            }
            else {
                log.info(String.format("Successfully deployed server. Deployment ID: %s", ar.result()));
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ExchangeListener exchangeListener = new ExchangeListener(new Subscription());
        OrderStreamerRequestHandler orderStreamerRequestHandler = new OrderStreamerRequestHandler(exchangeListener);
        OrderStreamerService orderStreamerService = new OrderStreamerService(vertx, exchangeListener, orderStreamerRequestHandler);
        orderStreamerService.deployServer();

        Runtime.getRuntime().addShutdownHook(new Thread(vertx::close));
    }
}
