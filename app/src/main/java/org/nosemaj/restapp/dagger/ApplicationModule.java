/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.dagger;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

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

    /**
     * Provides a linear layout manager.
     * @param context An Android Context
     * @return LinearLayoutManager
     */
    @Provides
    @Singleton
    public static LinearLayoutManager provideLinearLayoutManager(final Context context) {
        return new LinearLayoutManager(context);
    }
}

