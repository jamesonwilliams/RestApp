/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.util;

import org.junit.Test;

/**
 * Tests the {@link Preconditions}.
 */
public final class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void notNull_NullObject_ThrowsNullPointer() {
        Preconditions.notNull(null, "This is supposed to throw.");
    }

    @Test
    public void notNull_ValidObject_Passes() {
        Preconditions.notNull(new Object(), "This is supposed to work!");
    }
}

