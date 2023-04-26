package com.richard.marketplace;

import com.richard.marketplace.types.Ok;
import com.richard.marketplace.types.Result;

@ForCommand(CreateAdCommand.class)
public class CreateAdCommandHandler implements CommandHandler< Ad> {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Result<Ad> handle(Object command) {
        System.out.println("handling command " + command);

        var  ad = new Ad((CreateAdCommand) command);
        return new Ok(ad);
    }
}
