package com.richard.marketplace;

import java.util.function.Function;

public record Error<T, E>(E throwable) implements Result<T, E> {

    public E getError() {
        return throwable;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public T get() {
        throw new IllegalArgumentException("Error does not have any value");
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> fun) {
        return new Error<>(throwable);
    }
}

