/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import org.nosemaj.restapp.model.OnPostersAvailableListener;
import org.nosemaj.restapp.model.OnPostersNotAvailableListener;
import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.poster.single.PosterContract;

import java.util.List;

/**
 * Contract for components of a poster list.
 */
public interface PosterListContract {

    /**
     * A visible list of posters.
     */
    interface View {

        /**
         * Called to notify the view it is no longer displaying
         * an up-to-date poster list.
         */
        void invalidateView();
    }

    /**
     * Presents content into the view, and responds to view's requests.
     */
    interface Presenter {

        /**
         * Present new posters into the view, if needed.
         */
        void refreshPosters();

        /**
         * Present a single poster view into a position of the list view.
         * @param poster A single poster view
         * @param position The position at which it should be shown, in
         *                 the visible list
         */
        void showPosterAtPosition(PosterContract.View poster, int position);

        /**
         * Gets the number of items in the poster list.
         * @return length of poster list
         */
        int getListLength();
    }

    /**
     * Poster list's interactions with outside modules.
     */
    interface Interactor {

        /**
         * Get a list of posters to show.
         * @param onAvailable Callback when posters are available
         * @param onError Callback when posters are not available
         */
        void getPosters(
            OnPostersAvailableListener onAvailable,
            OnPostersNotAvailableListener onError);
    }
    
    // Model = Poster
}

