package com.richard.marketplace.cqrs.annotations;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@IndexAnnotated
public @interface AggregateIdentifier {
}
