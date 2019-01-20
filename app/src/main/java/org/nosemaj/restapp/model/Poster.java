/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.nosemaj.restapp.util.Preconditions;

/**
 * A Poster for a movie.
 */
public final class Poster {

    private final String movieName;
    private final String posterImageUrl;

    /**
     * Creates a poster.
     * @param movieName Name of movie
     * @param posterImageUrl URL to movie poster image
     */
    public static Poster create(final String movieName, final String posterImageUrl) {
        Preconditions.notNull(movieName, "Null movie name; won't make poster.");
        Preconditions.notNull(posterImageUrl, "Null poster image url; won't make poster.");
        return new Poster(movieName, posterImageUrl);
    }

    /**
     * Constructs a movie.
     * @param movieName name of movie
     * @param posterImageUrl URL to movie poster image
     */
    private Poster(final String movieName, final String posterImageUrl) {
        this.movieName = movieName;
        this.posterImageUrl = posterImageUrl;
    }

    /**
     * Gets the movie name.
     * @return Name of movie
     */
    public String getMovieName() {
        return this.movieName;
    }

    /**
     * Gets the movie poster image url.
     * @return URL to movie poster image
     */
    public String getPosterImageUrl() {
        return this.posterImageUrl;
    }
}

