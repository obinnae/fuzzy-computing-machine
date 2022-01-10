package com.btc.one.exchange.message;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionAckResponse extends SubscriptionResponse {
    List<SubscriptionChannel> channels;
}
