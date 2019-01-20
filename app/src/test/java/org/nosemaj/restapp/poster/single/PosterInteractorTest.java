/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.content.Context;
import android.content.Intent;

import org.nosemaj.restapp.util.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link PosterInteractor}.
 */
@RunWith(RobolectricTestRunner.class)
public final class PosterInteractorTest {

    private Context context;
    private PosterContract.Model model;
    private PosterContract.Interactor interactor;

    @Before
    public void setup() {
        context = mock(Context.class);
        model = mock(PosterContract.Model.class);
        interactor = new PosterInteractor(context, model);
    }

    @Test
    public void goToMovieDetails_FiresIntent() {
        // Arrange name of movie in model
        final String movieName = RandomString.string();
        doReturn(movieName).when(model).getMovieName();

        // Act
        interactor.goToMovieDetails(model);

        // Assert: intent is fired
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(context, times(1)).startActivity(intentCaptor.capture());

        // And it is an intent to search for the movie.
        final Intent intent = intentCaptor.getValue();
        assertEquals(Intent.ACTION_VIEW, intent.getAction());
        final String actualUri = intent.getData().toString();
        assertEquals("https://www.google.com/search?q=" + movieName, actualUri);
    }
}

