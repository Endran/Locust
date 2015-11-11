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
    private final Map<Unit, Double> unitCountMap = new HashMap<>();

    @Inject
    public UnitHousing() {
        Unit[] units = Unit.values();
        for (Unit unit : units) {
            unitCountMap.put(unit, 0d);
        }

        unitCountMap.put(Unit.FOOD, 25d);
        unitCountMap.put(Unit.NYMPH, 1d);
    }

    public void increment(final float fraction) {
        Unit[] units = Unit.values();

        for (int i = Unit.FOOD.ordinal() + 1; i < units.length; i++) {
            Unit unit = units[i];
            Unit targetUnit = unit.getTargetUnit();

            int flooredNumberOfUnits = (int) (double) unitCountMap.get(unit);
            double targetNumberOfUnits = unitCountMap.get(targetUnit);
            double generatedTargetNumberOfUnits = unit.getIncrementPerSecond() * fraction * flooredNumberOfUnits;

            unitCountMap.put(targetUnit, targetNumberOfUnits + generatedTargetNumberOfUnits);
        }
    }

    @NonNull
    public Map<Unit, Double> getUnitCountMap() {
        return unitCountMap;
    }
}
