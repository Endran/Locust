/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import nl.endran.locust.R
import nl.endran.locust.injections.getAppComponent
import nl.endran.locust.views.UnitSpawnView

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    val unitSpawnView : UnitSpawnView by bindView(R.id.unitSpawnView)

    lateinit var foodUnitUI: GameUnitUI
    lateinit var nymphUnitUI: GameUnitUI
    lateinit var hopperUnitUI: GameUnitUI

    var presenter: UnitsFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        foodUnitUI = inflateGameUnitUI(rootView, R.id.foodView)
        nymphUnitUI = inflateGameUnitUI(rootView, R.id.nymphView)
        hopperUnitUI = inflateGameUnitUI(rootView, R.id.hopperView)

        return rootView
    }

    private fun inflateGameUnitUI(rootView: View, viewId: Int): GameUnitUI {
        val view = rootView.findViewById(viewId)
        var gameUnitUI = GameUnitUI(
                view,
                view.findViewById(R.id.textViewName) as TextView,
                view.findViewById(R.id.textViewCount) as TextView,
                view.findViewById(R.id.textViewMultiplier) as TextView,
                view.findViewById(R.id.textViewProduce) as TextView,
                view.findViewById(R.id.fabSpawn) as FloatingActionButton
        )
        return gameUnitUI
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        presenter = context.getAppComponent().createUnitsFragmentPresenter();
        presenter!!.start(unitSpawnView, foodUnitUI, nymphUnitUI, hopperUnitUI)
    }

    override fun onPause() {
        super.onPause()

        presenter!!.stop()
        presenter = null
    }
}
