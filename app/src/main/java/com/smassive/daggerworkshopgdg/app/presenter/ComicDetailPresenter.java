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
package com.smassive.daggerworkshopgdg.app.presenter;

import com.smassive.daggerworkshopgdg.app.injector.PerActivity;
import com.smassive.daggerworkshopgdg.app.model.ComicModel;
import com.smassive.daggerworkshopgdg.app.model.mapper.ComicModelMapper;
import com.smassive.daggerworkshopgdg.app.view.fragment.ComicDetailFragment;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;
import com.smassive.daggerworkshopgdg.domain.exception.ErrorBundle;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicUseCase;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation layer.
 */
@PerActivity
public class ComicDetailPresenter implements Presenter {

    private static final int RANDOM_MIN = 0;

    private static final String TAG = ComicDetailPresenter.class.getName();

    private ComicDetailFragment view;

    private final GetComicUseCase getComicUseCase;

    private ComicModel model;

    @Inject
    public ComicDetailPresenter(GetComicUseCase getComicUseCase) {
        this.getComicUseCase = getComicUseCase;
    }

    public void setView(@NonNull ComicDetailFragment comicDetailFragment) {
        this.view = comicDetailFragment;
    }

    public void getComic(int comicId) {
        getComicUseCase.execute(comicId, comicDetailCallback);
    }

    public String getRandomImageUrl(ComicModel model) {
        if (model != null) {
            List<String> imageUrls = model.getImageUrls();
            if (imageUrls != null && !imageUrls.isEmpty()) {
                return imageUrls.get(getRandomPosition(imageUrls.size() - 1));
            }
        }
        return "";
    }

    private int getRandomPosition(int max) {
        Random random = new Random();
        return random.nextInt(max - RANDOM_MIN + 1) + RANDOM_MIN;
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    @Override
    public void resume() {

    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    @Override
    public void pause() {

    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    @Override
    public void destroy() {

    }

    private void setModel() {
        view.setComicInfo(model);
    }

    private final GetComicUseCase.Callback comicDetailCallback = new GetComicUseCase.Callback() {
        @Override
        public void onComicDetailLoaded(ComicBo comicBo) {
            model = ComicModelMapper.toModel(comicBo);
            setModel();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(TAG, errorBundle.getErrorMessage());
        }
    };
}
