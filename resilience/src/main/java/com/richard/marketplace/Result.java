package com.richard.marketplace;

import java.util.function.Function;

// https://github.com/hdevalke/result4j/blob/master/src/main/java/result4j/Result.java
public sealed interface Result<T, E> permits Ok, Error, Empty {

   default boolean isOk() {
       return false;
   }

    default boolean isEmpty() {
        return false;
    }

    default boolean isError() {
        return false;
    }

    default T get() {
        throw new RuntimeException("operation not supported");
    }

    default E getError() {
        throw new RuntimeException("operation not supported");
    }

    <U> Result<U, E> map(final Function<T, U> fun);

    static <T, E> Result<T, E> empty() {
        return new Empty<>();
    }

    static <T> Result<T, Throwable> ok(final T value) {
        return new Ok<>(value);
    }

    static <T> Result<T, Throwable> error(final String message) {
        return new Error<>(new RuntimeException(message));
    }

}

/*
mport java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public sealed interface Try<T> permits Empty, Failure, Success {

    boolean isSuccessful();

    boolean isFailure();

    T get();

    T orElse(T defaultValue);

    T orElseGet(ThrowableSupplier<T> defaultValue);

    T orElseNull();

    // Returns the encapsulated value if this instance represents success or
    // throws the encapsulated Throwable exception if it is failure.
    // T orThrow()

    Try<T> filter(Predicate<T> predicate, String message);

    <U> Try<U> map(ThrowableFunction<T, U> func);

    <U> Try<U> flatMap(ThrowableFunction<T, Try<U>> func);

    <U> Try<U> recover(ThrowableSupplier<U> supplier);

    default void onFailure(Consumer<Exception> consumer) {
        // do nothing
    }

    default void onSuccess(Consumer<T> consumer) {
        // do nothing
    }

    // exceptionOrNull()

    // <R, T> R Try<T>.fold(
    //    onSuccess: (T value) -> R,
    //    onFailure: (Throwable exception) -> R
    //)

    default Try<Exception> mapFailure(Function<Exception, ? extends Exception> function) {
//       return  map(function)
        return Try.empty();
    }

    static <T> Try<T> empty() {
        return new Empty<>();
    }

    static <T> Try<T> success(T value) {
        return new Success<>(value);
    }

    static <T> Try<T> failure(Exception exception) {
        return new Failure<>(exception);
    }

    static <T> Try<T> failure(String message) {
        return new Failure<>(new Exception(message));
    }

    static <T> Try<T> failure(String message, Exception ex) {
        return new Failure<>(new Exception(message, ex));
    }

    static <T> Try<T> failure(Failure<T> failure) {
        return new Failure<>(failure.exception());
    }

    static <T> Try<T> ofNullable(T value) {
        return (value == null) ? failure("value is null") : success(value);
    }

    static <T> Try<T> of(ThrowableSupplier<T> supplier) {
        try {
            T t = supplier.get();
            return new Success<>(t);
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }

}
 */

/*
@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R apply(T t) throws Exception;
}

@FunctionalInterface
public interface ThrowableSupplier<T>  {

    T get() throws Exception;
}
 */
/*

public record Empty<T>() implements Try<T> {

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public T get() {
        ;
        throw new RuntimeException("attempting to retrieve value from empty result");
    }

    @Override
    public T orElse(T defaultValue) {
        return defaultValue;
    }

    @Override
    public T orElseGet(ThrowableSupplier<T> defaultValue) {
        try {
            T t = defaultValue.get();
            if(t == null) {
                throw new RuntimeException("default value is null");
            }
            return t;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T orElseNull() {
        return null;
    }

    @Override
    public Try<T> filter(Predicate<T> predicate, String message) {
        return Try.empty();
    }

    @Override
    public <U> Try<U> map(ThrowableFunction<T, U> func) {
        return Try.empty();
    }

    @Override
    public <U> Try<U> flatMap(ThrowableFunction<T, Try<U>> func) {
        return Try.empty();
    }

    @Override
    public <U> Try<U> recover(ThrowableSupplier<U> supplier) {
        return Try.empty();
    }
}
 */

/*

record Failure<T>(Exception exception) implements Try<T> {

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public T get() {
        throw new RuntimeException("attempting to get value from a failed operation " + exception.getMessage());
    }

    @Override
    public T orElse(T defaultValue) {
        return defaultValue;
    }

    @Override
    public T orElseGet(ThrowableSupplier<T> defaultValue) {
        try {
            return defaultValue.get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public T orElseNull() {
        return null;
    }

    @Override
    public Try<Exception> mapFailure(Function<Exception, ? extends Exception> function) {
        return new Failure<>(function.apply(exception));
    }

    @Override
    public Try<T> filter(Predicate<T> predicate, String message) {
        return new Failure<>(this.exception);
    }

    @Override
    public <U> Try<U> map(ThrowableFunction<T, U> func) {
        return new Failure<>(this.exception);
    }

    @Override
    public <U> Try<U> flatMap(ThrowableFunction<T, Try<U>> func) {
        return new Failure<>(this.exception);
    }

    @Override
    public <U> Try<U> recover(ThrowableSupplier<U> function) {
        try {
            U result = function.get();
            return Try.ofNullable(result);
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }

    @Override
    public void onFailure(Consumer<Exception> consumer) {
        consumer.accept(exception);
//        T t = supplier.get();
    }
}
 */

/*

record Success<T>(T value) implements Try<T> {

    @Override
    public boolean isSuccessful() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public T get() {
        return this.value;
    }

    @Override
    public T orElse(T defaultValue) {
        return value;
    }

    @Override
    public T orElseGet(ThrowableSupplier<T> defaultValue) {
        return value;
    }

    @Override
    public T orElseNull() {
        return value;
    }

    @Override
    public Try<T> filter(Predicate<T> predicate, String message) {
        try {
            if (predicate.test(this.value)) {
                return new Success<>(this.value);
            } else {
                return Try.failure(message);
            }
        } catch (Exception ex) {
            return Try.failure(ex);
        }
    }

    @Override
    public <U> Try<U> map(ThrowableFunction<T, U> func) {
        try {
            return new Success<>(func.apply(value));
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }

    @Override
    public <U> Try<U> flatMap(ThrowableFunction<T, Try<U>> func) {
        try {
            return func.apply(value);
        } catch (Exception ex) {
            return new Failure<>(ex);
        }
    }

    @Override
    public <U> Try<U> recover(ThrowableSupplier< U> supplier) {
        try {
            return Try.ofNullable(supplier.get());
        } catch (Exception e) {
           return new Failure<>(e);
        }
    }

    @Override
    public void onSuccess(Consumer<T> consumer) {
        consumer.accept(value);
    }

    @Override
    public String toString() {
        return "Success{" + "value=" + value + '}';
    }
}
 */
