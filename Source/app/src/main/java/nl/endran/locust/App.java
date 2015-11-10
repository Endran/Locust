/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust;

import android.app.Application;

import nl.endran.locust.wrappers.Analytics;
import nl.endran.locust.wrappers.CrashTracking;
import nl.endran.locust.wrappers.FabricFactory;
import nl.endran.locust.wrappers.GoogleAnalyticsFactory;

public class App extends Application {

    private Analytics analytics;

    @Override
    public void onCreate() {
        super.onCreate();

        initCrashTracking();
        initAnalytics();
    }

    private void initCrashTracking() {
        CrashTracking crashTracking = new CrashTracking(new FabricFactory());
        crashTracking.start(this);
    }

    private void initAnalytics() {
        analytics = new Analytics(this, new GoogleAnalyticsFactory());
    }

    public Analytics getAnalytics() {
        return analytics;
    }
}
