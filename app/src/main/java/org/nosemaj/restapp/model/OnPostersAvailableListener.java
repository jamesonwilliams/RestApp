/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import java.util.List;

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

