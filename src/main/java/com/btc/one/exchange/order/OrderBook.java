package com.btc.one.exchange.order;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class OrderBook {
    private Set<Order> bids = new ConcurrentSkipListSet<>();
    private Set<Order> asks = new ConcurrentSkipListSet<>();

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
