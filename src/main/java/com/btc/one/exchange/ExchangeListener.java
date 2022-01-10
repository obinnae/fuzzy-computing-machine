package com.btc.one.exchange;

import com.btc.one.exchange.message.SubscriptionRequest;
import com.btc.one.exchange.message.SubscriptionResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.WebSocket;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class ExchangeListener extends AbstractVerticle {

    private WebSocket webSocket;
    private final Subscription subscription;

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
        webSocket.textMessageHandler(update -> {
            SubscriptionResponse subscriptionResponse = SubscriptionResponse.fromString(update);
            System.out.println("subscriptionResponse = " + subscriptionResponse);
        });
        SubscriptionRequest subscriptionRequest = subscription.subscribe(product);
        webSocket.writeTextMessage(subscriptionRequest.toString());
    }
}
