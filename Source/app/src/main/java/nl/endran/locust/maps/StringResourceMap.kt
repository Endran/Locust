/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.maps

import android.content.res.Resources
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import nl.endran.locust.R
import nl.endran.locust.fragments.ResourcesFragment
import nl.endran.locust.fragments.TerritoryFragment
import nl.endran.locust.fragments.UnitsFragment
import nl.endran.locust.game.units.Food
import nl.endran.locust.game.units.Nymph
import javax.inject.Inject

class StringResourceMap @Inject constructor(val resources: Resources) {
    val classToStringResMap = mapOf(
            UnitsFragment::class.java to R.string.units,
            ResourcesFragment::class.java to R.string.resources,
            TerritoryFragment::class.java to R.string.territory,

            Food::class.java to R.string.food,
            Nymph::class.java to R.string.nymph)

    fun getString(clazz: Class<*>): String {
        val stringId = classToStringResMap.getRaw(clazz)

        if (stringId != null) {
            return getString(stringId)
        } else {
            return clazz.simpleName
        }
    }

    fun getString(@StringRes stringRes: Int): String {
        return resources.getString(stringRes)
    }

    @StringRes
    fun getStringRes(fragment: Fragment): Int {
        return classToStringResMap[fragment.javaClass] ?: R.string.placeholder
    }
}
