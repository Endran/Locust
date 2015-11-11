/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import nl.endran.locust.injections.AppComponent;
import nl.endran.locust.injections.AppGraph;
import nl.endran.locust.injections.AppModule;
import nl.endran.locust.injections.DaggerAppComponent;

public class App extends Application implements AppGraph {

    private AppComponent appComponent;

    @Inject
    Starters starters;

    @Override
    public void onCreate() {
        super.onCreate();

        initGraph();

        appComponent.inject(this);
        starters.start(this);
    }

    private void initGraph() {

        AppModule appModule = new AppModule(this, new Handler());

        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(appModule)
                    .build();
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
