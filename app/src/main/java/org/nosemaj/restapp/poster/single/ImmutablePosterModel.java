/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import org.nosemaj.restapp.annotation.SimpleFactory;

import org.immutables.value.Value;

/**
 * Data model for a poster view.
 */
@Value.Immutable
@SimpleFactory
public interface ImmutablePosterModel extends PosterContract.Model {

    /**
     * Gets the movie name.
     * @return movie name
     */
    @Override
    public String getMovieName();

    /**
     * Gets the poster art url.
     * @return poster art url
     */
    @Override
    public String getPosterArtUrl();
}

