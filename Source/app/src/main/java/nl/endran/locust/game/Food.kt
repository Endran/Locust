/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import rx.Observable
import rx.Subscription
import javax.inject.Inject

class Food @Inject constructor(repeatObservable: Observable<Long>, count: Long, nymph: Nymph)
: GameUnit (repeatObservable, count, nymph) {

    override fun subscribe(countObservable: Observable<Long>): Subscription {
        return countObservable.
                subscribe { count += it }
    }
}

