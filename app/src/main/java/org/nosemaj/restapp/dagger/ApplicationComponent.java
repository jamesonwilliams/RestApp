/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.dagger;

import android.app.Application;

import org.nosemaj.restapp.poster.list.PosterListActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * An ApplicationComponent is a dependency graph, build from provided
 * dependencies in modules.
 */
@Singleton
@Component(modules = {
    ApplicationModule.class,
    ModelModule.class,
    PosterListModule.class
})
public interface ApplicationComponent {

    /**
     * Builds an application component.
     */
    @Component.Builder
    interface Builder {

        /**
         * Bind an application instance to the component.
         * @param application An application instance
         */
        @BindsInstance
        Builder application(Application application);

        /**
         * Builds the component.
         * @return ApplicationComponent
         */
        ApplicationComponent build();
    }

    /**
     * Inject dependencies into a poster list activity.
     * @param activity A poster list activity
     */
    void inject(PosterListActivity activity);
}

