package com.richard.marketplace;

import com.richard.marketplace.types.Result;

@ForCommand(CreateAdCommand.class)
public class CreateAdCommandHandler implements CommandHandler<Ad> {
    @Override
    public Result<Ad, Exception> handle(Object command) {
        System.out.println("handling command " + command);
        return Result.ok(new Ad((CreateAdCommand) command));
    }
}
