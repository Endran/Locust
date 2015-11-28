/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game

import rx.Observable
import rx.Subscription
import javax.inject.Inject

class Nymph @Inject constructor(repeatObservable: Observable<Long>, count: Long)
: GameUnit (repeatObservable, count, null) {

    override fun subscribe(countObservable: Observable<Long>): Subscription {
        throw UnsupportedOperationException()
    }

}