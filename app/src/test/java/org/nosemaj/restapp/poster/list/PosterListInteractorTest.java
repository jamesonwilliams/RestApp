/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.poster.list;

import org.nosemaj.restapp.model.Poster;
import org.nosemaj.restapp.model.PostersProvider;
import org.nosemaj.restapp.util.RandomString;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Tests the {@link PosterListInteractor}.
 */
public final class PosterListInteractorTest {

    private static final Throwable EXPECTED_THROWABLE = new RuntimeException("Boo!");

    private static final List<Poster> EXPECTED_POSTERS = Arrays.asList(
        Poster.create(RandomString.string(), RandomString.string()),
        Poster.create(RandomString.string(), RandomString.string()),
        Poster.create(RandomString.string(), RandomString.string())
    );

    private Executor executor;
    private PostersProvider postersProvider;
    private PosterListContract.Interactor interactor;

    @Before
    public void setup() {
        executor = Executors.newSingleThreadExecutor();
        postersProvider = mock(PostersProvider.class);
        interactor = new PosterListInteractor(executor, postersProvider);
    }

    @Test
    public void getPosters_ReturnsPostersWhenAvailable() throws InterruptedException {
        // Mock posters providers, it just sends back the mock posters.
        doAnswer(invocation -> {
            final PostersProvider.OnPostersAvailableListener listener =
                (PostersProvider.OnPostersAvailableListener) (invocation.getArguments()[1]);
            listener.onPostersAvailable(EXPECTED_POSTERS);
            return null;
        })
        .when(postersProvider)
        .getPosters(anyInt(), any(), any());

        // Arrange a place for posters to go, if the interactor return them
        final List<Poster> actualPosters = new CopyOnWriteArrayList<>();

        // Arrange a latch to signal that posters were received (on
        // another thread).
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // Act: try to get some posters
        interactor.getPosters(posters -> {
            actualPosters.addAll(posters);
            countDownLatch.countDown();
        }, error -> {
        });

        // Wait for other thread to count us down
        countDownLatch.await(100, TimeUnit.MILLISECONDS);

        // Assert: actual posters are expected posters
        assertEquals(EXPECTED_POSTERS, actualPosters);
    }

    @Test
    public void getPosters_ReturnsErrorWhenNotAvailable() throws InterruptedException {
        // Look for exception type on other thread; cache it here.
        // Don't need to sync on this; get/set is guaranteed serial by
        // count down lock
        final AtomicReference<Throwable> actualException = new AtomicReference<>(null);

        // Arrange for posters provider to return an error callback
        doAnswer(invocation -> {
            final PostersProvider.OnPostersNotAvailableListener listener =
                (PostersProvider.OnPostersNotAvailableListener) (invocation.getArguments()[2]);
            listener.onPostersNotAvailable(EXPECTED_THROWABLE);
            return null;
        })
        .when(postersProvider)
        .getPosters(anyInt(), any(), any());

        // Arrange a latch to signal that posters were received (on
        // another thread).
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        // Act: try to get some posters
        interactor.getPosters(posters -> {
        }, error -> {
            actualException.set(error);
            countDownLatch.countDown();
        });

        // Wait for other thread to count us down
        countDownLatch.await(100, TimeUnit.MILLISECONDS);

        // Assert: actual exception was the expected one.
        assertEquals(EXPECTED_THROWABLE, actualException.get());
    }
}

