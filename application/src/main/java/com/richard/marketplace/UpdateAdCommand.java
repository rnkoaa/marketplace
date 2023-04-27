package com.richard.marketplace;

public record UpdateAdCommand(
        AdId id,
        String title
) {
}
