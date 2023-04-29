package com.richard.marketplace;

import com.richard.marketplace.cqrs.annotations.CommandHandler;
import com.richard.marketplace.cqrs.annotations.TargetAggregateIdentifier;
import com.richard.marketplace.cqrs.support.TargetAggregateIdentifierComponents;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;

public class AggregateMediator {

    static final Map<Class<?>, Class<?>> handlers = new HashMap<>();
    static final Map<Class<?>, String> handlerMethodName = new HashMap<>();

    AggregateLoader aggregateLoader = new AggregateLoader();

    List<Class<?>> allowedAggregateTypes = List.of(String.class, UUID.class);

    public AggregateMediator() {
        handlers.clear();
    }

    void register(Class<?> commandClass, Class<?> handlerClass) {
        Objects.requireNonNull(commandClass, "command class cannot be null");
        Objects.requireNonNull(handlerClass, "handler class cannot be null");

        Method[] declaredMethods = handlerClass.getDeclaredMethods();
        List<Method> commandMethods = Arrays.stream(declaredMethods).filter(it -> it.isAnnotationPresent(CommandHandler.class))
            .toList();

        // keep track of the name of the method to handle this command
        // we only allow one parameter for command handler methods.
        Optional<Method> handlerMethod = commandMethods.stream().filter(it -> {
                Class<?>[] parameterTypes = it.getParameterTypes();
                return (parameterTypes.length == 1 && parameterTypes[0].equals(commandClass));
            })
            .findFirst();

        handlerMethod.ifPresent(method -> handlerMethodName.put(commandClass, method.getName()));
        handlers.put(commandClass, handlerClass);
    }

    Optional<Class<?>> getHandler(Class<?> klass) {
        return Optional.ofNullable(handlers.get(klass));
    }

    Result<?, Throwable> handle(Object command) {
        Objects.requireNonNull(command, "command object cannot be null");
        Class<?> handlerClass = handlers.get(command.getClass());
        if (handlerClass == null) {
            return Result.error("unknown handler for class " + command.getClass().getCanonicalName());
        }

        // if there's a valid constructor for this command, let the constructor handle it.
        var result = handleObjectForConstructor(command, handlerClass);
        if (!result.isEmpty()) {
            return result;
        }

        Result<TargetAggregateIdentifierComponents, Throwable> aggregateInfoError = getAggregateIdInfo(command);
        if (aggregateInfoError.isError()) {
            return aggregateInfoError;
        }

        TargetAggregateIdentifierComponents aggregateIdentifierComponents = aggregateInfoError.get();
        Class<?> aggregateIdentifierType = aggregateIdentifierComponents.type();
        if (!allowedAggregateTypes.contains(aggregateIdentifierType)) {
            return Result.error("Aggregate Identifier must either be a string or a UUID");
        }

        System.out.println(aggregateIdentifierComponents);
        String aggregateIdAsString;
        if (aggregateIdentifierComponents.type() == String.class) {
            aggregateIdAsString = (String) aggregateIdentifierComponents.value();
        } else {
            var asUUID = (UUID) aggregateIdentifierComponents.value();
            aggregateIdAsString = asUUID.toString();
        }

        var maybeAggregate = aggregateLoader.load(aggregateIdAsString);
        var handlerObject = maybeAggregate

            // TODO -
            // cast the handler object,
            // or create a new instance if not found
            .map(it -> (Ad) it).orElseGet(Ad::new);

        // find methods that handle this command using Method handle
        try {
            // get handler object
            return handle(command, handlerObject);
        } catch (Throwable e) {
            return Result.error(e.getMessage());
        }
    }

    Result<?, Throwable> handle(Object command, Object handler) {
        String methodName = handlerMethodName.get(command.getClass());
        if (methodName == null) {
            throw new IllegalArgumentException("no method available to handle command " + command.getClass());
        }

        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodType mt = MethodType.methodType(void.class, command.getClass());
            MethodHandle handleMethod = lookup.findVirtual(handler.getClass(), methodName, mt);
            handleMethod.invoke(handler, command);
            return Result.ok(handler);
        } catch (Throwable e) {
            return Result.error(e.getMessage());
        }
    }

    Result<TargetAggregateIdentifierComponents, Throwable> getAggregateIdInfo(Object command) {
        if (command.getClass().isRecord()) {
            RecordComponent[] recordComponents = command.getClass().getRecordComponents();
            var aggregateRootIdComponent = Arrays.stream(recordComponents)
                .filter(it -> it.isAnnotationPresent(TargetAggregateIdentifier.class))
                .findFirst();

            if (aggregateRootIdComponent.isPresent()) {
                RecordComponent recordComponent = aggregateRootIdComponent.get();
                Class<?> type = recordComponent.getType();

                try {
                    Object componentValue = recordComponent.getAccessor().invoke(command);
                    var aggregateIdentifierInfo = new TargetAggregateIdentifierComponents(type, componentValue);
                    return Result.ok(aggregateIdentifierInfo);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return Result.error(e.getMessage());
                }
            }

        } else {
            // TODO - find id field using method handles
            Field[] declaredFields = command.getClass().getDeclaredFields();

        }
        return Result.error("not found");
    }

    Result<?, Throwable> handleObjectForConstructor(Object command, Class<?> handlerClass) {
        try {
            Constructor<?> constructor = handlerClass.getDeclaredConstructor(command.getClass());
            if (constructor.isAnnotationPresent(CommandHandler.class)) {
                var object = constructor.newInstance(command);
                return Result.ok(object);
            }
        } catch (NoSuchMethodException e) {
            // constructor not found
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return Result.error(e.getMessage());
        }
        return Result.empty();
    }
}
