/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a list of posters. Use an in-memory cache to prevent excess
 * REST calls. Synchronous, blocking implementation that must *NOT* be
 * used on main thread.
 */
public final class CachingPostersProvider implements PostersProvider {

    private final ApiClient apiClient;
    private final List<Poster> availablePosters;

    /**
     * Constructs a CachingPostersProvider.
     * @param apiClient An API client
     */
    public CachingPostersProvider(final ApiClient apiClient) {
        this.apiClient = apiClient;
        this.availablePosters = new ArrayList<>();
    }

    @Override
    public void getPosters(
            int howMany,
            OnPostersAvailableListener onAvailable,
            OnPostersNotAvailableListener onNotAvailable) {

        // Do we need to get more posters?
        final int countStillNeeded = howMany - availablePosters.size();

        // No, just callback listener.
        if (countStillNeeded <= 0) {
            onAvailable.onPostersAvailable(availablePosters.subList(0, howMany));
            return;
        }

        // Yes, we need more. Use the API client to fill the list.
        try {
            availablePosters.addAll(apiClient.getPosters(countStillNeeded));
            // All good; have the request count of posters!
            onAvailable.onPostersAvailable(availablePosters);
        } catch (final ApiClientException clientException) {
            // API client failed; tell callback we can't fulfill the request
            onNotAvailable.onPostersNotAvailable(clientException);
        }
    }
}

