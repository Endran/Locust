/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import javax.inject.Inject;

import nl.endran.locust.R;
import nl.endran.locust.injections.AppComponent;
import nl.endran.locust.injections.AppGraph;
import nl.endran.locust.wrappers.AdManager;
import nl.endran.locust.wrappers.Analytics;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Analytics analytics;

    @Inject
    AdManager adManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        AppComponent appComponent = ((AppGraph) getApplication()).getAppComponent();
        appComponent.inject(this);

        analytics.trackPage(getPageName());

        final AdView adView = (AdView) findViewById(R.id.adView);

        adManager.checkForAds(getString(R.string.ad_url), new AdManager.Listener() {
            @Override
            public void onShouldShowAds(final boolean shouldShowAds) {
                if (shouldShowAds) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                    adView.setVisibility(View.VISIBLE);
                } else {
                    adView.setVisibility(View.GONE);
                }
            }
        });
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract String getPageName();
}
