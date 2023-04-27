package com.richard.marketplace.cqrs.command.handler;

import com.richard.marketplace.types.Result;

public interface CommandHandler {

    Result<?, Throwable> handle(Object command);

}
