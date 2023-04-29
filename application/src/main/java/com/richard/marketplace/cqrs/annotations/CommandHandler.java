package com.richard.marketplace.cqrs.annotations;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

@Documented
@IndexAnnotated
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface CommandHandler {
}
