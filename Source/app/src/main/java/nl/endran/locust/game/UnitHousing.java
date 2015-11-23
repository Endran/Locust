/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class UnitHousing {

    @NonNull
    private final Map<Units, Double> unitCountMap = new HashMap<>();

    @Inject
    public UnitHousing() {
        Units[] unitses = Units.values();
        for (Units unit : unitses) {
            unitCountMap.put(unit, 0d);
        }

        unitCountMap.put(Units.FOOD, 25d);
        unitCountMap.put(Units.NYMPH, 1d);
    }

    public void increment(final float fraction) {
        Units[] unitses = Units.values();

        for (int i = Units.FOOD.ordinal() + 1; i < unitses.length; i++) {
            Units unit = unitses[i];
            Units targetUnits = unit.getTargetUnit();

            int flooredNumberOfUnits = (int) (double) unitCountMap.get(unit);
            double targetNumberOfUnits = unitCountMap.get(targetUnits);
            double generatedTargetNumberOfUnits = unit.getIncrementPerSecond() * fraction * flooredNumberOfUnits;

            unitCountMap.put(targetUnits, targetNumberOfUnits + generatedTargetNumberOfUnits);
        }
    }

    @NonNull
    public Map<Units, Double> getUnitCountMap() {
        return unitCountMap;
    }
}
