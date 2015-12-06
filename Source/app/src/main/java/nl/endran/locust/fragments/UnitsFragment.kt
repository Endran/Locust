/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.endran.locust.R
import nl.endran.locust.injections.getAppComponent
import nl.endran.locust.views.UnitCompoundedView

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    lateinit var unitCompoundedViewFood: UnitCompoundedView
    lateinit var unitCompoundedViewNymph: UnitCompoundedView
    lateinit var unitCompoundedViewHopper: UnitCompoundedView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        unitCompoundedViewFood = rootView.findViewById(R.id.unitCompoundedViewFood) as UnitCompoundedView
        unitCompoundedViewNymph = rootView.findViewById(R.id.unitCompoundedViewNymph) as UnitCompoundedView
        unitCompoundedViewHopper = rootView.findViewById(R.id.unitCompoundedViewHopper) as UnitCompoundedView

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        val gameCentral = context.getAppComponent().gameCentral

        unitCompoundedViewFood.prepare(gameCentral.food)
        unitCompoundedViewNymph.prepare(gameCentral.nymph)
        unitCompoundedViewHopper.prepare(gameCentral.hopper)
    }

    override fun onPause() {
        unitCompoundedViewFood.reset()
        unitCompoundedViewNymph.reset()
        unitCompoundedViewHopper.reset()
    }
}


/*
Units gather units of land, or other units
Each unit of land produce food
Units cost food to produce

Defense/Tech units or items do some cool stuff TBD
 */