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
package com.smassive.daggerworkshopgdg.data.net;

import com.smassive.daggerworkshopgdg.data.bean.dto.comic.ComicsResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface representing the comic API service.
 */
public interface ComicApiService {

    /**
     * Get all the comics from a specified character.
     *
     * @param characterId int identifier of the character whose comics would be downloaded.
     */
    @GET("characters/{characterId}/comics")
    Call<ComicsResponseDto> getComicsByCharacterId(@Path("characterId") int characterId);
}
