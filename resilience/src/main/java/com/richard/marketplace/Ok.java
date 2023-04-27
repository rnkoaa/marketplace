package com.richard.marketplace;


import java.util.function.Function;

public record Ok<T, E>(T item) implements Result<T, E> {

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