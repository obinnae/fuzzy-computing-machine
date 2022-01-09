package com.btc.one.exchange;

import com.google.common.collect.EvictingQueue;

import java.util.Iterator;

public class OrderBook {
    private EvictingQueue<Order> bids = EvictingQueue.create(10); //TODO: Revisit and sort by price
    private EvictingQueue<Order> asks = EvictingQueue.create(10);

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
