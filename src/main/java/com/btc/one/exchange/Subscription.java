package com.btc.one.exchange;

import com.btc.one.exchange.message.SubscriptionRequest;

import java.util.HashSet;
import java.util.Set;

public class Subscription {
    private Set<String> products = new HashSet<>();

    public SubscriptionRequest subscribe(String product) {
        products.add(product);
        return new SubscriptionRequest(products);
    }
}
