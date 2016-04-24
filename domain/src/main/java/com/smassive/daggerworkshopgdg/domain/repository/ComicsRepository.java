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
package com.smassive.daggerworkshopgdg.domain.repository;

import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;
import com.smassive.daggerworkshopgdg.domain.exception.ErrorBundle;

import java.util.Collection;

/**
 * Interface for retrieving comics data.
 */
public interface ComicsRepository {

    /**
     * Callback used to be notified when either a comic list has been loaded or an error happened.
     */
    interface ComicListCallback {
        void onComicListLoaded(Collection<ComicBo> comicBoCollection);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Callback used to be notified when either a comic has been loaded or an error happened.
     */
    interface ComicDetailCallback {
        void onComicLoaded(ComicBo comic);

        void onError(ErrorBundle errorBundle);
    }

    /**
     * Get a collection of {@link ComicBo}.
     *
     * @param characterId int character identifier which we want to retrieve comics from.
     * @param refresh boolean true if the request should be done to the cloud.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    void getComics(int characterId, boolean refresh, ComicListCallback comicListCallback);

    /**
     * Get a {@link ComicBo} by id.
     *
     * @param comicId int comic identifier which we want to retrieve information.
     * @param comicDetailCallback a {@link ComicDetailCallback} to return info to presentation.
     */
    void getComic(int comicId, ComicDetailCallback comicDetailCallback);
}
