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

import com.smassive.daggerworkshopgdg.data.bean.vo.comic.ComicVo;
import com.smassive.daggerworkshopgdg.data.bean.vo.comic.mapper.ComicVoMapper;
import com.smassive.daggerworkshopgdg.data.exception.ComicNotFoundException;
import com.smassive.daggerworkshopgdg.data.exception.ComicsNotFoundException;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RealmComicDataStore implements ComicDataStore {

    private final RealmConfiguration realmConfiguration;

    @Inject
    public RealmComicDataStore(final RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    /**
     * Get a collection of {@link ComicBo}.
     *
     * @param characterId       int character identifier which we want to retrieve comics from.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    @Override
    public void getComics(int characterId, ComicListCallback comicListCallback) {
        final Realm realm = Realm.getInstance(realmConfiguration);

        RealmResults<ComicVo> comicVos = realm.where(ComicVo.class).equalTo(ComicVo.FIELD_CHARACTER_ID, characterId).findAll();
        List<ComicBo> comicBos = ComicVoMapper.toBo(comicVos);

        realm.close();

        if (comicBos != null && !comicBos.isEmpty()) {
            comicListCallback.onComicListLoaded(comicBos);
        } else {
            comicListCallback.onError(new ComicsNotFoundException());
        }
    }

    /**
     * Get a {@link ComicBo} by id.
     *
     * @param comicId             int comic identifier which we want to retrieve information.
     * @param comicDetailCallback a {@link ComicDetailCallback} to return info to presentation.
     */
    @Override
    public void getComic(int comicId, ComicDetailCallback comicDetailCallback) {
        final Realm realm = Realm.getInstance(realmConfiguration);

        ComicVo comicVo = realm.where(ComicVo.class).equalTo(ComicVo.PRIMARY_KEY, comicId).findFirst();
        ComicBo comicBo = ComicVoMapper.toBo(comicVo);

        realm.close();

        if (comicBo != null) {
            comicDetailCallback.onComicLoaded(comicBo);
        } else {
            comicDetailCallback.onError(new ComicNotFoundException());
        }
    }

    /**
     * Save a collection of {@link ComicBo}.
     *
     * @param characterId       int character identifier which we want to save comics for.
     * @param comicBoCollection collection of comics to save.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    @Override
    public void saveComics(int characterId, final Collection<ComicBo> comicBoCollection,
            final ComicListCallback comicListCallback) {
        final Realm realm = Realm.getInstance(realmConfiguration);

        final Collection<ComicVo> comicVos = ComicVoMapper.toVo(characterId, comicBoCollection);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(comicVos);
            }
        }, null);
    }
}
