/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class StringResourceMap {

    @NonNull
    private final Resources resources;

    @NonNull
    private final Map<Class, Integer> classToStringResMap = new HashMap<>();

    public StringResourceMap(@NonNull final Resources resources) {
        this.resources = resources;

        classToStringResMap.put(UnitsFragment.class, R.string.units);
        classToStringResMap.put(ResourcesFragment.class, R.string.resources);
        classToStringResMap.put(TerritoryFragment.class, R.string.territory);
    }

    @NonNull
    public String getString(@StringRes final int stringRes) {
        return resources.getString(stringRes);
    }

    @StringRes
    public int getStringRes(Fragment fragment) {
        return classToStringResMap.get(fragment.getClass());
    }
}
