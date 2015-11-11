/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import javax.inject.Inject;

import nl.endran.locust.game.GameLoop;
import nl.endran.locust.game.Unit;
import nl.endran.locust.game.UnitHousing;

public class UnitsFragmentPresenter {

    public interface ViewModel {
        void updateUnitCount(Map<Unit, Double> unitCountMap);
    }

    @NonNull
    private final GameLoop gameLoop;

    @NonNull
    private final UnitHousing unitHousing;

    @Nullable
    private ViewModel viewModel;

    @Inject
    public UnitsFragmentPresenter(@NonNull final GameLoop gameLoop, @NonNull final UnitHousing unitHousing) {
        this.gameLoop = gameLoop;
        this.unitHousing = unitHousing;
    }

    public void start(@NonNull final ViewModel viewModel) {
        this.viewModel = viewModel;
        gameLoop.register(listener);
    }

    public void stop() {
        gameLoop.unregister(listener);
        viewModel = null;
    }

    @NonNull
    private GameLoop.Listener listener = new GameLoop.Listener() {
        @Override
        public void onTrigger(final int millisSincePreviousTrigger) {
            unitHousing.increment(((float) millisSincePreviousTrigger) / 1000);
            if (viewModel != null) {
                viewModel.updateUnitCount(unitHousing.getUnitCountMap());
            }
        }
    };
}
