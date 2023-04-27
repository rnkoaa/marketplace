package com.richard.marketplace;

import java.util.Objects;
import java.util.UUID;

public record AdId(UUID id) {

    public AdId {
        Objects.requireNonNull(id);
    }

    public AdId(){
        this(UUID.randomUUID());
    }

    static AdId newId(UUID id) {
        return new AdId(id);
    }

    static AdId newId() {
        return new AdId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
