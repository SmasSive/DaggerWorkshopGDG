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

import com.smassive.daggerworkshopgdg.data.bean.dto.comic.ComicsResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.dto.comic.mapper.ComicsResponseDtoMapper;
import com.smassive.daggerworkshopgdg.data.exception.ComicsNotFoundException;
import com.smassive.daggerworkshopgdg.data.net.ApiConstants;
import com.smassive.daggerworkshopgdg.data.net.ComicApiService;
import com.smassive.daggerworkshopgdg.data.net.interceptor.AuthInterceptor;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.Collection;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitComicDataStore implements ComicDataStore {

    private ComicApiService comicApiService;

    public RetrofitComicDataStore(String publicKey, String privateKey) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        AuthInterceptor authInterceptor = new AuthInterceptor(publicKey, privateKey);

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.ENDPOINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.comicApiService = retrofit.create(ComicApiService.class);
    }

    /**
     * Get a collection of {@link ComicBo}.
     *
     * @param characterId       int character identifier which we want to retrieve comics from.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    @Override
    public void getComics(int characterId, final ComicListCallback comicListCallback) {
        Call<ComicsResponseDto> call = comicApiService.getComicsByCharacterId(characterId);
        call.enqueue(new Callback<ComicsResponseDto>() {
            @Override
            public void onResponse(Call<ComicsResponseDto> call, Response<ComicsResponseDto> response) {
                if (response != null) {
                    comicListCallback.onComicListLoaded(ComicsResponseDtoMapper.toBo(response.body()));
                } else {
                    comicListCallback.onError(new ComicsNotFoundException());
                }
            }

            @Override
            public void onFailure(Call<ComicsResponseDto> call, Throwable t) {
                comicListCallback.onError(new ComicsNotFoundException(t.getMessage()));
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
    public void getComic(int comicId, ComicDetailCallback comicDetailCallback) {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    /**
     * Save a collection of {@link ComicBo}.
     *
     * @param characterId       int character identifier which we want to save comics for.
     * @param comicBoCollection collection of comics to save.
     * @param comicListCallback a {@link ComicListCallback} to return info to presentation.
     */
    @Override
    public void saveComics(int characterId, Collection<ComicBo> comicBoCollection, ComicListCallback comicListCallback) {
        throw new UnsupportedOperationException("Operation not allowed");
    }
}
