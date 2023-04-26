package com.richard.marketplace;

import com.richard.marketplace.types.Ok;
import com.richard.marketplace.types.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateAdCommandHandlerTest {

    CommandHandler<CreateAdCommand, Ad> createAdCommandAdCommandHandler = new CreateAdCommandHandler();
    CommandHandler<UpdateAdCommand, Ad> updateAdCommandAdCommandHandler = new UpdateAdCommandHandler();
    Mediator mediator = new Mediator();

    @Test
    void validCommandShouldBeHandled() {
        var command = new CreateAdCommand("Ipad 2");
        Result<Ad> handle = createAdCommandAdCommandHandler.handle(command);
        assertTrue(handle.isOk());
        ;
        assertFalse(handle.isError());

        Ok<Ad> ok = (Ok<Ad>) handle;

        System.out.println(ok.get());
//        switch (handle) {
//            case Ok<Ad>(Ad ad) -> System.out.println(ad);
//            default -> System.out.println("error happened");
////            case com.richard.marketplace.types.Error<Exception> (Exception ex) -> System.out.println(ex);
//        }
    }

    @Test
    void mediatorShouldBeAbleToHandleIt() {
        var command = new CreateAdCommand("Ipad 2");
        mediator.register(CreateAdCommand.class, createAdCommandAdCommandHandler);
        mediator.register(UpdateAdCommand.class,  updateAdCommandAdCommandHandler);

        var result = mediator.handle(command);
        assertTrue(result.isOk());
        if(result.isOk()) {
            Ok<Ad> ok = (Ok<Ad>) result;
            System.out.println(ok.get());
        }
    }
}