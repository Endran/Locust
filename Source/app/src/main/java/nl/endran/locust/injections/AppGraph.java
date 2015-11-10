/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.injections;

import android.support.annotation.NonNull;

public interface AppGraph {

    @NonNull
    AppComponent getAppComponent();
}
