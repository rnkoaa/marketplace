package com.richard.marketplace.cqrs;

import com.richard.marketplace.cqrs.annotations.AggregateIdentifier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AggregateCommandHandler {
    private static final Map<Class<?>, Class<?>> aggregateCommandMapper = new HashMap<>();

    void register(Class<?> commandType, Class<?> aggregateType) {
        aggregateCommandMapper.put(commandType, aggregateType);
    }

    void handleCommand(Object command) throws InvocationTargetException, IllegalAccessException {
        Class<?> aggregateType = aggregateCommandMapper.get(command.getClass());

        if (aggregateType == null) {
            return;
        }

        if (command.getClass().isRecord()) {
            RecordComponent[] recordComponents = command.getClass().getRecordComponents();
            var aggregateRootIdComponent = Arrays.stream(recordComponents)
                    .filter(it -> it.isAnnotationPresent(AggregateIdentifier.class))
                    .findFirst();

            if(aggregateRootIdComponent.isPresent()){
                RecordComponent recordComponent = aggregateRootIdComponent.get();
                Object invoke = recordComponent.getAccessor().invoke(command);
                Class<?> type = recordComponent.getType();

                //  RecordComponent[] rc = Point.class.getRecordComponents();
                //        System.out.println(rc[0].getAccessor().invoke(point));
//                recordComponent.
            }

        } else {
            Field[] declaredFields = command.getClass().getDeclaredFields();

        }


    }
}
