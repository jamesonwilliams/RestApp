/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.util;

/**
 * Preconditions are checked to complete prior to performing other
 * logic.
 */
public final class Preconditions {

    /**
     * No instances.
     */
    private Preconditions() {
    }

    /**
     * Ensure that an object is not null.
     * @param object An object, maybe null
     * @param message Message to throw in exception if object is null
     */
    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }
}
