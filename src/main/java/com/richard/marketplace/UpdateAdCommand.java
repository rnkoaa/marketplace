package com.richard.marketplace;

import java.util.UUID;

public record UpdateAdCommand(
        UUID id,
        String title
) {
}
