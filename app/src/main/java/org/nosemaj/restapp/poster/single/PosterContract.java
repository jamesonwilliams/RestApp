/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.support.annotation.NonNull;

/**
 * An MVP module for a single Poster, with an interactor to handle
 * interactions with external modules.
 */
public interface PosterContract {

    /**
     * The elements of a Poster screen that are available to the user to
     * look at and touch.
     */
    public interface View {

        /**
         * Sets the title of the movie.
         * @param movieTitle
         */
        void setMovieTitle(@NonNull String movieTitle);

        /**
         * Sets the background of the poster.
         */
        void setPosterBackground(@NonNull String imageUrl);

        /**
         * Sets a callback to be invoked when the view is clicked.
         * @param onPosterClickedListener Callback when poster is clicked
         */
        void setOnPosterClickedListener(@NonNull OnPosterClickedListener onPosterClickedListener);
    }

    /**
     * Presenter contract, between view and interactor.
     */
    public interface Presenter {

        /**
         * The user clicked somewhere on the poster.
         */
        void posterClicked();

        /**
         * Displays a poster into the view.
         */
        void displayPoster();
    }

    /**
     * Interactions between the poster and other modules.
     */
    public interface Interactor {

        /**
         * Navigate away from this poster, to a page that displays
         * details about the movie described in the poster.
         */
        void goToMovieDetails(@NonNull Model model);

        /**
         * Gets the current poster data model.
         */
        @NonNull
        Model getPoster();
    }

    /**
     * A data model for the visible poster.
     */
    public interface Model {

        /**
         * Gets a URL to an image to a poster's main art.
         */
        @NonNull
        String getPosterArtUrl();

        /**
         * Gets the name of the movie being displayed.
         */
        @NonNull
        String getMovieName();
    }
}

