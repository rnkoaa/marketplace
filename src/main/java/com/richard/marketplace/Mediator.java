package com.richard.marketplace;

import com.richard.marketplace.types.Error;
import com.richard.marketplace.types.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mediator {

    static final Map<Class<?>, Object> handlers = new HashMap<>();
    static final Map<Class<?>, Class<?>> commandTypes = new HashMap<>();

    void register(Class<?> commandClass, Object handler) {
        Objects.requireNonNull(handler, "handler cannot be null");

        if (!handler.getClass().isAnnotationPresent(ForCommand.class)) {
            System.out.println("Handler Class requires @ForCommand annotation");
            return;
        }

        ForCommand annotation = handler.getClass().getAnnotation(ForCommand.class);
        Class<?> commandType = annotation.value();
        handlers.put(commandClass, handler);
        commandTypes.put(handler.getClass(), commandType);
    }

    Result<?> handle(Object command) {
        var commandClass = command.getClass();
        Object handlerObject = handlers.get(commandClass);
        if (handlerObject == null) {
            return new Error("There's no handler registered for this class");
        }

        Class<?> commandType = commandTypes.get(handlerObject.getClass());
        if (commandType == null) {
            return new Error("unable to determine type of command");
        }
        if (command.getClass() != commandType) {
            return new Error("invalid registration of command with its handler");
        }
        var commandHandler = (CommandHandler<?>) handlerObject;
        return commandHandler.handle(commandType.cast(command));
    }
}
