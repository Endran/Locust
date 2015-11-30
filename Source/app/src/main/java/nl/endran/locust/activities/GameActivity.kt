/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import butterknife.ButterKnife
import nl.endran.locust.R
import nl.endran.locust.fragments.ResourcesFragment
import nl.endran.locust.fragments.TerritoryFragment
import nl.endran.locust.fragments.UnitsFragment
import nl.endran.locust.injections.getAppComponent
import nl.endran.locust.maps.StringResourceMap

class GameActivity : BaseActivity() {

    override val layoutId = R.layout.activity_game
    override val pageName = "GameActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAppComponent().gameCentral.start()

        val stringResourceMap = getAppComponent().stringResourceMap

        val toolbar = ButterKnife.findById<Toolbar>(this, R.id.toolbar)
        setSupportActionBar(toolbar)

        val viewPager = ButterKnife.findById<ViewPager>(this, R.id.viewPager)
        viewPager.adapter = GameFragmentPagerAdapter(supportFragmentManager, stringResourceMap)

        val tabLayout = ButterKnife.findById<TabLayout>(this, R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()

        getAppComponent().gameCentral.stop()
    }

    private inner class GameFragmentPagerAdapter(fm: FragmentManager, val stringResourceMap: StringResourceMap) : FragmentPagerAdapter(fm) {

        private val fragments: Array<Fragment> = arrayOf(
                UnitsFragment.createInstance(),
                ResourcesFragment.createInstance(),
                TerritoryFragment.createInstance())

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            val stringRes = stringResourceMap.getStringRes(getItem(position))
            return stringResourceMap.getString(stringRes)
        }
    }
}
