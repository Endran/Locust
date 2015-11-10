/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.wrappers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;
import javax.inject.Singleton;

import nl.endran.locust.BuildConfig;

@Singleton
public class CrashTracking {

    @NonNull
    private FabricFactory fabricFactory;

    @Inject
    public CrashTracking(@NonNull final FabricFactory fabricFactory) {
        this.fabricFactory = fabricFactory;
    }

    public void start(@NonNull final Context context) {
        if (shouldTrack()) {
            fabricFactory.create(context, new Crashlytics());
        }
    }

    private boolean shouldTrack() {
        return BuildConfig.VERSION_NAME.contains("master");
    }
}
