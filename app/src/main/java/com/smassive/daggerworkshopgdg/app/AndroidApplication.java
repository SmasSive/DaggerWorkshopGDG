/**
 * Copyright (C) 2016 Sergi Castillo Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smassive.daggerworkshopgdg.app;

import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.smassive.daggerworkshopgdg.app.injector.component.ApplicationComponent;
import com.smassive.daggerworkshopgdg.app.injector.component.DaggerApplicationComponent;
import com.smassive.daggerworkshopgdg.app.injector.module.ApplicationModule;

import android.app.Application;

public class AndroidApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeInjector();

        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
        }
    }

    private void initializeInjector() {
        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
