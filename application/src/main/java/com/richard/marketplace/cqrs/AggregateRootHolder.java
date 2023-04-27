package com.richard.marketplace.cqrs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AggregateRootHolder {
    private static final Map<String, Object> aggregateRoots = new HashMap<>();

    public static void put(String key, Object aggregateRoot) {
        Objects.requireNonNull(key, "key of aggregate root cannot be null");
        Objects.requireNonNull(aggregateRoot, "aggregate root cannot be null");
        aggregateRoots.put(key, aggregateRoot);
    }

    public static Optional<?> get(String key) {
        return Optional.ofNullable(aggregateRoots.get(key));
    }
}
