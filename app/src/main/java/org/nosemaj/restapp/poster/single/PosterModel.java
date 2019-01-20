/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.support.annotation.NonNull;

import org.nosemaj.restapp.util.Preconditions;

/**
 * Data model for a poster view.
 */
public final class PosterModel implements PosterContract.Model {

    private final String posterArtUrl;
    private final String movieName;

    /**
     * Creates a new PosterModel.
     * @param movieName Name of the movie
     * @param posterArtUrl Poster art URL
     * @return PosterModel instance
     */
    public static PosterModel create(
            @NonNull final String movieName,
            @NonNull final String posterArtUrl) {
        Preconditions.notNull(movieName, "Passed null movie name to PosterModel factory.");
        Preconditions.notNull(posterArtUrl, "Passed null URL to PosterModel factory.");
        return new PosterModel(movieName, posterArtUrl);
    }

    /**
     * Constructs a new PosterModel.
     * @param movieName Movie name
     * @param posterArtUrl URL to poster art
     */
    private PosterModel(final String movieName, final String posterArtUrl) {
        this.movieName = movieName;
        this.posterArtUrl = posterArtUrl;
    }

    @NonNull
    @Override
    public String getMovieName() {
        return movieName;
    }

    @NonNull
    @Override
    public String getPosterArtUrl() {
        return posterArtUrl;
    }
}

