/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.model.PostersProvider;

import java.util.List;

import dagger.Lazy;

import io.reactivex.Observable;

/**
 * A Poster list interactor.
 */
final class PosterListInteractor implements PosterListContract.Interactor {

    private final int DEFAULT_POSTER_COUNT = 8;

    private final Lazy<PostersProvider> postersProvider;

    /**
     * Constructs a poster list interactor.
     */
    PosterListInteractor(final Lazy<PostersProvider> postersProvider) {
        this.postersProvider = postersProvider;
    }

    @Override
    public Observable<Poster> observePosters() {
        return Observable.create(emitter -> {
            postersProvider.get().getPosters(
                DEFAULT_POSTER_COUNT,
                posters -> Observable.fromIterable(posters)
                    .forEach(poster -> emitter.onNext(poster)),
                error -> emitter.onError(error)
            );
        });
    }
}
