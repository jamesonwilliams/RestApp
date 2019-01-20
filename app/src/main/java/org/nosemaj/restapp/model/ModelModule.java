/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

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
}

