package com.btc.one.exchange;

import java.util.HashSet;
import java.util.Set;

public class Subscription {
    private Set<String> products = new HashSet<>();

    public SubscriptionMessage subscribe(String product) {
        products.add(product);
        return new SubscriptionMessage(products);
    }
}
