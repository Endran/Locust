/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_unit_spawn.view.*
import nl.endran.locust.R
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.injections.getAppComponent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class UnitSpawnView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val subscriptionList: MutableList<Subscription> = arrayListOf()

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.view_unit_spawn, this, true)
    }

    public fun prepare(gameUnit: GameUnit<*>) {
        if (gameUnit.productionUnit == null) {
            return
        }

        val appComponent = context.getAppComponent()
        val stringResourceMap = appComponent.stringResourceMap

        val productionUnit = stringResourceMap.getString(gameUnit.javaClass)

        subscriptionList.add(
                gameUnit.updateObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val maxSpawnCount = it.getMaxSpawnCount()
                            textViewMaxSpawn.text = context.getString(R.string.spawn_max, maxSpawnCount, productionUnit)
                        }
        )

        val costText = gameUnit.spawnCostList
                .map { "${it.cost} ${stringResourceMap.getString(it.gameUnit.javaClass)}" }
                .reduce { s1, s2 -> "$s1, $s2" }
        textViewCost.text = context.getString(R.string.spawn_cost, costText)

        val color = context.resources.getColor(R.color.red);
        spawnRoot.setBackgroundColor(color)
    }

    public fun reset() {
        subscriptionList.forEach { it.unsubscribe() }
        subscriptionList.clear()
    }

    fun show() {
        visibility = VISIBLE
        val fadeIn = AlphaAnimation(0f, 1f);
        fadeIn.interpolator = DecelerateInterpolator();
        fadeIn.duration = 150;
        fadeIn.fillAfter = true
        startAnimation(fadeIn);
    }

    fun hide() {
        val fadeOut = AlphaAnimation(1f, 0f);
        fadeOut.interpolator = DecelerateInterpolator();
        fadeOut.duration = 150;
        fadeOut.fillAfter = true
        startAnimation(fadeOut);
    }
}
