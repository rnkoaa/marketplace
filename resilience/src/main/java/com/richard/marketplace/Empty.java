package com.richard.marketplace;

import java.util.function.Function;

public record Empty<T, E>() implements Result<T, E> {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T get() {
        throw new IllegalArgumentException("Empty does not have any value");
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> fun) {
        return Result.empty();
    }
}
