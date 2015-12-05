/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.view.View
import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.R
import nl.endran.locust.game.Spawnery
import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.maps.StringResourceMap
import nl.endran.locust.views.UnitDetailView
import nl.endran.locust.views.UnitSpawnView
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UnitsFragmentPresenter
@Inject
constructor(val stringResourceMap: StringResourceMap, val gameCentral: GameCentral, val spawnery: Spawnery) {

    val subscriptionList: MutableList<Subscription> = arrayListOf()

    fun start(unitSpawnView: UnitSpawnView,
              foodGameUnitUI: GameUnitUI,
              nymphGameUnitUI: GameUnitUI,
              hopperGameUnitUI: GameUnitUI,
              unitDetailView: UnitDetailView) {

        unitSpawnView.setGameUnit(gameCentral.nymph)
        unitDetailView.setGameUnit(gameCentral.nymph)

        prepareGameUnit(gameCentral.food, foodGameUnitUI)
        prepareGameUnit(gameCentral.nymph, nymphGameUnitUI)
        prepareGameUnit(gameCentral.hopper, hopperGameUnitUI)
    }

    private fun prepareGameUnit(gameUnit: GameUnit<*>, gameUnitUI: GameUnitUI) {
        var subscription = gameUnit.updateObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val context = gameUnitUI.view.context
                    val maxSpawnCount = it.getMaxSpawnCount()

                    gameUnitUI.textViewName.text = stringResourceMap.getString(it.javaClass)

                    if (it is Food) {
                        gameUnitUI.textViewCount.text = context.getString(
                                R.string.current_food, it.count)
                        gameUnitUI.textViewMultiplier.visibility = View.GONE
                        gameUnitUI.textViewMultiplier.visibility = View.GONE
                    } else {
                        gameUnitUI.textViewCount.text = context.getString(
                                R.string.unit_count, it.count)
                        gameUnitUI.textViewMultiplier.text = context.getString(
                                R.string.produce_multiplier, it.produceModifier)
                        gameUnitUI.textViewProduce.text = context.getString(
                                R.string.produce_total, it.count * it.produceModifier, stringResourceMap.getString(it.productionUnit!!.javaClass))
                    }

                    if (maxSpawnCount > 0) {
                        gameUnitUI.fabSpawn.show()
                    } else {
                        gameUnitUI.fabSpawn.hide()
                    }
                    //                    gameUnitUI.spawnOneButton.text = context.getString(R.string.spawn, 1)
                    //
                    //                    gameUnitUI.spawn50PercentButton.isEnabled = maxSpawnCount > 1
                    //                    gameUnitUI.spawn50PercentButton.text = context.getString(R.string.spawn, Math.max(maxSpawnCount / 2, 2))
                    //
                    //                    gameUnitUI.spawn100PercentButton.isEnabled = maxSpawnCount > 2
                    //                    gameUnitUI.spawn100PercentButton.text = context.getString(R.string.spawn, Math.max(3, maxSpawnCount))
                }
        subscriptionList.add(subscription)

        spawnery.setSpawnObservable(gameUnit,
                gameUnitUI.fabSpawn.clicks(),
                Observable.never(),
                Observable.never())
//                gameUnitUI.spawn50PercentButton.clicks(),
//                gameUnitUI.spawn100PercentButton.clicks())
    }

    fun stop() {
        spawnery.unSubscribeAll()
        subscriptionList.forEach { it.unsubscribe() }
        subscriptionList.clear()
    }
}
