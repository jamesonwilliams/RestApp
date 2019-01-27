/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import org.nosemaj.restapp.model.OnPostersAvailableListener;
import org.nosemaj.restapp.model.OnPostersNotAvailableListener;
import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.poster.single.PosterContract;

import io.reactivex.Observable;

/**
 * Contract for components of a poster list.
 */
public interface PosterListContract {

    /**
     * A visible list of posters.
     */
    interface View {

        /**
         * Clear all posters in the list.
         */
        void clearAllPosters();

        /**
         * A poster has been created, and needs to be inserted into the
         * list.
         * @param position Position in list at which to insert
         */
        void posterInsertedAt(int position);
    }

    /**
     * Presents content into the view, and responds to view's requests.
     */
    interface Presenter {

        /**
         * A view has been created.
         */
        void viewCreated();

        /**
         * A view has been destroyed.
         */
        void viewDestroyed();

        /**
         * Gets the current count of posters that should be presented.
         * @return number of posters to present
         */
        int getPosterCount();

        /**
         * Notifies the presenter that a particular poster within the
         * list is entering the window.
         * @param posterView A single poster
         * @param position Position in the list
         */
        void posterBecomingVisible(final PosterContract.View posterView, int position);
    }

    /**
     * Poster list's interactions with outside modules.
     */
    interface Interactor {

        /**
         * Watch the data model for new posters.
         * @return An observable stream of posters
         */
        Observable<Poster> observePosters();
    }
    
    // Model = Poster
}

