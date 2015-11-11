/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.wrappers;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Inject;

public class AdManager {

    public interface Listener {
        void onShouldShowAds(boolean shouldShowAds);
    }

    @NonNull
    private final Handler handler;

    @Inject
    public AdManager(@NonNull final Handler handler) {
        this.handler = handler;
    }

    public void checkForAds(@NonNull final String adUrl, @NonNull final Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean shouldShowAds = false;

                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    new URL(adUrl).openStream()));
                    String line = in.readLine();
                    in.close();

                    AdSettings[] adSettingsArray = new Gson().fromJson(line, AdSettings[].class);
                    AdSettings adSettings = adSettingsArray[0];

                    shouldShowAds = adSettings.shouldShowAds() ;
                } catch (Exception e) {
                    Log.e("AdManager", "An error occurred while checking for ads", e);
                }

                informListener(shouldShowAds, listener);
            }
        }).start();
    }

    private void informListener(final boolean shouldShowAds, @NonNull final Listener listener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onShouldShowAds(shouldShowAds);
            }
        });
    }
}
