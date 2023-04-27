package com.richard.marketplace.types;


import java.util.function.Function;

public final class Ok<T, E> implements Result<T, E> {

    private final T item;

    public Ok(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public <U> Result<U, E> map(Function<T, U> fun) {
        return new Ok<>(fun.apply(item));
    }
}