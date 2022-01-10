package com.btc.one.exchange;

import com.btc.one.exchange.message.SubscriptionRequest;

import java.util.HashSet;
import java.util.Set;

public class Subscription {
    private Set<String> products = new HashSet<>();

    /**
     * Subscribe to a exchange updates for a product on the same channel as the existing subscribed-to products.
     * @param product a valid Crypto-Crypto/Crypto-Currency pair.
     * @return a JSON serializable request to subscribe to the new and existing products.
     */
    public SubscriptionRequest subscribe(String product) {
        products.add(product);
        return new SubscriptionRequest(products);
    }
}
