package com.richard.marketplace;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AggregateLoader {
    private final Map<String, Object> aggregates = new HashMap<>();

    public Optional<Object> load(String aggregateId) {
        return Optional.ofNullable(aggregates.get(aggregateId));
    }
}
