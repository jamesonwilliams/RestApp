/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.nosemaj.restapp.util.RandomString;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests the {@link CachingPostersProvider}.
 */
public final class CachingPostersProviderTest {

    /**
     * A mock list of posters.
     */
    private static final List<Poster> POSTERS = Arrays.asList(
        Poster.create(RandomString.string(), RandomString.string()),
        Poster.create(RandomString.string(), RandomString.string()),
        Poster.create(RandomString.string(), RandomString.string())
    );

    private ApiClient apiClient;
    private PostersProvider.OnPostersAvailableListener onAvailable;
    private PostersProvider.OnPostersNotAvailableListener onError;

    private PostersProvider postersProvider;

    @Before
    public void setup() {
        apiClient = mock(ApiClient.class);
        postersProvider = new CachingPostersProvider(apiClient);

        onAvailable = mock(PostersProvider.OnPostersAvailableListener.class);
        onError = mock(PostersProvider.OnPostersNotAvailableListener.class);
    }

    @SuppressWarnings("unchecked") // List.class
    @Test
    public void getPosters_NeedMoreThanHas_ApiSucceeds_ReturnsPosters() {
        // Arrange API to return POSTERS
        when(apiClient.getPosters(anyInt())).thenReturn(POSTERS);

        // Act: ask provider for some posters.
        postersProvider.getPosters(POSTERS.size(), onAvailable, onError);

        // Verify the API was called
        verify(apiClient, times(1)).getPosters(anyInt());

        // Verify that posters were available
        final ArgumentCaptor<List<Poster>> postersCaptor =
            ArgumentCaptor.forClass(List.class);
        verify(onAvailable, times(1)).onPostersAvailable(postersCaptor.capture());

        // And that they were the posters from the API.
        assertEquals(POSTERS, postersCaptor.getValue());

        // And that onError is not invoked ever
        verifyZeroInteractions(onError);
    }

    @SuppressWarnings("unchecked") // List.class
    @Test
    public void getPosters_AlreadyHasEnough_DoesNotCallApi_ReturnsPosters() {
        // Arrange API to return POSTERS
        when(apiClient.getPosters(anyInt())).thenReturn(POSTERS);

        // Arrange: we already have posters.
        postersProvider.getPosters(POSTERS.size(), onAvailable, onError);

        // Reset api client for next checks
        reset(apiClient);
        reset(onAvailable);

        // ACT: get posters again, after they've already loaded
        postersProvider.getPosters(POSTERS.size(), onAvailable, onError);

        // Assert that API client was not invoked
        verifyZeroInteractions(apiClient);

        // But none the less, we have posters!
        final ArgumentCaptor<List<Poster>> postersCaptor =
            ArgumentCaptor.forClass(List.class);
        verify(onAvailable, times(1)).onPostersAvailable(postersCaptor.capture());
        assertEquals(POSTERS, postersCaptor.getValue());
    }

    @Test
    public void getPosters_NeedsMoreThanHas_ApiFails_InvokesErrorCallback() {

        // Arrange bad response from api client
        final Throwable expectedException =
            new ApiClientException("No such file", 400);
        doThrow(expectedException)
            .when(apiClient)
            .getPosters(anyInt());

        // Try to get some posters.
        postersProvider.getPosters(POSTERS.size(), onAvailable, onError);

        // onError is called
        final ArgumentCaptor<Throwable> errorCaptor =
            ArgumentCaptor.forClass(Throwable.class);
        verify(onError, times(1)).onPostersNotAvailable(errorCaptor.capture());

        // onError provided same error that was thrown
        assertEquals(expectedException, errorCaptor.getValue());
    }
}
