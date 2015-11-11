/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GameLoop {

    public interface Listener {
        void onTrigger(final int millisSincePreviousTrigger);
    }

    public static final int DELAY_MILLIS = 10;
    public static final int TRIGGER_INTERVAL_MILLIS = 100;

    @NonNull
    private final Handler handler;

    private final List<Listener> listeners = new ArrayList<>();
    private boolean isRunning;
    private long previousTimeMillis;

    @Inject
    public GameLoop(@NonNull final Handler handler) {
        this.handler = handler;
    }

    public void register(@NonNull final Listener listener) {
        listeners.add(listener);
        startLazy();
    }

    public void unregister(@NonNull final Listener listener) {
        listeners.remove(listener);
        stopLazy();
    }

    private void stopLazy() {
        if (listeners.isEmpty()) {
            isRunning = false;
        }
    }

    private void startLazy() {
        if (!isRunning) {
            isRunning = true;

            previousTimeMillis = System.currentTimeMillis();
            rePost();
        }
    }

    private void rePost() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRunning) {
                    return;
                }

                long currentTimeMillis = System.currentTimeMillis();
                long millisSincePreviousTrigger = currentTimeMillis - previousTimeMillis;
                if (millisSincePreviousTrigger > TRIGGER_INTERVAL_MILLIS) {
                    for (Listener listener : listeners) {
                        previousTimeMillis = currentTimeMillis;
                        listener.onTrigger((int) millisSincePreviousTrigger);
                    }
                }

                rePost();
            }
        }, DELAY_MILLIS);
    }
}
