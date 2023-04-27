package com.richard.marketplace.types;

import java.util.function.Function;

public final class Error<T, E> implements Result<T, E> {
    private final Exception exception;

    public Error(String message) {
        exception = new RuntimeException(message);
    }

    public Exception getError() {
        return exception;
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
    public <U> Result<U, E> map(Function<T, U> fun) {
        return new Error<>(exception.getMessage());
    }
}

