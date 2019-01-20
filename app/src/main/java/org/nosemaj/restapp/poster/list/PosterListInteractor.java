/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.OnPostersAvailableListener;
import org.nosemaj.restapp.model.OnPostersNotAvailableListener;
import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.model.PostersProvider;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * A Poster list interactor.
 */
final class PosterListInteractor implements PosterListContract.Interactor {

    private final int DEFAULT_POSTER_COUNT = 8;

    private final Executor backgroundExecutor;
    private final PostersProvider postersProvider;

    /**
     * Constructs a poster list interactor.
     * @param backgroundExecutor A background executor
     */
    PosterListInteractor(
            final Executor backgroundExecutor, final PostersProvider postersProvider) {
        this.backgroundExecutor = backgroundExecutor;
        this.postersProvider = postersProvider;
    }

    @Override
    public void getPosters(
            final OnPostersAvailableListener onAvailable,
            final OnPostersNotAvailableListener onError) {

        backgroundExecutor.execute(() -> {
            postersProvider.getPosters(
                DEFAULT_POSTER_COUNT,
                posters -> onAvailable.onPostersAvailable(posters),
                error -> onError.onPostersNotAvailable(error)
            );
        });
    }
}
