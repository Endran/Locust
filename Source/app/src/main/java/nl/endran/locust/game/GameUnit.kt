/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import rx.Observable
import rx.Subscription
import rx.lang.kotlin.toSingletonObservable
import rx.schedulers.Schedulers

abstract class GameUnit constructor(
        initialCount: Long,
        val sourceUnit: GameUnit?,
        val repeatObservable: Observable<Long>,
        val spawnOneObservable: Observable<Unit>) {

    val countObservable: Observable<Long>
    var count = initialCount

    var subscriptionList: MutableList<Subscription> = arrayListOf()

    init {
        countObservable = this.toSingletonObservable()
                .map { it.count }
                .repeatWhen { repeatObservable }
    }

    fun start() {
        if (sourceUnit != null) {
            subscriptionList.add(
                    subscribe(sourceUnit.countObservable)
            )
        }

        subscriptionList.add(
                spawnOneObservable
                        .observeOn(Schedulers.computation())
                        .subscribe { count += 1 }
        )
    }

    fun stop() {
        subscriptionList.forEach { it.unsubscribe() }
        subscriptionList.clear()
    }

    abstract fun subscribe(countObservable: Observable<Long>): Subscription
}