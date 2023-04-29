package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.TargetAggregateIdentifier;

import java.util.UUID;

public record UpdateAdTextCommand(
    @TargetAggregateIdentifier
    UUID id,
    String title
) {
}
