/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.model;

/**
 * An exception thrown when there is trouble interacting with the
 * backend.
 */
public final class ApiClientException extends RuntimeException {

    private final int code;

    /**
     * Constructs an ApiClientException.
     * @param error Message describing the exception
     * @param code Code returned from backend
     */
    public ApiClientException(final String error, final int code) {
        super(error);
        this.code = code;
    }

    /**
     * Constructs an ApiClientException.
     * @param error Message describing error
     */
    public ApiClientException(final String error) {
        this(error, -1);
    }

    /**
     * Constructs an ApiClientException.
     * @param cause Cause of error
     * @param code Code returned from backend
     */
    public ApiClientException(final Throwable cause, final int code) {
        super(cause);
        this.code = code;
    }

    /**
     * Constructs an ApiClientException.
     * @param cause Cause of error
     */
    public ApiClientException(final Throwable cause) {
        this(cause, -1);
    }

    /**
     * Gets error code returned from backend.
     * @return error code, -1 if backend did not render a response.
     */
    public int getErrorCode() {
        return code;
    }
}

