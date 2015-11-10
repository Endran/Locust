/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.injections;

import javax.inject.Singleton;

import dagger.Component;
import nl.endran.locust.App;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void injectApp(App app);
}
