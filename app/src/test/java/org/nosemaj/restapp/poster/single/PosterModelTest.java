/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import org.nosemaj.restapp.util.RandomString;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link PosterModel}.
 */
public final class PosterModelTest {

    @Test(expected = NullPointerException.class)
    public void create_NullMovieName_Throws() {
        PosterModel.create(null, RandomString.string());
    }

    @Test(expected = NullPointerException.class)
    public void create_NullPosterArtUrl_Throws() {
        PosterModel.create(RandomString.string(), null);
    }

    @Test
    public void create_NonNullArgs_ReturnsExpectedModel() {
        final String movieName = RandomString.string();
        final String imageUrl = RandomString.string();
        final PosterModel model = PosterModel.create(movieName, imageUrl);
        assertEquals(movieName, model.getMovieName());
        assertEquals(imageUrl, model.getPosterArtUrl());
    }
}
