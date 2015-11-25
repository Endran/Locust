/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import android.os.Handler
import java.util.*
import javax.inject.Inject

class GameLoop
@Inject
constructor(private val handler: Handler) {

    interface Listener {
        fun onTrigger(millisSincePreviousTrigger: Int)
    }


    val DELAY_MILLIS = 10
    val TRIGGER_INTERVAL_MILLIS = 100

    private val listeners = ArrayList<Listener>()
    private var isRunning = false
    private var previousTimeMillis = 0L

    fun register(listener: Listener) {
        listeners.add(listener)
        startLazy()
    }

    fun unregister(listener: Listener) {
        listeners.remove(listener)
        stopLazy()
    }

    private fun stopLazy() {
        if (listeners.isEmpty()) {
            isRunning = false
        }
    }

    private fun startLazy() {
        if (!isRunning) {
            isRunning = true

            previousTimeMillis = System.currentTimeMillis()
            rePost()
        }
    }

    private fun rePost() {
        handler.postDelayed(Runnable {
            if (!isRunning) {
                return@Runnable
            }

            val currentTimeMillis = System.currentTimeMillis()
            val millisSincePreviousTrigger = currentTimeMillis - previousTimeMillis
            if (millisSincePreviousTrigger > TRIGGER_INTERVAL_MILLIS) {
                previousTimeMillis = currentTimeMillis
                val millisSincePreviousTriggerInt = millisSincePreviousTrigger.toInt()
                listeners.forEach { it.onTrigger(millisSincePreviousTriggerInt) }
            }

            rePost()
        }, DELAY_MILLIS.toLong())
    }
}
