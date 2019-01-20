/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.nosemaj.restapp.util.RandomString;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link NosemajApiClient}.
 */
@RunWith(RobolectricTestRunner.class)
public final class NosemajApiClientTest {

    private static final int HOW_MANY_TO_GET = 10;

    // Dependency mocks
    private OkHttpClient okHttpClient;
    private Call call;

    // Under test
    private ApiClient apiClient;

    @Before
    public void setup() {
        // Default mocks OkHttpClient responses
        okHttpClient = mock(OkHttpClient.class);
        call = mock(Call.class);
        doReturn(call).when(okHttpClient).newCall(any(Request.class));

        // Object under test.
        apiClient = new NosemajApiClient(okHttpClient);
    }

    @Test
    public void getPosters_ValidJson_ReturnsPostersList() throws JSONException {
        // Arrange valid response from server
        final String movieName1 = RandomString.string();
        final String movieName2 = RandomString.string();
        final String backgroundImageUrl1 = RandomString.string();
        final String backgroundImageUrl2 = RandomString.string();

        mockResponse(200, new JSONObject()
            .put("posters", new JSONArray()
                .put(new JSONObject()
                    .put("movieName", movieName1)
                    .put("backgroundImageUrl", backgroundImageUrl1)
                )
                .put(new JSONObject()
                    .put("movieName", movieName2)
                    .put("backgroundImageUrl", backgroundImageUrl2)
                )
            )
            .toString());

        // Act: get some posters.
        final List<Poster> posters = apiClient.getPosters(HOW_MANY_TO_GET);

        // Assert: actual posters are expected posters
        assertEquals(2, posters.size());
        assertEquals(movieName1, posters.get(0).getMovieName());
        assertEquals(backgroundImageUrl1, posters.get(0).getPosterImageUrl());
        assertEquals(movieName2, posters.get(1).getMovieName());
        assertEquals(backgroundImageUrl2, posters.get(1).getPosterImageUrl());
    }

    @Test(expected = ApiClientException.class)
    public void getPosters_MalformedJson_ThrowsApiClientException() {
        // Server responds, but it sends us garbage.
        mockResponse(200, RandomString.string());

        // Act: try to get posters, but fail
        apiClient.getPosters(HOW_MANY_TO_GET);
    }

    @Test(expected = ApiClientException.class)
    public void getPosters_ClientError_ThrowsApiClientException() throws JSONException {
        // Server returns a 4xx.
        mockResponse(400, new JSONObject()
            .put("error", "Bad resource / 400")
            .toString());

        // Act: try to get posters; fail
        apiClient.getPosters(HOW_MANY_TO_GET);
    }

    @Test(expected = ApiClientException.class)
    public void getPosters_ServerError_ThrowsApiClientException() throws JSONException {
        // Server returns a 5xx.
        mockResponse(500, new JSONObject()
            .put("error", "Something went wrong on server.")
            .toString());

        // Act: try to get posters; fail.
        apiClient.getPosters(HOW_MANY_TO_GET);
    }

    /**
     * Mocking OkHttp is messy business. Do it once, in a utility method.
     * @param code http status code
     * @param bodyContent String in response body
     */
    private void mockResponse(final int code, final String bodyContent) {
        try {
            when(call.execute()).thenReturn(new Response.Builder()
                .body(ResponseBody.create(MediaType.parse("application/json"), bodyContent))
                .request(new Request.Builder().url("https://dummy.com").build())
                .protocol(Protocol.HTTP_1_1)
                .message(RandomString.string())
                .code(code)
                .build());
        } catch (final IOException badMockException) {
            throw new RuntimeException(badMockException);
        }
    }
}

