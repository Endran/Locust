/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game;

public enum Unit {
    FOOD(null, 0),
    NYMPH(FOOD, 1);

    private final Unit unitToIncrement;
    private final int incrementPerSecond;

    Unit(final Unit unitToIncrement, final int incrementPerSecond) {
        this.unitToIncrement = unitToIncrement;
        this.incrementPerSecond = incrementPerSecond;
    }

    public Unit getTargetUnit() {
        return unitToIncrement;
    }

    public int getIncrementPerSecond() {
        return incrementPerSecond;
    }
}
