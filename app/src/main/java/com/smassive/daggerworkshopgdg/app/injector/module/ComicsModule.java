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
package com.smassive.daggerworkshopgdg.app.injector.module;

import com.smassive.daggerworkshopgdg.app.R;
import com.smassive.daggerworkshopgdg.app.injector.PerActivity;
import com.smassive.daggerworkshopgdg.domain.executor.PostExecutionThread;
import com.smassive.daggerworkshopgdg.domain.executor.ThreadExecutor;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicsUseCase;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicsUseCaseImpl;
import com.smassive.daggerworkshopgdg.domain.repository.ComicsRepository;

import android.app.Activity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicsModule {

    @Provides
    @PerActivity
    @Named("character_id")
    int provideCharacterId(Activity activity) {
        return Integer.valueOf(activity.getString(R.string.character_id));
    }

    @Provides
    @PerActivity
    GetComicsUseCase provideGetComicsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            ComicsRepository comicsRepository) {
        return new GetComicsUseCaseImpl(threadExecutor, postExecutionThread, comicsRepository);
    }
}
