/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.annotation;

import org.immutables.value.Value;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * A stylization of org.immutable's generated immutables, optimized for
 * a simple static factory class.
 */
@Target({
    ElementType.PACKAGE,
    ElementType.TYPE
})
@Retention(RetentionPolicy.CLASS)
@Value.Style(
    typeAbstract = {
        "Immutable*"
    },
    typeImmutable = "*",
    allParameters = true,
    of = "create",
    defaults = @Value.Immutable(
        copy = false,
        builder = false
    )
)
public @interface SimpleFactory {}

