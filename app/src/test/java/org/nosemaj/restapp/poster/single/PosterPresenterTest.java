/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import org.nosemaj.restapp.util.RandomString;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@link PosterPresenter}.
 */
public final class PosterPresenterTest {

    private static final PosterContract.Model MODEL =
        PosterModel.create(RandomString.string(), RandomString.string());

    private PosterContract.View view;
    private PosterContract.Interactor interactor;
    private PosterContract.Presenter presenter;

    @Before
    public void setup() {
        view = mock(PosterContract.View.class);
        interactor = mock(PosterContract.Interactor.class);
        presenter = new PosterPresenter(view, interactor);

        doReturn(MODEL).when(interactor).getPoster();
    }

    @Test
    public void displayPoster_InvokesExpectedViewMethods() {
        presenter.displayPoster();
        verify(view, times(1)).setMovieTitle(eq(MODEL.getMovieName()));
        verify(view, times(1)).setPosterBackground(eq(MODEL.getPosterArtUrl()));
    }

    @Test
    public void posterClicked_SignalsInteractor() {
        presenter.posterClicked();
        verify(interactor, times(1)).goToMovieDetails(eq(MODEL));
    }
}

