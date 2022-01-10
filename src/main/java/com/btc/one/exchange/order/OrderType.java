package com.btc.one.exchange.order;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum OrderType {
    BUY("buy"),
    SELL("sell");

    public final String name;

    private static final Map<String, OrderType> orderTypes = new HashMap<>();

    static {
        for (OrderType orderType : values()) {
            orderTypes.put(orderType.name, orderType);
        }
    }

    OrderType(String name) {
        this.name = name;
    }

    public static OrderType forName(String name) {
        if (!orderTypes.containsKey(name))
            throw new IllegalArgumentException("Invalid order type");
        return orderTypes.get(name);
    }
}
