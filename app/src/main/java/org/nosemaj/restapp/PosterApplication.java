/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp;

import android.app.Application;

import org.nosemaj.restapp.dagger.ApplicationComponent;
import org.nosemaj.restapp.dagger.DaggerApplicationComponent;

/**
 * Application class for posters application.
 * Primarily here to store a reference to the dagger dependency tree.
 */
public final class PosterApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build();
    }

    /**
     * Get the dependency injection component.
     * @return An application component
     */
    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

