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

import com.smassive.daggerworkshopgdg.app.model.ComicModel;
import com.smassive.daggerworkshopgdg.app.model.mapper.ComicModelMapper;
import com.smassive.daggerworkshopgdg.app.view.activity.MainActivity;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;
import com.smassive.daggerworkshopgdg.domain.exception.ErrorBundle;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicsUseCase;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.List;

/**
 * {@link Presenter} that controls communication between views and models of the presentation layer.
 */
public class ComicsPresenter implements Presenter {

    private static final String TAG = ComicsPresenter.class.getName();

    private MainActivity view;

    private final GetComicsUseCase getComicsUseCase;

    private List<ComicModel> models;

    public ComicsPresenter(GetComicsUseCase getComicsUseCase) {
        this.getComicsUseCase = getComicsUseCase;
    }

    public void setView(@NonNull MainActivity mainActivity) {
        this.view = mainActivity;
    }

    public void getComics(int characterId, boolean refresh) {
        getComicsUseCase.execute(characterId, refresh, comicListCallback);
    }

    private void setModels() {
        if (models != null && !models.isEmpty()) {
            view.setItems(models);
        } else {

        }
    }

    private void stopRefresh() {
        view.stopRefresh();
    }

    private void showMessage(String message) {
        view.showMessage(message);
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

    private final GetComicsUseCase.Callback comicListCallback = new GetComicsUseCase.Callback() {
        @Override
        public void onComicListLoaded(Collection<ComicBo> comicBos) {
            models = ComicModelMapper.toModel(comicBos);
            setModels();
            stopRefresh();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(TAG, errorBundle.getErrorMessage());
            showMessage(errorBundle.getErrorMessage());
            stopRefresh();
        }
    };
}
