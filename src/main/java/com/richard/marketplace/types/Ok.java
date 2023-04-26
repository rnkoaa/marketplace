package com.richard.marketplace.types;


public final class Ok<T> implements Result<T> {

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
}