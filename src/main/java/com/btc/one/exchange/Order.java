package com.btc.one.exchange;

import com.btc.one.exchange.message.OrderSubscriptionResponse;
import lombok.NonNull;
import lombok.Value;

@Value
public class Order {
    @NonNull
    OrderType type;
    Double price;
    Double size;

    public static Order fromExchangeUpdate(OrderSubscriptionResponse orderSubscriptionResponse) {
        OrderType orderType = orderType(orderSubscriptionResponse);
        Double price = price(orderSubscriptionResponse);
        Double size = size(orderSubscriptionResponse);

        return new Order(orderType, price, size);
    }

    private static OrderType orderType(OrderSubscriptionResponse orderSubscriptionResponse) {
        return OrderType.forName(orderSubscriptionResponse.getChanges().get(0).get(0));
    }

    private static Double price(OrderSubscriptionResponse orderSubscriptionResponse) {
        return Double.valueOf(orderSubscriptionResponse.getChanges().get(0).get(1));
    }

    private static Double size(OrderSubscriptionResponse orderSubscriptionResponse) {
        return Double.valueOf(orderSubscriptionResponse.getChanges().get(0).get(2));
    }
}
