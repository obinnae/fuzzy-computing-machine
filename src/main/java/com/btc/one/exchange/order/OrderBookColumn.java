package com.btc.one.exchange.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

@RequiredArgsConstructor
@Getter
@Setter
public class OrderBookColumn {
    @JsonIgnore
    private final int maxSize;
    private final ConcurrentSkipListSet<Order> column = new ConcurrentSkipListSet<>();

    /**
     * Add an order to the column preserving the natural ordering and without exceeding the max size.
     * @param order the order to be added.
     */
    public void add(Order order) {
        if (column.size() >= maxSize) {
            // if we're at capacity, compare order with the last element in columns
            if (column.last().compareTo(order) > 0)
            {
                // if order has a higher ordering than the last element, evict the last element and add order
                column.pollLast();
                column.add(order);
            }
        } else {
            column.add(order);
        }
    }

    public Iterator<Order> iterator() {
        return column.iterator();
    }
}
