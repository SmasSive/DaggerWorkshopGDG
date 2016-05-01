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
package com.smassive.daggerworkshopgdg.data.repository;

import com.smassive.daggerworkshopgdg.data.exception.ComicsNotFoundException;
import com.smassive.daggerworkshopgdg.data.exception.RepositoryErrorBundle;
import com.smassive.daggerworkshopgdg.data.repository.datasource.ComicDataStore;
import com.smassive.daggerworkshopgdg.data.repository.datasource.ComicDataStoreFactory;
import com.smassive.daggerworkshopgdg.data.repository.datasource.RealmComicDataStore;
import com.smassive.daggerworkshopgdg.domain.repository.ComicsRepository;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ComicsRepositoryImpl implements ComicsRepository {

    private ComicDataStoreFactory comicDataStoreFactory;

    @Inject
    public ComicsRepositoryImpl(ComicDataStoreFactory comicDataStoreFactory) {
        this.comicDataStoreFactory = comicDataStoreFactory;
    }

    /**
     * Get a collection of {@link ComicBo}.
     *
     * @param characterId       int character identifier which we want to retrieve comics from.
     * @param refresh           boolean true if the request should be done to the cloud.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    @Override
    public void getComics(final int characterId, final boolean refresh, final ComicListCallback comicListCallback) {
        final ComicDataStore comicDataStore = comicDataStoreFactory.create(refresh);
        comicDataStore.getComics(characterId, new ComicDataStore.ComicListCallback() {
            @Override
            public void onComicListLoaded(Collection<ComicBo> comicBoCollection) {
                ComicDataStore realmComicDataStore = comicDataStoreFactory.create(false);
                saveComics(comicBoCollection, (RealmComicDataStore)realmComicDataStore, characterId, comicListCallback);

                comicListCallback.onComicListLoaded(comicBoCollection);
            }

            @Override
            public void onError(Exception exception) {
                if (exception instanceof ComicsNotFoundException && comicDataStore instanceof RealmComicDataStore) {
                    final RealmComicDataStore realmComicDataStore = (RealmComicDataStore) comicDataStore;

                    // try in the cloud
                    ComicDataStore cloudComicDataStore = comicDataStoreFactory.create(true);
                    cloudComicDataStore.getComics(characterId, new ComicDataStore.ComicListCallback() {
                        @Override
                        public void onComicListLoaded(Collection<ComicBo> comicBoCollection) {
                            saveComics(comicBoCollection, realmComicDataStore, characterId, comicListCallback);
                            comicListCallback.onComicListLoaded(comicBoCollection);
                        }

                        @Override
                        public void onError(Exception exception) {
                            comicListCallback.onError(new RepositoryErrorBundle(exception));
                        }
                    });
                } else {
                    comicListCallback.onError(new RepositoryErrorBundle(exception));
                }
            }
        });
    }

    private void saveComics(Collection<ComicBo> comicBoCollection, RealmComicDataStore realmComicDataStore, int characterId,
            final ComicListCallback comicListCallback) {
        realmComicDataStore.saveComics(characterId, comicBoCollection, new ComicDataStore.ComicListCallback() {
            @Override
            public void onComicListLoaded(Collection<ComicBo> comicBoCollection) {
                // Do nothing
            }

            @Override
            public void onError(Exception exception) {
                comicListCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }

    /**
     * Get a {@link ComicBo} by id.
     *
     * @param comicId             int comic identifier which we want to retrieve information.
     * @param comicDetailCallback a {@link ComicDetailCallback} to return info to presentation.
     */
    @Override
    public void getComic(int comicId, final ComicDetailCallback comicDetailCallback) {
        // We have loaded all the comics from cloud before
        ComicDataStore comicDataStore = comicDataStoreFactory.create(false);
        comicDataStore.getComic(comicId, new ComicDataStore.ComicDetailCallback() {
            @Override
            public void onComicLoaded(ComicBo comic) {
                comicDetailCallback.onComicLoaded(comic);
            }

            @Override
            public void onError(Exception exception) {
                comicDetailCallback.onError(new RepositoryErrorBundle(exception));
            }
        });
    }
}
