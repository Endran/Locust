/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.view.View
import android.widget.Button
import android.widget.TextView
import rx.Observable

data class GameUnitUI constructor(
        val view : View,
        val nameTextView: TextView,
        val currentProduceTextView: TextView,
        val spawnCostTextView: TextView,
        val spawnOneButton : Button,
        val spawn50PercentButton : Button,
        val spawn100PercentButton : Button
)