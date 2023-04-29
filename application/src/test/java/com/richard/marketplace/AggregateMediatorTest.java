package com.richard.marketplace;

import com.richard.marketplace.aggregate.Market;
import com.richard.marketplace.cqrs.support.TargetAggregateIdentifierComponents;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AggregateMediatorTest {

    @Test
    void successfullyRegisterHandler() {
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);

        assertThat(AggregateMediator.handlerMethodName.size()).isEqualTo(1);

        Optional<Class<?>> handler = aggregateMediator.getHandler(CreateAdCommand.class);
        assertThat(handler).isPresent();

        assert handler.isPresent();

        Class<?> aClass = handler.get();
        assertThat(aClass).isEqualTo(Ad.class);
    }

    @Test
    void successfullyRegisterMultipleHandlers() {
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);

        assertThat(AggregateMediator.handlers.size()).isEqualTo(2);

        Optional<Class<?>> handler = aggregateMediator.getHandler(CreateAdCommand.class);
        assertThat(handler).isPresent();

        assert handler.isPresent();

        Class<?> aClass = handler.get();
        assertThat(aClass).isEqualTo(Ad.class);

        Optional<Class<?>> updateCommandClass = aggregateMediator.getHandler(UpdateAdTextCommand.class);
        assert updateCommandClass.isPresent();

        Class<?> updateClass = handler.get();
        assertThat(updateClass).isEqualTo(Ad.class);
    }

    @Test
    void handleCreateCommand() {
        var adId = UUID.randomUUID();
        var title = "iPad 2";
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);

        Result<?, Throwable> result = aggregateMediator.handle(new CreateAdCommand(title));
        assertThat(result.isOk()).isTrue();

        Ad ad = (Ad) result.get();
        assertThat(ad).isNotNull();
        assertThat(ad).satisfies(a -> {
//            assertThat(a.getId()).isEqualTo(AdId.of(adId));
            assertThat(a.getTitle()).isEqualTo(title);
        });

        switch (result) {
            case Error<?, Throwable> exceptionError ->
                System.out.println(exceptionError.getError().getLocalizedMessage());
            case Ok<?, Throwable>(Ad okAd) -> {
                System.out.println(okAd);
            }
            case Empty<?, Throwable> ignored -> System.out.println("result is empty");
            default -> throw new IllegalStateException("Unexpected value: " + result);
        }
    }

    @Test
    void handleUpdateCommand() {
        var adId = UUID.randomUUID();
        var title = "iPad 2";
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);

        Result<?, Throwable> result = aggregateMediator.handle(new CreateAdCommand(title));
        assertThat(result.isOk()).isTrue();

        Ad ad = (Ad) result.get();
        assertThat(ad).isNotNull();
        assertThat(ad).satisfies(a -> {
//            assertThat(a.getId()).isEqualTo(AdId.of(adId));
            assertThat(a.getTitle()).isEqualTo(title);
        });

        Result<?, Throwable> updateResult = aggregateMediator.handle(new UpdateAdTextCommand(adId, "iPad 3 Pro"));
        assertThat(updateResult.isOk()).isTrue();
        assertThat(updateResult.isError()).isFalse();
    }

    @Test
    void handleCommand() {
        Ad ad = new Ad(new CreateAdCommand("iPad 3 Pro"));
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);

        assertDoesNotThrow(() -> {
            aggregateMediator.handle(
                new UpdateAdTextCommand(ad.getId().id(), "iPad 4 Pro - 2023"),
                ad
            );
        });
        assertThat(ad.getTitle()).isEqualTo("iPad 4 Pro - 2023");
    }

    @Test
    void nonSupportedAggregateTypesReturnError() {
        var updateMarket = new Market.UpdateMarket(Instant.now());
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(Market.UpdateMarket.class, Market.class);

        var result = aggregateMediator.handle(updateMarket);
        assertThat(result.isError()).isTrue();

        Throwable error = result.getError();
        assertThat(error.getMessage()).isEqualTo("Aggregate Identifier must either be a string or a UUID");
    }

    @Test
    void testCommandIdentifierInfoForRecordsWithAnnotation() {
        var updateCommand = new Market.UpdateMarket(Instant.now());
        AggregateMediator aggregateMediator = new AggregateMediator();
        Result<TargetAggregateIdentifierComponents, Throwable> aggregateIdInfo = aggregateMediator.getAggregateIdInfo(updateCommand);
        assertThat(aggregateIdInfo.isOk()).isTrue();
        TargetAggregateIdentifierComponents targetAggregateIdentifierComponents = aggregateIdInfo.get();
        assertThat(targetAggregateIdentifierComponents.type()).isEqualTo(Instant.class);
        assertThat(targetAggregateIdentifierComponents.value()).isEqualTo(updateCommand.id());
    }

    @Test
    void testCreateCommandHandlerWithAnnotation() {
        var createCommand = new CreateAdCommand("some title");
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);

        Result<?, Throwable> result = aggregateMediator.handle(createCommand);
        assertThat(result.isOk()).isTrue();
    }

    @Test
    void testNonConstructorCommandReturnsEmpty() {
        var updateCommand = new UpdateAdTextCommand(UUID.randomUUID(), "some title");
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(UpdateAdTextCommand.class, Ad.class);
        Result<?, Throwable> result = aggregateMediator.handleObjectForConstructor(updateCommand, Ad.class);
        assertThat(result.isOk()).isFalse();
        assertThat(result.isEmpty()).isTrue();
        assertThat(result.isError()).isFalse();
    }

    @Test
    void testConstructorCommandReturnsOk() {
        var createCommand = new CreateAdCommand("some title");
        AggregateMediator aggregateMediator = new AggregateMediator();
        aggregateMediator.register(CreateAdCommand.class, Ad.class);
        Result<?, Throwable> result = aggregateMediator.handleObjectForConstructor(createCommand, Ad.class);
        assertThat(result.isOk()).isTrue();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.isError()).isFalse();

        Ad ad = (Ad) result.get();
        assertThat(ad).isNotNull();
        assertThat(ad.getTitle()).isEqualTo(createCommand.title());
    }
}
