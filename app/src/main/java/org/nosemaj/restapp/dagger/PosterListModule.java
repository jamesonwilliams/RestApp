/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.dagger;

import android.content.Context;

import org.nosemaj.restapp.model.PostersProvider;
import org.nosemaj.restapp.poster.list.PosterListAdapter;

import java.util.concurrent.Executor;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * PosterListModule provides components for a poster list.
 */
@Module
public final class PosterListModule {

    /**
     * Provides a poster list adapter.
     * @param context An Android Context
     * @param postersProvider a posters provider
     * @param executor An executor
     * @return A poster list adapter
     */
    @Provides
    @Singleton
    public static PosterListAdapter providePosterListAdapter(
            final Context context, final PostersProvider postersProvider, final Executor executor) {
        return new PosterListAdapter(context, postersProvider, executor);
    }
}

