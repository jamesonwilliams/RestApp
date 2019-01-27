/**
 * Copyright (c) nosemaj.org, 2019.
 */

package org.nosemaj.restapp.application;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;

/**
 * Utility for tracing method invocations.
 */
public final class Trace {

    private static final String LOG_FILE = "/sdcard/trace.log";
    private static final int BUFFER_SIZE = 300_000_000;
    private static final long RECORD_DURATION_MS = 20_000L /* ms */;

    /**
     * No instances.
     */
    private Trace() {
    }

    /**
     * Begin tracing method invocations.
     */
    public static void it() {
        Debug.startMethodTracing(LOG_FILE, BUFFER_SIZE);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            android.os.Debug.stopMethodTracing();
        }, RECORD_DURATION_MS);
    }
}

