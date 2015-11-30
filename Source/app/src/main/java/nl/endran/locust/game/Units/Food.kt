/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game.units

import rx.Observable
import javax.inject.Inject

class Food @Inject constructor(
        initialCount: Long,
        repeatObservable: Observable<Long>)
: GameUnit<Any> (initialCount, repeatObservable, null, listOf()) {

}

