/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import rx.Observable
import rx.Subscription
import javax.inject.Inject

class Nymph @Inject constructor(
        count: Long,
        repeatObservable: Observable<Long>,
        spawnOneObservable: Observable<Unit>)
: GameUnit (count, null, repeatObservable, spawnOneObservable) {

    override fun subscribe(countObservable: Observable<Long>): Subscription {
        throw UnsupportedOperationException()
    }

}