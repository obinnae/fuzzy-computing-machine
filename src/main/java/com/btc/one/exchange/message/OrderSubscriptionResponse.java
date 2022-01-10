package com.btc.one.exchange.message;

import lombok.Data;

import java.util.List;

@Data
public class OrderSubscriptionResponse extends SubscriptionResponse {
    String product_id;
    List<List<String>> changes;
    String time; // TODO: Change to Instant/Date
}
