/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.nosemaj.restapp.annotation.SimpleFactory;

import org.immutables.value.Value;

/**
 * A Poster for a movie.
 */
@Value.Immutable
@SimpleFactory
public interface ImmutablePoster {

    /**
     * Gets the movie name.
     * @return Name of movie
     */
    public String getMovieName();

    /**
     * Gets the movie poster image url.
     * @return URL to movie poster image
     */
    public String getPosterImageUrl();
}

