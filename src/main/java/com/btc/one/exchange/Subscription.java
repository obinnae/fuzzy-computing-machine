package com.btc.one.exchange;

import com.btc.one.exchange.message.SubscriptionRequest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Subscription {
    private Set<String> products = new HashSet<>();

    /**
     * Subscribe to exchange updates for multiple products on the same channel as the existing subscribed-to products.
     * @param many a collection of valid Crypto-Crypto/Crypto-Currency pair.
     * @return a JSON serializable request to subscribe to the new and existing products.
     */
    public SubscriptionRequest subscribe(Collection<? extends String> many) {
        products.addAll(many);
        return new SubscriptionRequest(products);
    }
}
