/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.poster.single.PosterContract;
import org.nosemaj.restapp.util.RandomString;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link PosterListPresenter}.
 */
public final class PosterListPresenterTest {

    private static final List<Poster> POSTERS = Arrays.asList(
        Poster.create(RandomString.string(), RandomString.string()),
        Poster.create(RandomString.string(), RandomString.string())
    );

    private Context context;
    private PosterListContract.View view;
    private PosterListContract.Interactor interactor;

    // Object under test
    private PosterListContract.Presenter presenter;

    @Before
    public void setup() {
        context = mock(Context.class);
        interactor = mock(PosterListContract.Interactor.class);
        view = mock(PosterListContract.View.class);
        presenter = new PosterListPresenter(view, interactor, context);

        doAnswer(invocation -> {
            final OnPostersAvailableListener listener =
                (OnPostersAvailableListener) (invocation.getArguments()[0]);
            listener.onPostersAvailable(POSTERS);
            return null;
        })
        .when(interactor)
        .getPosters(any(), any());
    }

    @Test
    public void showPosterAtPosition_CausesChildToPresent() {
        // Arrange view models
        presenter.refreshPosters();

        // Act: show a poster at a position
        final PosterContract.View view = mock(PosterContract.View.class);
        presenter.showPosterAtPosition(view, 0);
        verify(view, times(1)).setMovieTitle(anyString());
    }

    @Test
    public void getListLength_MatchesViewModel() {
        // Populate the view models
        presenter.refreshPosters();

        // Act: how big is the list?
        final int actualLength = presenter.getListLength();

        // List length should match arranged number of posters
        assertEquals(POSTERS.size(), actualLength);
    }

    @Test
    public void viewIsInvalidted_WhenPostersAvailable() {
        // Act: refresh posters
        presenter.refreshPosters();

        // Assert View is invalidted
        verify(view, times(1)).invalidateView();
    }

    @Test
    public void viewIsInvalidated_WhenPostersAreNotAvailable() {
        doAnswer(invocation -> {
            final OnPostersNotAvailableListener listener =
                (OnPostersNotAvailableListener) (invocation.getArguments()[1]);
            listener.onPostersNotAvailable(new RuntimeException("uh oh"));
            return null;
        })
        .when(interactor)
        .getPosters(any(), any());

        presenter.refreshPosters();

        verify(view, times(1)).invalidateView();
        assertEquals(0, presenter.getListLength());
    }

    @Test
    public void refreshPosters_GetsPosters() {
        presenter.refreshPosters();
        verify(interactor, times(1)).getPosters(any(), any());
    }
}

