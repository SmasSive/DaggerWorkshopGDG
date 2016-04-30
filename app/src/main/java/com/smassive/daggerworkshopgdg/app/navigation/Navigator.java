/**
 * Copyright (C) 2016 Sergi Castillo Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smassive.daggerworkshopgdg.app.navigation;

import com.smassive.daggerworkshopgdg.app.view.activity.ComicDetailActivity;
import com.smassive.daggerworkshopgdg.app.view.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;

/**
 * Class used to navigate through the application.
 */
public class Navigator {

    public Navigator() {
    }

    /**
     * Go to list of comics screen.
     *
     * @param activity Activity from.
     */
    public void goToList(Activity activity) {
        activity.navigateUpTo(new Intent(activity, MainActivity.class));
    }

    /**
     * Go to comic detail screen.
     *
     * @param activity Activity from.
     * @param comicId  int identifier of the comic which will be loaded.
     */
    public void goToDetail(Activity activity, int comicId) {
        if (activity != null) {
            Intent intentToLaunch = ComicDetailActivity.getCallingIntent(activity, comicId);
            activity.startActivity(intentToLaunch);
        }
    }
}
