/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import nl.endran.locust.R;
import nl.endran.locust.fragments.ResourcesFragment;
import nl.endran.locust.fragments.TerritoryFragment;
import nl.endran.locust.fragments.UnitsFragment;
import nl.endran.locust.injections.AppGraph;
import nl.endran.locust.maps.StringResourceMap;

public class GameActivity extends BaseActivity {

    @Inject
    StringResourceMap stringResourceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppGraph)getApplication()).getAppComponent().inject(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = ButterKnife.findById(this, R.id.viewPager);
        viewPager.setAdapter(new GameFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = ButterKnife.findById(this, R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected String getPageName() {
        return "GameActivity";
    }

    private class GameFragmentPagerAdapter extends FragmentPagerAdapter {

        @NonNull
        private final Fragment[] fragments;

        public GameFragmentPagerAdapter(@NonNull final FragmentManager fm) {
            super(fm);
            fragments = new Fragment[3];
            fragments[0] = UnitsFragment.createInstance();
            fragments[1] = ResourcesFragment.createInstance();
            fragments[2] = TerritoryFragment.createInstance();
        }

        @Override
        public Fragment getItem(final int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return stringResourceMap.getString(stringResourceMap.getStringRes(getItem(position)));
        }
    }
}
