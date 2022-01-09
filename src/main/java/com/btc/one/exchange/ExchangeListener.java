package com.btc.one.exchange;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.WebSocket;
import lombok.extern.java.Log;

@Log
public class ExchangeListener extends AbstractVerticle {

    private WebSocket webSocket;

    @Override
    public void start(Promise<Void> startPromise) {
        HttpClientOptions httpClientOptions = new HttpClientOptions().setMaxWebSocketFrameSize(65536 * 100);
        HttpClient httpClient = vertx.createHttpClient(httpClientOptions);
        httpClient.webSocketAbs("wss://ws-feed.exchange.coinbase.com", null, null, null, ar -> {
            if (ar.failed()) {
                log.severe("Unable to connect to the exchange");
                log.severe(ar.cause().getMessage());
                startPromise.fail(ar.cause());
            } else {
                webSocket = ar.result();
                try {
                    super.start(startPromise);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void listen(String product, Handler<Order> newOrderHandler) {
        webSocket.textMessageHandler(System.out::println);
        webSocket.writeTextMessage("{\"type\":\"subscribe\",\"product_ids\":[\"ETH-USD\",\"ETH-EUR\"],\"channels\":[\"level2\"]}");
    }
}
