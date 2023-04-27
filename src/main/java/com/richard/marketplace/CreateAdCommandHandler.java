package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.ForCommand;
import com.richard.marketplace.cqrs.command.handler.CommandHandler;
import com.richard.marketplace.types.Result;

@ForCommand(CreateAdCommand.class)
public class CreateAdCommandHandler implements CommandHandler {
    @Override
    public Result<Ad, Throwable> handle(Object command) {
        System.out.println("handling command " + command);
        return Result.ok(new Ad((CreateAdCommand) command));
    }
}
