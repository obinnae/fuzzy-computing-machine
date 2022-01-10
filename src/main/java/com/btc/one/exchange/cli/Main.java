package com.btc.one.exchange.cli;

import com.btc.one.exchange.ExchangeListener;
import com.btc.one.exchange.Subscription;
import com.btc.one.exchange.order.OrderBook;
import io.vertx.core.Vertx;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        String product = args.length > 0 ? args[0] : "ETH-USD";

        Map<String, OrderBook> orderBooks = new HashMap<>();

        ExchangeListener exchangeListener = new ExchangeListener(new Subscription());
        vertx.deployVerticle(exchangeListener, ar -> {
            if (ar.failed()) {
                log.severe("Error while deploying exchange listener");
                log.severe(ar.cause().getMessage());
                vertx.close();
                System.exit(-1);
            }
            else {
                log.info(String.format("Successfully deployed exchange listener. Deployment ID: %s", ar.result()));
                OrderBook orderBook = new OrderBook();
                orderBooks.put(product, orderBook);
                exchangeListener.listen(Collections.singletonList(product), order -> {
                    orderBook.newOrder(order);
                    System.out.println(orderBook);
                });
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(vertx::close));
    }
}
