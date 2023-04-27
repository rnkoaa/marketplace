package com.richard.marketplace;

import com.richard.marketplace.cqrs.command.handler.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateAdCommandHandlerTest {

    CommandHandler createAdCommandAdCommandHandler = new CreateAdCommandHandler();
    CommandHandler updateAdCommandAdCommandHandler = new UpdateAdCommandHandler();
    Mediator mediator = new Mediator();

    @BeforeEach
    void setup() {
        mediator.register(CreateAdCommand.class, createAdCommandAdCommandHandler);
        mediator.register(UpdateAdCommand.class, updateAdCommandAdCommandHandler);
    }

    @Test
    void validCommandShouldBeHandled() {
//        var ad = new Ad(new CreateAdCommand("Ipad 2"));
        var result = mediator.handle(new CreateAdCommand(UUID.randomUUID(), "Ipad 2"));

        assertTrue(result.isOk());
        Ad ad = (Ad) result.get();


        assertEquals("Ipad 2", ad.getTitle());
//
//        switch (result) {
//            case Error<?, Exception> exceptionError ->
//                    System.out.println(exceptionError.getError().getLocalizedMessage());
//            case Ok<?, Exception>(Ad okAd) -> {
//                System.out.println(okAd);
//            }
//            default -> throw new IllegalStateException("Unexpected value: " + result);
//        }
//        var command = new CreateAdCommand("Ipad 2");
////        Result<Ad> handle = createAdCommandAdCommandHandler.handle(command);
//        assertTrue(handle.isOk());
//        ;
//        assertFalse(handle.isError());
//
//        Ok<Ad> ok = (Ok<Ad>) handle;
//
//        System.out.println(ok.get());
//        switch (handle) {
//            case Ok<Ad>(Ad ad) -> System.out.println(ad);
//            default -> System.out.println("error happened");
////            case com.richard.marketplace.types.Error<Exception> (Exception ex) -> System.out.println(ex);
//        }
    }

//    @Test
//    void mediatorShouldBeAbleToHandleIt() {
////        var command = new CreateAdCommand("Ipad 2");
////        mediator.register(CreateAdCommand.class, createAdCommandAdCommandHandler);
////        mediator.register(UpdateAdCommand.class,  updateAdCommandAdCommandHandler);
////
////        var result = mediator.handle(command);
////        assertTrue(result.isOk());
////        if(result.isOk()) {
////            Ok<Ad> ok = (Ok<Ad>) result;
////            System.out.println(ok.get());
////        }
//    }
}