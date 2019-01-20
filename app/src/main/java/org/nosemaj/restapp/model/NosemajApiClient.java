/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An API client that talks to nosemaj.org to get a simple static json
 * file. Raw OkHttpClient and "cherry-picking" of org.json data, without
 * explicit API modeling.
 */
final class NosemajApiClient implements ApiClient {

    private static final int SUCCESS = 200;
    private static final String POSTERS_URL = "https://nosemaj.org/dl/posters.json";

    private final OkHttpClient okHttpClient;

    /**
     * Constructs a new NosemajApiClient.
     * @param okHttpClient OkHttpClient
     */
    NosemajApiClient(final OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public List<Poster> getPosters(final int posterCount) throws ApiClientException {

        final Request request = new Request.Builder()
            .url(POSTERS_URL)
            .build();
  
        try (final Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new ApiClientException(response.message(), response.code());
            }
            return parseResponse(response.body().string());
        } catch (final IOException | JSONException responseException) {
            throw new ApiClientException(responseException);
        }
    }

    /**
     * Builds a list of posters from a response string.
     * @param responseString Response from backend, as string
     * @return List of posters
     * @throws JSONException if the response cannot be parsed
     */
    private static List<Poster> parseResponse(final String responseString) throws JSONException {
        final List<Poster> posters = new ArrayList<>();

        final JSONArray postersArray =
            new JSONObject(responseString).getJSONArray(JsonKeys.POSTERS_ARRAY);

        for (int index = 0; index < postersArray.length(); index++) {
            final JSONObject poster = postersArray.getJSONObject(index);
            final String movieName = poster.getString(JsonKeys.MOVIE_NAME);
            final String backgroundImageUrl = poster.getString(JsonKeys.BACKGROUND_IMAGE_URL);
            posters.add(Poster.create(movieName, backgroundImageUrl));
        }

        return posters;
    }

    /**
     * Keys found in the JSON response.
     */
    static final class JsonKeys {
        static final String POSTERS_ARRAY = "posters";
        static final String BACKGROUND_IMAGE_URL = "backgroundImageUrl";
        static final String MOVIE_NAME = "movieName";
    }
}

