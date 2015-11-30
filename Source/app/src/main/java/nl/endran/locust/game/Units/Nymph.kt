/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game.units

import rx.Observable
import javax.inject.Inject

class Nymph @Inject constructor(
        initialCount: Long,
        repeatObservable: Observable<Long>,
        productionUnit: Food)
: GameUnit<Food> (initialCount, repeatObservable, productionUnit,
        listOf(Pair(productionUnit, 10))) {
}
