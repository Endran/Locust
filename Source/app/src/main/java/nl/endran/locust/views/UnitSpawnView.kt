/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.bindView
import nl.endran.locust.R
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.injections.getAppComponent

class UnitSpawnView(context: Context?, attrs: AttributeSet?) : CardView(context, attrs) {

    val textViewCost: TextView by bindView(R.id.textViewCost)

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.view_unit_spawn, this, true)

        //        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ManualEntryItem)
        //        applyCustomAttributes(attributes)
    }

    public fun setGameUnit(gameUnit: GameUnit<*>) {
        val stringResourceMap = context.getAppComponent().stringResourceMap
        val costText = gameUnit.spawnCostList
                .map { "${it.cost} ${stringResourceMap.getString(it.gameUnit.javaClass)}" }
                .reduce { s1, s2 -> "$s1, $s2" }
        textViewCost.text = context.getString(R.string.spawn_cost, costText)
    }
}