package com.richard.marketplace.types;

import java.util.function.Function;

// https://github.com/hdevalke/result4j/blob/master/src/main/java/result4j/Result.java
public sealed interface Result<T, E> permits Ok, Error {

    boolean isOk();

    boolean isError();

    <U> Result<U, E> map(final Function<T, U> fun);

    static <T, E> Result<T, E> ok(final T value) {
        return new Ok<>(value);
    }

    static <T, E> Result<T, E> error(final String message) {
        return new Error<>(message);
    }

}
