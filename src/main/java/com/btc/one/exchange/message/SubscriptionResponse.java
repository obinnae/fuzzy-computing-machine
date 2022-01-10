package com.btc.one.exchange.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.vertx.core.json.Json;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SubscriptionAckResponse.class, name = "subscriptions"),
        @JsonSubTypes.Type(value = OrderSubscriptionResponse.class, name = "l2update")
})
public abstract class SubscriptionResponse {
    String type;

    public static SubscriptionResponse fromString(String responseString) {
        return Json.decodeValue(responseString, SubscriptionResponse.class);
    }
}
