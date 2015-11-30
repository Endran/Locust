/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game.units

import rx.Observable
import rx.Subscription
import rx.lang.kotlin.toSingletonObservable
import java.util.concurrent.TimeUnit

abstract class GameUnit<T> constructor(
        initialCount: Long,
        private val repeatObservable: Observable<Long>,
        val productionUnit: GameUnit<Any>?,
        val spawnCostList: List<Pair<GameUnit<Any>, Int>>) {

    val updateObservable: Observable<GameUnit<T>>
    var count = initialCount

    val produceModifier = 1
    val upkeepModifier = 1
    val spawnModifier = 1

    var subscriptionList: MutableList<Subscription> = arrayListOf()

    init {
        updateObservable = this.toSingletonObservable().repeatWhen { repeatObservable }
                .debounce (10, TimeUnit.MILLISECONDS)
    }

    fun start() {
        subscriptionList.add(
                repeatObservable.subscribe {
                    upkeep()
                    if (productionUnit != null) {
                        productionUnit.count += count * produceModifier * 1
                    }
                }
        )
    }

    fun spawn(spawnCount: Int) {
        if (spawnCount <= getMaxSpawnCount()) {
            count += spawnCount
            spawnCostList.forEach { it.first.count -= it.second * spawnCount }
        }
    }

    fun stop() {
        subscriptionList.forEach { it.unsubscribe() }
        subscriptionList.clear()
    }

    fun upkeep() {

    }

    fun getMaxSpawnCount(): Int {
        if (spawnCostList.isEmpty()) {
            return 0
        }

        var spawnCount = Int.MAX_VALUE
        spawnCostList.forEach {
            spawnCount = Math.min(spawnCount, (it.first.count / it.second).toInt())
        }

        return spawnCount
    }
}
