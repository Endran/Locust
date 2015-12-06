/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import butterknife.bindView
import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.ArcTranslateAnimation
import nl.endran.locust.R
import nl.endran.locust.game.units.GameUnit
import rx.Subscription

class UnitCompoundedView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val unitDetailView: UnitDetailView by bindView(R.id.unitDetailView)
    val unitSpawnView: UnitSpawnView by bindView(R.id.unitSpawnView)
    val fabSpawn: FloatingActionButton by bindView(R.id.fabSpawn)
    val fabSpawnCancel: FloatingActionButton by bindView(R.id.fabSpawnCancel)

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
                .subscribe { showSpawn() }

        subscription = fabSpawnCancel.clicks()
                .subscribe { showDetails() }

        showDetails()
    }

    private fun showSpawn() {
        if (fabSpawnCancel.visibility == INVISIBLE) {
            animateWithArc(fabSpawnCancel, fabSpawn)
        }

        unitDetailView.visibility = INVISIBLE
        unitSpawnView.visibility = VISIBLE

        fabSpawn.visibility = INVISIBLE
        fabSpawnCancel.visibility = VISIBLE
    }

//    enum class ViewToShow{
//        DETAIL, SPAWN
//    }

    private fun showDetails() {
        if (fabSpawn.visibility == INVISIBLE) {
            animateWithArc(fabSpawn, fabSpawnCancel)
        }

//        val viewToShow : ViewToShow.DETAIL


        unitDetailView.visibility = VISIBLE
        unitSpawnView.visibility = INVISIBLE

        fabSpawn.visibility = VISIBLE
        fabSpawnCancel.visibility = INVISIBLE
    }

    private fun animateWithArc(viewToAnimate: FloatingActionButton, source: FloatingActionButton) {
        val arc = ArcTranslateAnimation(source.x - viewToAnimate.x, 0f,
                source.y - viewToAnimate.y, 0f)
        arc.duration = 300
        arc.interpolator = AccelerateDecelerateInterpolator()

        viewToAnimate.startAnimation(arc)
    }

    public fun reset() {
        unitDetailView.reset()
        unitSpawnView.reset()
        subscription?.unsubscribe()
    }
}