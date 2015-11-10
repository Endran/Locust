/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.wrappers;

import android.content.Context;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class FabricFactory {

    @Inject
    public FabricFactory() {
    }

    public Fabric create(Context context, Kit... kits) {
       return Fabric.with(context, kits);
    }
}
