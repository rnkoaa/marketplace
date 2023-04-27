package com.richard.marketplace;

import com.richard.marketplace.types.Ok;
import com.richard.marketplace.types.Result;

@ForCommand(UpdateAdCommand.class) // circumvents type erasure
public class UpdateAdCommandHandler implements CommandHandler<Ad> {
    @Override
    public Result<Ad, Exception> handle(Object command) {
        if (!(command instanceof UpdateAdCommand updateCommand)) {
            return new Ok<>(null);
        }
        var ad = new Ad(new CreateAdCommand(updateCommand.title()));
        ad.handle(updateCommand);
        return new Ok<>(ad);
    }
}
