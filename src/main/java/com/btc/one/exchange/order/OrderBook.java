package com.btc.one.exchange.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;

@Getter
@Setter
public class OrderBook {
    private OrderBookColumn bids = new OrderBookColumn(10);
    private OrderBookColumn asks = new OrderBookColumn(10);

    /**
     * Add a new order to the order book.
     * Buy orders will be added to the bids column, and sell orders to the asks column.
     * @param order the order to be added to the book.
     */
    public void newOrder(Order order) {
        if (order.getType() == OrderType.BUY)
            bids.add(order);
        else
            asks.add(order);
    }

    @Override
    public String toString() {
        StringBuilder print = new StringBuilder("BID\t\t\t\tASK\n");
        Iterator<Order> bidsIterator = bids.iterator();
        Iterator<Order> asksIterator = asks.iterator();
        while (bidsIterator.hasNext() || asksIterator.hasNext()) {
            if (bidsIterator.hasNext())
                print.append(bidsIterator.next());
            print.append("\t\t");
            if (asksIterator.hasNext())
                print.append(asksIterator.next());
            print.append("\n");
        }
        return print.toString();
    }
}
