/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.game;

public enum Units {
    FOOD(null, 0),
    NYMPH(FOOD, 1);

    private final Units unitsToIncrement;
    private final int incrementPerSecond;

    Units(final Units unitsToIncrement, final int incrementPerSecond) {
        this.unitsToIncrement = unitsToIncrement;
        this.incrementPerSecond = incrementPerSecond;
    }

    public Units getTargetUnit() {
        return unitsToIncrement;
    }

    public int getIncrementPerSecond() {
        return incrementPerSecond;
    }
}
