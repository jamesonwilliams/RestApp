/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

/**
 * A callback that is invoked when a poster is clicked.
 */
@FunctionalInterface
public interface OnPosterClickedListener {

    /**
     * Called when a poster is clicked.
     */
    void onPosterClicked();
}

