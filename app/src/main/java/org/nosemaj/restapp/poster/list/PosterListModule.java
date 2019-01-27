/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.PostersProvider;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/**
 * PosterListModule provides components for a poster list.
 */
@Module
public final class PosterListModule {

    /**
     * Provides a poster list interactor.
     * @param executor An executor
     * @param postersProvider a posters provider
     * @return A poster list interactor
     */
    @Provides
    @Singleton
    public static PosterListContract.Interactor providePosterListInteractor(
            final Lazy<PostersProvider> postersProvider) {
        return new PosterListInteractor(postersProvider);
    }
}

