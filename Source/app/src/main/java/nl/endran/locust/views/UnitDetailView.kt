/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import nl.endran.locust.R
import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.injections.getAppComponent
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class UnitDetailView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val imageViewIcon: ImageView by bindView(R.id.imageViewIcon)
    val textViewName: TextView by bindView(R.id.textViewName)
    val textViewCount: TextView by bindView(R.id.textViewCount)
    val textViewMultiplier: TextView by bindView(R.id.textViewMultiplier)
    val textViewProduce: TextView by bindView(R.id.textViewProduce)

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
                        textViewMultiplier.visibility = View.GONE
                    } else {
                        textViewCount.text = context.getString(
                                R.string.unit_count, it.count)
                        textViewMultiplier.text = context.getString(
                                R.string.produce_multiplier, it.produceModifier)
                        textViewProduce.text = context.getString(
                                R.string.produce_total, it.count * it.produceModifier,
                                stringResourceMap.getString(it.productionUnit!!.javaClass))
                    }

                    //                    if (maxSpawnCount > 0) {
                    //                        fabSpawn.show()
                    //                    } else {
                    //                        fabSpawn.hide()
                    //                    }

                    //                    spawnOneButton.text = context.getString(R.string.spawn, 1)
                    //
                    //                    spawn50PercentButton.isEnabled = maxSpawnCount > 1
                    //                    spawn50PercentButton.text = context.getString(R.string.spawn, Math.max(maxSpawnCount / 2, 2))
                    //
                    //                    spawn100PercentButton.isEnabled = maxSpawnCount > 2
                    //                    spawn100PercentButton.text = context.getString(R.string.spawn, Math.max(3, maxSpawnCount))
                }
    }

    public fun reset() {
        subscription?.unsubscribe()
        subscription = null
    }
}