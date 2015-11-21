/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust

import android.content.Context

import nl.endran.locust.wrappers.Analytics
import nl.endran.locust.wrappers.CrashTracking
import javax.inject.Inject

class Starters @Inject constructor(private val crashTracking: CrashTracking, private val analytics: Analytics) {

    fun start(context: Context) {
        crashTracking.start(context)
        analytics.start(context)
    }
}
