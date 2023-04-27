package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.Aggregate;
import com.richard.marketplace.cqrs.annotations.AggregateIdentifier;
import com.richard.marketplace.cqrs.annotations.AggregateRootId;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.atteo.classindex.ClassIndex;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class RecordComponentTest {

    @Test
    void recordCommandTest() throws InvocationTargetException, IllegalAccessException {
        var command = new CreateAdCommand(UUID.randomUUID(), "iPad 2");
        handle(command);
    }

    @Test
    void findAggregateAnnotatedClasses(){
        Iterable<Class<?>> aggregateClasses = ClassIndex.getAnnotated(Aggregate.class);
        assertThat(aggregateClasses.iterator().hasNext()).isTrue();
        List<Class<?>> classes = StreamSupport.stream(aggregateClasses.spliterator(), false)
                .toList();
        assertThat(classes).hasSize(1);
        assertThat(classes.get(0)).isEqualTo(Ad.class);
    }

    @Test
    void shouldFindAggregateIdentifierField() throws IllegalAccessException {
        Field[] declaredFields = Ad.class.getDeclaredFields();
        Optional<Field> aggregateIdentifierField = Arrays.stream(declaredFields)
                .filter(it -> it.isAnnotationPresent(AggregateIdentifier.class))
                .findFirst();

        assertThat(aggregateIdentifierField).isPresent();

        Ad ad = new Ad();
//        ad.apply(new AdCreatedEvent(AdId.newId(), "Ipad 2"));
        Field field = aggregateIdentifierField.orElseGet(() -> null);
        field.setAccessible(true);
        var value = field.get(ad);
        assertThat(value).isNull();
    }

    void handle(Object command) throws InvocationTargetException, IllegalAccessException {
        Optional<AggregateIdComponents> aggregateIdInfo = getAggregateIdInfo(command);
        if (aggregateIdInfo.isEmpty()) {
            // invoke constructor with command
        }


    }

    Optional<AggregateIdComponents> getAggregateIdInfo(Object command) {
        if (command.getClass().isRecord()) {
            RecordComponent[] recordComponents = command.getClass().getRecordComponents();
            var aggregateRootIdComponent = Arrays.stream(recordComponents)
                    .filter(it -> it.isAnnotationPresent(AggregateRootId.class))
                    .findFirst();

            if (aggregateRootIdComponent.isPresent()) {
                RecordComponent recordComponent = aggregateRootIdComponent.get();
                Class<?> type = recordComponent.getType();

                try {
                    Object componentValue = recordComponent.getAccessor().invoke(command);
                    return Optional.of(new AggregateIdComponents(type, componentValue));

                } catch (IllegalAccessException | InvocationTargetException e) {
                    return Optional.empty();
                }
            }

        } else {
            Field[] declaredFields = command.getClass().getDeclaredFields();

        }
        return Optional.empty();
    }
}

record AggregateIdComponents(Class<?> type, Object value) {
}
