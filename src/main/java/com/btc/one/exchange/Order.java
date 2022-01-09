package com.btc.one.exchange;

import lombok.NonNull;
import lombok.Value;

@Value
public class Order {
    @NonNull
    OrderType type;
    double price;
    double quantity;
}
