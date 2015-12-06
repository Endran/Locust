/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import butterknife.bindView
import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.ArcTranslateAnimation
import nl.endran.locust.R
import nl.endran.locust.game.units.GameUnit
import rx.Subscription
import rx.schedulers.Schedulers

class UnitCompoundedView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val unitDetailView: UnitDetailView by bindView(R.id.unitDetailView)
    val unitSpawnView: UnitSpawnView by bindView(R.id.unitSpawnView)
    val fabSpawn: FloatingActionButton by bindView(R.id.fabSpawn)
    val fabSpawnCancel: FloatingActionButton by bindView(R.id.fabSpawnCancel)

    val subscriptionList: MutableList<Subscription> = arrayListOf()

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.view_unit_compounded, this, true)
    }

    public fun prepare(gameUnit: GameUnit<*>) {
        unitDetailView.prepare(gameUnit)
        unitSpawnView.prepare(gameUnit)

        unitSpawnView.visibility = INVISIBLE
        showDetails()

        if (gameUnit.productionUnit == null) {
            fabSpawn.visibility = GONE
            return
        }

        subscriptionList.add(fabSpawn.clicks()
                .subscribe { showSpawn() }
        )

        subscriptionList.add(fabSpawnCancel.clicks()
                .subscribe { showDetails() }
        )

        subscriptionList.add(
                unitSpawnView.fabSpawnOne.clicks()
                        .observeOn(Schedulers.computation())
                        .filter { isSpawnViewShowing() }
                        .filter { gameUnit.getMaxSpawnCount() > 0 }
                        .subscribe {
                            gameUnit.spawn(1)
                            handler.post { showDetails() }
                        }
        )

        subscriptionList.add(
                unitSpawnView.fabSpawn50Percent.clicks()
                        .observeOn(Schedulers.computation())
                        .filter { isSpawnViewShowing() }
                        .filter { gameUnit.getMaxSpawnCount() > 0 }
                        .subscribe {
                            gameUnit.spawn(gameUnit.getMaxSpawnCount() / 2)
                            handler.post { showDetails() }
                        }
        )

        subscriptionList.add(
                unitSpawnView.fabSpawn100Percent.clicks()
                        .observeOn(Schedulers.computation())
                        .filter { isSpawnViewShowing() }
                        .filter { gameUnit.getMaxSpawnCount() > 0 }
                        .subscribe {
                            gameUnit.spawn(gameUnit.getMaxSpawnCount())
                            handler.post { showDetails() }
                        }
        )
    }

    private fun isSpawnViewShowing() = fabSpawnCancel.visibility == VISIBLE

    private fun showSpawn() {
        animateWithArc(fabSpawnCancel, fabSpawn) {
            unitSpawnView.show()
        }
    }

    private fun showDetails() {
        unitSpawnView.hide()
        animateWithArc(fabSpawn, fabSpawnCancel) { }
    }

    private fun animateWithArc(viewToAnimate: FloatingActionButton, source: FloatingActionButton, continueShowing: () -> Unit) {
        if (viewToAnimate.visibility == INVISIBLE) {
            val arc = ArcTranslateAnimation(source.x - viewToAnimate.x, 0f,
                    source.y - viewToAnimate.y, 0f)
            arc.duration = 300
            arc.interpolator = DecelerateInterpolator()
            viewToAnimate.startAnimation(arc)
            arc.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    arc.setAnimationListener(null)
                    continueShowing()
                }
            });
        } else {
            continueShowing()
        }
        viewToAnimate.visibility = VISIBLE
        source.visibility = INVISIBLE
    }

    public fun reset() {
        unitDetailView.reset()
        unitSpawnView.reset()
        subscriptionList.forEach { it.unsubscribe() }
    }
}