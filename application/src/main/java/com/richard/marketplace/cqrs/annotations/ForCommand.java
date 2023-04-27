package com.richard.marketplace.cqrs.annotations;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForCommand {

    Class<?> value();
}
