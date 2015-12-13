/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_unit_detail.view.*
import nl.endran.locust.R
import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.injections.getAppComponent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class UnitDetailView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    var subscription: Subscription? = null

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.view_unit_detail, this, true)
    }

    public fun prepare(gameUnit: GameUnit<*>) {
        val stringResourceMap = context.getAppComponent().stringResourceMap
        subscription = gameUnit.updateObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val maxSpawnCount = it.getMaxSpawnCount()

                    textViewName.text = stringResourceMap.getString(it.javaClass)

                    if (it is Food) {
                        textViewCount.text = context.getString(
                                R.string.current_food, it.count)
                        textViewMultiplier.visibility = View.GONE
                        textViewProduce.visibility = View.GONE
                    } else {
                        textViewCount.text = context.getString(
                                R.string.unit_count, it.count)
                        textViewMultiplier.text = context.getString(
                                R.string.produce_multiplier, it.produceModifier)
                        textViewProduce.text = context.getString(
                                R.string.produce_total, it.count * it.produceModifier,
                                stringResourceMap.getString(it.productionUnit!!.javaClass))
                    }
                }
    }

    public fun reset() {
        subscription?.unsubscribe()
        subscription = null
    }
}