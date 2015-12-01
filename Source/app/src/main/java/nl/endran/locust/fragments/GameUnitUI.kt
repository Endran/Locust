/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.TextView

data class GameUnitUI constructor(
        val view: View,
        val textViewName: TextView,
        val textViewCount: TextView,
        val textViewMultiplier: TextView,
        val textViewProduce: TextView,
        val fabSpawn: FloatingActionButton
)