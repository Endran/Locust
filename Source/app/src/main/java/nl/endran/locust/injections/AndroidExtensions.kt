/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.injections

import android.content.Context
import android.view.View

fun Context.getAppComponent(): AppComponent {
    return (applicationContext as AppGraph).appComponent
}

fun <T> View.findTypedViewById(id: Int): T {
    return findViewById(id) as T
}
