package com.richard.marketplace.types;

// https://github.com/hdevalke/result4j/blob/master/src/main/java/result4j/Result.java
public sealed interface Result<T> permits Ok, Error {

    boolean isOk();

    boolean isError();

}
