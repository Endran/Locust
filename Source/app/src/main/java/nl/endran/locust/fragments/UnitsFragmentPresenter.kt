/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import nl.endran.locust.game.GameLoop
import nl.endran.locust.game.UnitHousing
import nl.endran.locust.game.Units
import javax.inject.Inject

class UnitsFragmentPresenter
@Inject
constructor(private val gameLoop: GameLoop, private val unitHousing: UnitHousing) {

    public interface ViewModel {
        fun updateUnitCount(unitsCountMap: Map<Units, Double>)
    }

    private var viewModel: ViewModel? = null

    fun start(viewModel: ViewModel) {
        this.viewModel = viewModel
        gameLoop.register(listener)
    }

    fun stop() {
        gameLoop.unregister(listener)
        viewModel = null
    }

    private val listener = (object : GameLoop.Listener {
        override fun onTrigger(millisSincePreviousTrigger: Int) {
            unitHousing.increment((millisSincePreviousTrigger.toFloat()) / 1000)
            viewModel?.updateUnitCount(unitHousing.unitCountMap)
        }
    })
}
