package com.richard.marketplace.aggregate;

import com.richard.marketplace.cqrs.annotations.Aggregate;
import com.richard.marketplace.cqrs.annotations.TargetAggregateIdentifier;

import java.time.Instant;

@Aggregate
public class Market {

    private Instant id;

    public Market(Instant id) {
        this.id = id;
    }

    public void handle(UpdateMarket updateMarket) {
        this.id = updateMarket.id;
    }

    public Instant getId() {
        return id;
    }

    public record UpdateMarket(
        @TargetAggregateIdentifier
        Instant id) {
    }
}


