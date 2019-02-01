/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import java.util.List;

/**
 * Provides a list of posters.
 */
@FunctionalInterface
public interface PostersProvider {

    /**
     * Get posters, async.
     * @param howMany How many posters to get
     * @param onPosters Called when posters are available
     * @param onError Called when error is received
     */
    void getPosters(
            int howMany,
            OnPostersAvailableListener onAvailable,
            OnPostersNotAvailableListener onNotAvailable);

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

    /**
     * Called when posters are available.
     */
    @FunctionalInterface
    public interface OnPostersAvailableListener {

        /**
         * Posters are available.
         * @param posters Available movie posters
         */
        void onPostersAvailable(final List<Poster> posters);
    }
}

