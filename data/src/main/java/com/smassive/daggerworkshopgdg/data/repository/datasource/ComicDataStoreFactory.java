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
package com.smassive.daggerworkshopgdg.data.repository.datasource;

import com.smassive.daggerworkshopgdg.data.R;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmConfiguration;

@Singleton
public class ComicDataStoreFactory {

    private final Context context;

    @Inject
    public ComicDataStoreFactory(Context context) {
        this.context = context;
    }

    public ComicDataStore create(boolean refresh) {
        ComicDataStore comicDataStore;

        if (refresh) {
            comicDataStore = createCloudDataStore();
        } else {
            comicDataStore = createDbDataStore();
        }

        return comicDataStore;
    }

    private ComicDataStore createDbDataStore() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        return new RealmComicDataStore(realmConfiguration);
    }

    private ComicDataStore createCloudDataStore() {
        return new RetrofitComicDataStore(context.getString(R.string.public_key), context.getString(R.string.private_key));
    }


}
