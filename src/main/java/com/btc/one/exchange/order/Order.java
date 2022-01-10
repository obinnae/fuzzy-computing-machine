package com.btc.one.exchange.order;

import com.btc.one.exchange.message.OrderSubscriptionResponse;
import lombok.NonNull;
import lombok.Value;

@Value
public class Order implements Comparable<Order> {
    @NonNull
    OrderType type;
    String product;
    Double price;
    Double size;

    public static Order fromExchangeUpdate(OrderSubscriptionResponse orderSubscriptionResponse) {
        OrderType orderType = orderType(orderSubscriptionResponse);
        String product = product(orderSubscriptionResponse);
        Double price = price(orderSubscriptionResponse);
        Double size = size(orderSubscriptionResponse);

        return new Order(orderType, product, price, size);
    }

    private static OrderType orderType(OrderSubscriptionResponse orderSubscriptionResponse) {
        return OrderType.forName(orderSubscriptionResponse.getChanges().get(0).get(0));
    }

    private static String product(OrderSubscriptionResponse orderSubscriptionResponse) {
        return orderSubscriptionResponse.getProduct_id();
    }

    private static Double price(OrderSubscriptionResponse orderSubscriptionResponse) {
        return Double.valueOf(orderSubscriptionResponse.getChanges().get(0).get(1));
    }

    private static Double size(OrderSubscriptionResponse orderSubscriptionResponse) {
        return Double.valueOf(orderSubscriptionResponse.getChanges().get(0).get(2));
    }

    @Override
    public int compareTo(Order o) {
        // For the purpose of the order book we are trying to build, only orders of similar types (Buy or Sell) will ever have to be compared
        if (this.type == OrderType.BUY && o.type == OrderType.BUY)
            return o.price.compareTo(this.price); // keep higher bid prices top of book
        else if (this.type == OrderType.SELL && o.type == OrderType.SELL)
            return this.price.compareTo(o.price); // keep lower ask prices top of book
        else
            return 0; // Both orders aren't of the same order type
    }
}
