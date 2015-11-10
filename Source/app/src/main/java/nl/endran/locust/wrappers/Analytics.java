/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.wrappers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

import nl.endran.locust.BuildConfig;
import nl.endran.locust.R;

@Singleton
public class Analytics {

    @NonNull
    private final GoogleAnalyticsFactory analyticsFactory;


    @Nullable
    private Tracker tracker;
    @Inject
    public Analytics(@NonNull final GoogleAnalyticsFactory analyticsFactory) {
        this.analyticsFactory = analyticsFactory;
    }

    public void start(@NonNull final Context context) {
        if (shouldInitTracker()) {
            GoogleAnalytics googleAnalytics = analyticsFactory.create(context);
            tracker = googleAnalytics.newTracker(R.xml.global_tracker);
            tracker.enableAdvertisingIdCollection(true);
        } else {
            tracker = null;
        }
    }

    private boolean shouldInitTracker() {
        return BuildConfig.VERSION_NAME.contains("master");
    }

    public void trackPage(@NonNull String pageName) {
        if (tracker != null) {
            tracker.setScreenName(pageName);
            tracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public void trackEvent(@NonNull String eventName) {
        if (tracker != null) {
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Action")
                    .setAction(eventName)
                    .build());
        }
    }
}
