/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import nl.endran.locust.game.units.Food
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

    val food = Food(1, repeatObservable)
    val nymph = Nymph(1, repeatObservable, food)

    fun start() {
        food.start()
        nymph.start()
    }

    fun stop() {
        nymph.stop()
        food.stop()
    }
}