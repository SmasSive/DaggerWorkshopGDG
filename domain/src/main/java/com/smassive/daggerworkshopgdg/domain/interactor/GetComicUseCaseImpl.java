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
package com.smassive.daggerworkshopgdg.domain.interactor;

import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;
import com.smassive.daggerworkshopgdg.domain.exception.ErrorBundle;
import com.smassive.daggerworkshopgdg.domain.executor.PostExecutionThread;
import com.smassive.daggerworkshopgdg.domain.executor.ThreadExecutor;
import com.smassive.daggerworkshopgdg.domain.repository.ComicsRepository;
import javax.inject.Inject;

/**
 * Get comic by comic identifier use case.
 */
public class GetComicUseCaseImpl implements GetComicUseCase {

    private final ComicsRepository comicsRepository;

    private final ThreadExecutor threadExecutor;

    private final PostExecutionThread postExecutionThread;

    private int comicId;

    private Callback callback;

    @Inject
    public GetComicUseCaseImpl(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
            ComicsRepository comicsRepository) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.comicsRepository = comicsRepository;
    }

    @Override
    public void execute(int comicId, GetComicUseCase.Callback callback) {
        if (comicId < 0 && callback == null) {
            throw new IllegalArgumentException("Invalid parameters!!!");
        }
        this.comicId = comicId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.comicsRepository.getComic(comicId, repositoryCallback);
    }

    private final ComicsRepository.ComicDetailCallback repositoryCallback = new ComicsRepository.ComicDetailCallback() {
        @Override
        public void onComicLoaded(ComicBo comic) {
            notifyGetComicDetailSuccessfully(comic);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyError(errorBundle);
        }
    };

    private void notifyGetComicDetailSuccessfully(final ComicBo comicBo) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onComicDetailLoaded(comicBo);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
