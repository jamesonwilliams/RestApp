/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import java.util.List;

/**
 * Models the operations available from a backend API.
 */
public interface ApiClient {

    /**
     * Gets posters from backend.
     * @param posterCount count of posters to get
     * @return A list of posters, of length posterCount
     * @throws ApiClientException If posters cannot be obtained
     */
    List<Poster> getPosters(final int posterCount) throws ApiClientException;
}

