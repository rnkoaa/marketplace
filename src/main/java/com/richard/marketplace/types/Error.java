package com.richard.marketplace.types;

public final class Error implements Result<Exception> {
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
}

