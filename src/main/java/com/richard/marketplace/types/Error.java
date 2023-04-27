package com.richard.marketplace.types;

import java.util.function.Function;

public record Error<T, E>(Throwable throwable) implements Result<T, E> {

    public Error(String message) {
        this(new RuntimeException(message));
    }

    public Throwable getError() {
        return throwable;
    }

    @Override
    public boolean isOk() {
        return false;
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

