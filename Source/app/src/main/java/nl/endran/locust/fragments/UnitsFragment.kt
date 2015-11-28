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
import com.jakewharton.rxbinding.view.clicks
import nl.endran.locust.R
import nl.endran.locust.game.Food
import nl.endran.locust.game.Nymph
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class UnitsFragment : Fragment() {

    companion object Factory {
        fun createInstance(): UnitsFragment {
            return UnitsFragment()
        }
    }

    lateinit var textViewFoodCount: TextView
    lateinit var textViewNymphCount: TextView
    lateinit var buttonNymph: View

    var presenter: UnitsFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_units, container, false)

        textViewFoodCount = rootView.findViewById(R.id.textViewFoodCount) as TextView
        textViewNymphCount = rootView.findViewById(R.id.textViewNymphCount) as TextView
        buttonNymph = rootView.findViewById(R.id.buttonNymph)

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        val repeatObservable = Observable.interval(1000, TimeUnit.MILLISECONDS)

        val nymph = Nymph(1, repeatObservable, buttonNymph.clicks())
        nymph.start()
        nymph.countObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    textViewNymphCount.text = "COUNT = $it"
                }

        val food = Food(1, nymph, repeatObservable, Observable.never())
        food.start()
        food.countObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    textViewFoodCount.text = "COUNT = $it"
                }


        //        presenter = context.getAppComponent().createUnitsFragmentPresenter()
        //        presenter!!.start (object : UnitsFragmentPresenter.ViewModel {
        //            override fun updateUnitCount(unitsCountMap: Map<Units, Double>) {
        //                textViewFoodCount?.text = "COUNT = ${unitsCountMap[Units.FOOD]?.toInt()}"
        //                textViewNymphCount?.text = "COUNT = ${unitsCountMap[Units.NYMPH]?.toInt()}"
        //            }
        //        })
    }

    override fun onPause() {
        super.onPause()

        //        presenter!!.stop()
        //        presenter = null
    }
}
