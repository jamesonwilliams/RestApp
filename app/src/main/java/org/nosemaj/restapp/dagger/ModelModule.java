/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.dagger;

import org.nosemaj.restapp.model.ApiClient;
import org.nosemaj.restapp.model.CachingPostersProvider;
import org.nosemaj.restapp.model.NosemajApiClient;
import org.nosemaj.restapp.model.PostersProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;

/**
 * Provides dependencies from the model subpackage.
 */
@Module
public final class ModelModule {

    @Singleton
    @Provides
    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    /**
     * Provides an ApiClient.
     * @param okHttpClient An OkHttpClient
     * @return ApiClient instance
     */
    @Singleton
    @Provides
    public static ApiClient provideApiClient(final OkHttpClient okHttpClient) {
        return new NosemajApiClient(okHttpClient);
    }

    /**
     * Provides a poster provider.
     * @param apiClient An api client
     * @return PostersProvider instance
     */
    @Singleton
    @Provides
    public static PostersProvider providePostersProvider(final ApiClient apiClient) {
        return new CachingPostersProvider(apiClient);
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

