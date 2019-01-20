/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.util;

import java.util.UUID;

/**
 * Java {@link java.util.Random} doesn't have a random string method.
 * But we do, here, as a test utility.
 */
public final class RandomString {

    /**
     * No instances.
     */
    private RandomString() {
    }

    /**
     * Gets a random string.
     * @return a random string
     */
    public static String string() {
        return UUID.randomUUID().toString();
    }
}

