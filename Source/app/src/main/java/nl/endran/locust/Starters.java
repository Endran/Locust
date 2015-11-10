/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import nl.endran.locust.wrappers.Analytics;
import nl.endran.locust.wrappers.CrashTracking;

public class Starters {

    @NonNull
    private final CrashTracking crashTracking;

    @NonNull
    private final Analytics analytics;

    @Inject
    public Starters(@NonNull final CrashTracking crashTracking, @NonNull final Analytics analytics) {
        this.crashTracking = crashTracking;
        this.analytics = analytics;
    }

    public void start(@NonNull final Context context){
        crashTracking.start(context);
        analytics.start(context);
    }
}
