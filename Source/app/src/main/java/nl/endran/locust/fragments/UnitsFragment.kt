/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import nl.endran.locust.GameUnitUI
import nl.endran.locust.R
import nl.endran.locust.injections.getAppComponent

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    lateinit var foodUnitUI: GameUnitUI;
    lateinit var nymphUnitUI: GameUnitUI;

    var presenter: UnitsFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        rootView.findViewById(R.id.foodView).findViewById(R.id.viewButtons).visibility = View.GONE
        foodUnitUI = inflateGameUnitUI(rootView, R.id.foodView)
        nymphUnitUI = inflateGameUnitUI(rootView, R.id.nymphView)

        return rootView
    }

    private fun inflateGameUnitUI(rootView: View, viewId: Int): GameUnitUI {
        val view = rootView.findViewById(viewId)
        var gameUnitUI = GameUnitUI(
                view,
                view.findViewById(R.id.textViewName) as TextView,
                view.findViewById(R.id.textViewCurrentProduce) as TextView,
                view.findViewById(R.id.textViewSpawnCost) as TextView,
                view.findViewById(R.id.buttonSpawnOne) as Button,
                view.findViewById(R.id.buttonSpawn50Percent) as Button,
                view.findViewById(R.id.buttonSpawn100Percent) as Button
        )
        return gameUnitUI
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        presenter = context.getAppComponent().createUnitsFragmentPresenter();
        presenter!!.start(foodUnitUI, nymphUnitUI)
    }

    override fun onPause() {
        super.onPause()

        presenter!!.stop()
        presenter = null
    }
}
