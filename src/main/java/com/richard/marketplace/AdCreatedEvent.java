package com.richard.marketplace;

import java.util.UUID;

public record AdCreatedEvent(
        UUID id,
        String title
) {
}
