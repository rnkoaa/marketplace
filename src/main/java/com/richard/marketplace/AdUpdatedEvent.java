package com.richard.marketplace;

import java.util.UUID;

public record AdUpdatedEvent(UUID id, String title) {
}

