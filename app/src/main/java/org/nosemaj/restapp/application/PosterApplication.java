/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.application;

import android.app.Application;

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

