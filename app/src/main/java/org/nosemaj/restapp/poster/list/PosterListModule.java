/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import android.content.Context;

import org.nosemaj.restapp.model.PostersProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

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
            final Executor executor, final PostersProvider postersProvider) {
        return new PosterListInteractor(executor, postersProvider);
    }

    /**
     * Provides a background executor.
     * @return An executor
     */
    @Singleton
    @Provides
    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}

