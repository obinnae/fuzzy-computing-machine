package com.btc.one.server;

import com.btc.one.exchange.ExchangeListener;
import com.btc.one.exchange.order.OrderBook;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.*;

@Log
@RequiredArgsConstructor
public class OrderStreamerRequestHandler extends AbstractVerticle implements Handler<HttpServerRequest> {

    private static final Map<String, OrderBook> ORDER_BOOKS = new HashMap<>();
    private final ExchangeListener exchangeListener;

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        if (httpServerRequest.path().startsWith("/stream")) {
            String productsParam = httpServerRequest.getParam("products");
            List<String> products = productsParam == null ? Collections.singletonList("ETH-USD") : Arrays.asList(productsParam.split(","));

            // upgrade connection to a websocket and stream back the order book updates to the requester.
            httpServerRequest.toWebSocket(websocketUpgrade -> {
                if (websocketUpgrade.failed()) {
                    log.severe("Unable to establish web socket connection with client");
                    log.severe(websocketUpgrade.cause().getMessage());
                }
                else {
                    ServerWebSocket webSocket = websocketUpgrade.result();
                    log.info("Successfully established web socket connection with client");
                    exchangeListener.listen(products, order -> {
                        OrderBook orderBook = ORDER_BOOKS.computeIfAbsent(order.getProduct(), (product) -> new OrderBook());
                        orderBook.newOrder(order);
                        webSocket.writeTextMessage(Json.encode(ORDER_BOOKS));
                    });
                    webSocket.closeHandler(closeAction -> log.info("Web socket connection closed by client"));
                }
            });
        }
        else {
            httpServerRequest.response().setStatusCode(404).end();
        }
    }
}
