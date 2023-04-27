package com.richard.marketplace;

import com.richard.marketplace.cqrs.AggregateRootHolder;
import com.richard.marketplace.cqrs.annotations.ForCommand;
import com.richard.marketplace.cqrs.command.handler.CommandHandler;
import com.richard.marketplace.types.Error;
import com.richard.marketplace.types.Result;

@ForCommand(UpdateAdCommand.class) // circumvents type erasure
public class UpdateAdCommandHandler implements CommandHandler {
    @Override
    public Result<Ad, Throwable> handle(Object command) {
        if (!(command instanceof UpdateAdCommand updateCommand)) {
            return new Error<>("unable to handle command of type " + command.getClass().getCanonicalName());
        }
        var maybeAggregateRoot = AggregateRootHolder.get(updateCommand.id().toString());
        if (maybeAggregateRoot.isEmpty()) {
            return Result.error("unable to find root aggregate");
        }

        Ad ad = (Ad) maybeAggregateRoot.get();
        ad.handle(updateCommand);
        return Result.ok(ad);
    }
}
