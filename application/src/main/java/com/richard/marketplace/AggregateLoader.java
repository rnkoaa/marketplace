package com.richard.marketplace;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AggregateLoader {
    private static final Map<String, Object> aggregates = new HashMap<>();

    public Optional<Object> load(String aggregateId) {
        return Optional.ofNullable(aggregates.get(aggregateId));
    }

    public void saveAggregate(String aggregateId, Object aggregate){
        Objects.requireNonNull(aggregate, "Aggregate object cannot be null");
        Objects.requireNonNull(aggregateId, "Aggregate id object cannot be null");
        aggregates.put(aggregateId, aggregate);
    }

    public void clear() {
        aggregates.clear();
    }
}
