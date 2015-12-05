/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nl.endran.locust.R
import nl.endran.locust.injections.getAppComponent
import nl.endran.locust.views.UnitCompoundedView

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    lateinit var foodUnitUI: GameUnitUI
    lateinit var unitCompoundedViewNymph: UnitCompoundedView
    lateinit var hopperUnitUI: GameUnitUI

    var presenter: UnitsFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        foodUnitUI = inflateGameUnitUI(rootView, R.id.foodView)
        unitCompoundedViewNymph = rootView.findViewById(R.id.unitCompoundedViewNymph) as UnitCompoundedView
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
                view.findViewById(R.id.textViewProduce) as TextView
        )
        return gameUnitUI
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        presenter = context.getAppComponent().createUnitsFragmentPresenter();
        presenter!!.start(foodUnitUI, unitCompoundedViewNymph, hopperUnitUI)
    }

    override fun onPause() {
        super.onPause()

        presenter!!.stop()
        presenter = null
    }
}
