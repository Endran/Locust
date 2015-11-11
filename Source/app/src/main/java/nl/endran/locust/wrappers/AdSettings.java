/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.wrappers;

public class AdSettings {

    private int shouldShowAds;
    private int initialBannerDelay;
    private int interstitialDelay;

    public boolean shouldShowAds() {
        return shouldShowAds > 0;
    }

    public int getInitialBannerDelay() {
        return initialBannerDelay;
    }

    public int getInterstitialDelay() {
        return interstitialDelay;
    }
}
