/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import rx.Observable
import rx.Subscription
import rx.lang.kotlin.toSingletonObservable

abstract class GameUnit constructor(val repeatObservable: Observable<Long>, initialCount: Long, val sourceUnit: GameUnit?) {

    val countObservable: Observable<Long>
    var count = initialCount

    var subscription: Subscription? = null

    init {
        countObservable = this.toSingletonObservable()
                .map { it.count }
                .repeatWhen { repeatObservable }
    }

    fun start() {
        if (sourceUnit != null) {
            subscription = subscribe(sourceUnit.countObservable)
        }
    }

    fun stop() {
        subscription?.unsubscribe()
        subscription = null;
    }

    abstract fun subscribe(countObservable: Observable<Long>): Subscription
}