package com.richard.marketplace.cqrs;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T> {

    public abstract T getId();

    private final List<Object> events = new ArrayList<>();

    public void apply(Object event) {
        events.add(event);
    }

    public List<Object> getEvents() {
        return List.copyOf(events);
    }

    public void clearEvents() {
        events.clear();
    }
}
