package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.AggregateRootId;

import java.util.UUID;

public record CreateAdCommand(
        @AggregateRootId
        UUID id,
        String title
) {
}
