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

class ResourcesFragment : Fragment() {

    companion object Factory {
        fun createInstance(): ResourcesFragment = ResourcesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_resources, container, false)
    }
}


// Instar
// Pupa

// These are stages the egg transform into before coming out of the POP, then becomung a Nymph