package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.TargetAggregateIdentifier;

import java.util.UUID;

public class RequestToPublishAdCommand {

    @TargetAggregateIdentifier
    private final UUID adId;

    public RequestToPublishAdCommand(UUID adId) {
        this.adId = adId;
    }

    public UUID getAdId() {
        return adId;
    }
}
