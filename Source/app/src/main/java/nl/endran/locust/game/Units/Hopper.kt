/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game.units

import rx.Observable
import javax.inject.Inject

class Hopper @Inject constructor(
        initialCount: Long,
        repeatObservable: Observable<Long>,
        nymph: Nymph,
        food: Food)
: GameUnit<Nymph> (initialCount, repeatObservable, nymph,
        listOf(GameUnit.GameUnitCost(nymph, 10), GameUnit.GameUnitCost(food, 100))) {
}
