package com.richard.marketplace.cqrs.annotations;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

@Documented
@Inherited
@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventSourcingHandler {
}
