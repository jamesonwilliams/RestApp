/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

/**
 * Called when posters are not available, because of an error.
 */
@FunctionalInterface
public interface OnPostersNotAvailableListener {

    /**
     * Posters are not available.
     * @param error What went wrong
     */
    void onPostersNotAvailable(final Throwable error);
}


