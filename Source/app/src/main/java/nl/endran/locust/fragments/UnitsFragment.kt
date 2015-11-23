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
import nl.endran.locust.game.Units
import nl.endran.locust.injections.findTypedViewById
import nl.endran.locust.injections.getAppComponent

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    var textViewFoodCount: TextView? = null
    var textViewNymphCount: TextView? = null

    var presenter: UnitsFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        textViewFoodCount = rootView.findViewById(R.id.textViewFoodCount) as TextView
        textViewNymphCount = rootView.findViewById(R.id.textViewNymphCount) as TextView

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        presenter = context.getAppComponent().createUnitsFragmentPresenter()
        presenter!!.start (object : UnitsFragmentPresenter.ViewModel {
            override fun updateUnitCount(unitsCountMap: Map<Units, Double>) {
                textViewFoodCount?.text = "COUNT = ${unitsCountMap[Units.FOOD]?.toInt()}"
                textViewNymphCount?.text = "COUNT = ${unitsCountMap[Units.NYMPH]?.toInt()}"
            }
        })
    }

    override fun onPause() {
        super.onPause()

        presenter!!.stop()
        presenter = null
    }
}
