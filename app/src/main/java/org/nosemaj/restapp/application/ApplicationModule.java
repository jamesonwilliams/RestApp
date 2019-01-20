/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides generic application components.
 */
@Module
public final class ApplicationModule {

    /**
     * Provides a context.
     * @param application An application
     * @return A context
     */
    @Provides
    @Singleton
    public static Context provideContext(final Application application) {
        return (Context) application;
    }
}

