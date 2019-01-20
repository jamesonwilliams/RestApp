/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

import org.nosemaj.restapp.util.RandomString;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ApiClientException}.
 */
public final class ApiClientExceptionTest {

    private Random random;

    @Before
    public void setup() {
        random = new Random();
    }

    @Test(expected = ApiClientException.class)
    public void canConstruct_FromMessageAndCode() {
        final String message = RandomString.string();
        final int code = random.nextInt();
        final ApiClientException exception = new ApiClientException(message, code);
        assertEquals(code, exception.getErrorCode());
        throw exception;
    }

    @Test(expected = ApiClientException.class)
    public void canConstruct_FromMessageOnly() {
        final String message = RandomString.string();
        final ApiClientException exception = new ApiClientException(message);
        assertEquals(-1, exception.getErrorCode());
        throw exception;
    }

    @Test(expected = ApiClientException.class)
    public void canConstruct_FromThrowableAndCode() {
        final Throwable throwable = new RuntimeException("Uh oh.");
        final int code = random.nextInt();
        final ApiClientException exception = new ApiClientException(throwable, code);
        assertEquals(code, exception.getErrorCode());
        throw exception;
    }

    @Test(expected = ApiClientException.class)
    public void canConstruct_FromThrowableOnly() {
        final Throwable throwable = new RuntimeException("Uh oh.");
        final ApiClientException exception = new ApiClientException(throwable);
        assertEquals(-1, exception.getErrorCode());
        throw exception;
    }
}
