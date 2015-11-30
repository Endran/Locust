/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.R
import nl.endran.locust.game.Spawnery
import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.maps.StringResourceMap
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UnitsFragmentPresenter
@Inject
constructor(val stringResourceMap: StringResourceMap, val gameCentral: GameCentral, val spawnery: Spawnery) {

    val subsciptionList: MutableList<Subscription> = arrayListOf()

    fun start(foodGameUnitUI: GameUnitUI,
              nymphGameUnitUI: GameUnitUI) {

        prepareGameUnit(gameCentral.food, foodGameUnitUI)
        prepareGameUnit(gameCentral.nymph, nymphGameUnitUI)
    }

    private fun prepareGameUnit(gameUnit: GameUnit<*>, gameUnitUI: GameUnitUI) {
        var subscription = gameUnit.updateObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val context = gameUnitUI.currentProduceTextView.context
                    val maxSpawnCount = it.getMaxSpawnCount()

                    gameUnitUI.nameTextView.text = stringResourceMap.getString(it.javaClass)

                    if (it is Food) {
                        gameUnitUI.currentProduceTextView.text = context.getString(
                                R.string.current_food, it.count)
                    } else {
                        gameUnitUI.currentProduceTextView.text = context.getString(
                                R.string.current_produce, it.count, it.produceModifier,
                                stringResourceMap.getString(it.productionUnit!!.javaClass))

                    }

                    gameUnitUI.spawnCostTextView.text = "temp"

                    gameUnitUI.spawnOneButton.isEnabled = maxSpawnCount > 0
                    gameUnitUI.spawnOneButton.text = context.getString(R.string.spawn, 1)

                    gameUnitUI.spawn50PercentButton.isEnabled = maxSpawnCount > 1
                    gameUnitUI.spawn50PercentButton.text = context.getString(R.string.spawn, Math.max(maxSpawnCount / 2, 2))

                    gameUnitUI.spawn100PercentButton.isEnabled = maxSpawnCount > 2
                    gameUnitUI.spawn100PercentButton.text = context.getString(R.string.spawn, Math.max(3, maxSpawnCount))
                }
        subsciptionList.add(subscription)

        spawnery.setSpawnObservable(gameUnit,
                gameUnitUI.spawnOneButton.clicks(),
                gameUnitUI.spawn50PercentButton.clicks(),
                gameUnitUI.spawn100PercentButton.clicks())
    }

    fun stop() {
        spawnery.unSubscribeAll()
        subsciptionList.forEach { it.unsubscribe() }
        subsciptionList.clear()
    }
}
