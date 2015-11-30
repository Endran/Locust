/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.GameUnit
import nl.endran.locust.game.units.Hopper
import nl.endran.locust.game.units.Nymph
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameCentral
@Inject
constructor() {

    val repeatObservable = Observable.interval(1000, TimeUnit.MILLISECONDS)

    val gameUnitList: List<GameUnit<*>>

    val food: Food
    val nymph: Nymph
    val hopper: Hopper

    init {
        food = Food(1, repeatObservable)
        nymph = Nymph(1, repeatObservable, food)
        hopper = Hopper(0, repeatObservable, nymph, food)

        gameUnitList = listOf(
                food,
                nymph,
                hopper
        )
    }


    fun start() {
        gameUnitList.forEach { it.start() }
    }

    fun stop() {
        gameUnitList.forEach { it.stop() }
    }
}