/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.single;

import android.support.annotation.NonNull;

import org.nosemaj.restapp.util.Preconditions;

/**
 * PosterPresenter gets poster view models from the interactor, and then
 * maps them into the view. Likewise, PosterPresenter responds to a
 * user's interaction with a view, by invoking an interaction for it.
 */
public final class PosterPresenter implements PosterContract.Presenter {

    private final PosterContract.View posterView;
    private final PosterContract.Interactor posterInteractor;

    /**
     * Constructs a new PosterPresenter.
     * @param posterView Poster view
     * @param posterInteractor Poster interactor
     */
    public PosterPresenter(
            @NonNull final PosterContract.View posterView,
            @NonNull final PosterContract.Interactor posterInteractor) {
        Preconditions.notNull(posterView, "Passed null poster view to poresenter.");
        Preconditions.notNull(posterInteractor, "Passed null interactor to presenter.");
        this.posterView = posterView;
        this.posterInteractor = posterInteractor;
    }

    @Override
    public void displayPoster() {
        final PosterContract.Model posterModel = posterInteractor.getPoster();
        posterView.setMovieTitle(posterModel.getMovieName());
        posterView.setPosterBackground(posterModel.getPosterArtUrl());
    }

    @Override
    public void posterClicked() {
        posterInteractor.goToMovieDetails(posterInteractor.getPoster());
    }
}

