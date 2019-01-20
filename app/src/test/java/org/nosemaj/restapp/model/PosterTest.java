/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.nosemaj.restapp.util.RandomString;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link Poster} model.
 */
public final class PosterTest {

    @Test
    public void createPoster_ReturnsPosterWithExpectedState() {
        final String movieName = RandomString.string();
        final String posterImageUrl = RandomString.string();
        final Poster poster = Poster.create(movieName, posterImageUrl);
        assertEquals(movieName, poster.getMovieName());
        assertEquals(posterImageUrl, poster.getPosterImageUrl());
    }

    @Test(expected = NullPointerException.class)
    public void createPoster_NullMovieName_Throws() {
        Poster.create(null, RandomString.string());
    }

    @Test(expected = NullPointerException.class)
    public void createPoster_NullPosterImageUrl_Throws() {
        Poster.create(RandomString.string(), null);
    }
}

