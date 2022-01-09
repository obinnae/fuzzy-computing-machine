package com.btc.one.exchange;

import io.vertx.core.json.Json;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Value
public class SubscriptionMessage {
    String type = "subscribe";
    Set<String> product_ids;
    Set<String> channels = new HashSet<>(Collections.singletonList("level2"));

    @Override
    public String toString() {return Json.encode(this); }
}
