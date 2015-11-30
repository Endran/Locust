/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import nl.endran.locust.game.units.GameUnit
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class Spawnery
@Inject
constructor() {

    var gameUnitSubscriptionMap: HashMap<GameUnit<*>, List<Subscription>> = hashMapOf()

    public fun setSpawnObservable(gameUnit: GameUnit<*>,
                                  spawnOneObservable: Observable<Unit>,
                                  spawn50PercentObservable: Observable<Unit>,
                                  spawn100PercentObservable: Observable<Unit>) {

        unSubscribeAll(gameUnit)

        var subscriptionList: MutableList<Subscription> = arrayListOf()

        subscriptionList.add(
                spawnOneObservable
                        .observeOn(Schedulers.computation())
                        .filter { gameUnit.getMaxSpawnCount() > 0 }
                        .subscribe {
                            val spawnCount = 1
                            gameUnit.spawn(spawnCount)
                        }
        )

        subscriptionList.add(
                spawn50PercentObservable
                        .observeOn(Schedulers.computation())
                        .filter { gameUnit.getMaxSpawnCount() > 1 }
                        .subscribe { gameUnit.spawn(gameUnit.getMaxSpawnCount() / 2) }
        )

        subscriptionList.add(
                spawn100PercentObservable
                        .observeOn(Schedulers.computation())
                        .filter { gameUnit.getMaxSpawnCount() > 2 }
                        .subscribe { gameUnit.spawn(gameUnit.getMaxSpawnCount()) }
        )

        gameUnitSubscriptionMap.put(gameUnit, subscriptionList)
    }

    public fun unSubscribeAll() {
        gameUnitSubscriptionMap.forEach { unSubscribeAll(it.key) }
    }

    public fun unSubscribeAll(gameUnit: GameUnit<*>) {
        gameUnitSubscriptionMap.getRaw(gameUnit)?.forEach { it.unsubscribe() }
    }
}
