package com.btc.one.exchange;

import io.vertx.core.Vertx;
import lombok.extern.java.Log;

@Log
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ExchangeListener exchangeListener = new ExchangeListener();
        vertx.deployVerticle(exchangeListener, ar -> {
            if (ar.failed()) {
                log.severe("Error while deploying exchange listener");
                log.severe(ar.cause().getMessage());
                vertx.close();
                System.exit(-1);
            }
            else {
                log.info(String.format("Successfully deployed exchange listener. Deployment ID: %s", ar.result()));
                exchangeListener.listen("", null);
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(vertx::close));
    }
}
