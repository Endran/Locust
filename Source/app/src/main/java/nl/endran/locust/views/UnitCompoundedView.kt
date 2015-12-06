/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import butterknife.bindView
import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.R
import nl.endran.locust.game.units.GameUnit
import rx.Subscription

class UnitCompoundedView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val unitDetailView: UnitDetailView by bindView(R.id.unitDetailView)
    val unitSpawnView: UnitSpawnView by bindView(R.id.unitSpawnView)
    val fabSpawn: FloatingActionButton by bindView(R.id.fabSpawn)

    var subscription: Subscription? = null

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.view_unit_compounded, this, true)
    }

    public fun prepare(gameUnit: GameUnit<*>) {
        unitDetailView.prepare(gameUnit)
        unitSpawnView.prepare(gameUnit)

        if (gameUnit.productionUnit == null) {
            fabSpawn.visibility = GONE
        } else {
            fabSpawn.visibility = VISIBLE
        }

        subscription = fabSpawn.clicks()
                .subscribe { toggleViews() }

        showDetails()
    }

    private fun toggleViews() {
        if (isDetailsShowing()) {
            showSpawn()
        } else {
            showDetails()
        }
    }

    private fun isDetailsShowing() = unitDetailView.visibility == VISIBLE

    private fun showSpawn() {
        unitDetailView.visibility = INVISIBLE
        unitSpawnView.visibility = VISIBLE
    }

    private fun showDetails() {
        unitDetailView.visibility = VISIBLE
        unitSpawnView.visibility = INVISIBLE
    }

    public fun reset() {
        unitDetailView.reset()
        unitSpawnView.reset()
        subscription?.unsubscribe()
    }
}