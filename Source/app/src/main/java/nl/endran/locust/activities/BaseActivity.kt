/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import nl.endran.locust.R
import nl.endran.locust.injections.getAppComponent

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val layoutId: Int
    protected abstract val pageName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        val appComponent = getAppComponent()

        appComponent.analytics.trackPage(pageName)

        val adView = findViewById(R.id.adView) as AdView
        appComponent.adManager.checkForAds(getString(R.string.ad_url)) { shouldShowAds ->
            if (shouldShowAds) {
                val adRequest = AdRequest.Builder().build()
                adView.loadAd(adRequest)
                adView.visibility = View.VISIBLE
            } else {
                adView.visibility = View.GONE
            }
        }
    }
}
