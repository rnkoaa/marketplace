package com.richard.marketplace;

public record AdCreatedEvent(
        AdId id,
        String title
) {
}
