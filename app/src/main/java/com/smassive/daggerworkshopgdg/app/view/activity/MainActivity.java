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
package com.smassive.daggerworkshopgdg.app.view.activity;

import com.smassive.daggerworkshopgdg.app.AndroidApplication;
import com.smassive.daggerworkshopgdg.app.R;
import com.smassive.daggerworkshopgdg.app.UIThread;
import com.smassive.daggerworkshopgdg.app.injector.component.ApplicationComponent;
import com.smassive.daggerworkshopgdg.app.injector.component.DaggerComicsComponent;
import com.smassive.daggerworkshopgdg.app.injector.module.ActivityModule;
import com.smassive.daggerworkshopgdg.app.injector.module.ComicsModule;
import com.smassive.daggerworkshopgdg.app.model.ComicModel;
import com.smassive.daggerworkshopgdg.app.presenter.ComicsPresenter;
import com.smassive.daggerworkshopgdg.app.view.adapter.ComicsAdapter;
import com.smassive.daggerworkshopgdg.app.view.fragment.ComicDetailFragment;
import com.smassive.daggerworkshopgdg.data.executor.JobExecutor;
import com.smassive.daggerworkshopgdg.data.repository.ComicsRepositoryImpl;
import com.smassive.daggerworkshopgdg.data.repository.datasource.ComicDataStoreFactory;
import com.smassive.daggerworkshopgdg.domain.executor.PostExecutionThread;
import com.smassive.daggerworkshopgdg.domain.executor.ThreadExecutor;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicsUseCase;
import com.smassive.daggerworkshopgdg.domain.interactor.GetComicsUseCaseImpl;
import com.smassive.daggerworkshopgdg.domain.repository.ComicsRepository;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main screen of the application. Contains the list of comics retrieved for a specific super hero.
 * This Master-Detail activity is shown in two designs depending on which device is displayed, just the list of comics if the
 * device is a handset or two panel (list + detail) if the device is a tablet.
 */
public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, ComicsAdapter.OnComicClickedListener {

    private static final String DETAIL_FRAGMENT = "DETAIL_FRAGMENT";

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.item_list)
    RecyclerView recyclerView;

    @Inject
    ComicsPresenter comicsPresenter;

    private boolean twoPanel;

    // tell dagger he should inject this!
    @Inject
    @Named("character_id")
    int characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar(false);

        if (findViewById(R.id.item_detail_container) != null) {
            twoPanel = true;
        }

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        initializePresenter();
    }

    /**
     * Initialize injections by field.
     *
     * @param applicationComponent {@link ApplicationComponent} main component.
     */
    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);

        // PerActivity injector initialization
        DaggerComicsComponent.builder()
                .applicationComponent(applicationComponent) // Main component must be set
                .activityModule(new ActivityModule(this)) // Initialize dependencies
                .comicsModule(new ComicsModule()).build().inject(this); // Make PerActivity module
    }

    private void initializePresenter() {
        comicsPresenter.setView(this);
        comicsPresenter.getComics(characterId, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.comicsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.comicsPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.comicsPresenter.destroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (twoPanel) {
            FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
            Fragment detailFragment = getFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
            if (detailFragment != null) {
                fragmentTransaction.remove(detailFragment);
            }
            fragmentTransaction.commit();
        }

        super.onSaveInstanceState(outState);
    }

    public void setItems(List<ComicModel> models) {
        ComicsAdapter adapter = new ComicsAdapter(this, models);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void showNoItems() {
        recyclerView.setVisibility(View.GONE);

    }

    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showMessage(String message) {
        Snackbar.make(recyclerView, R.string.error_request, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        comicsPresenter.getComics(characterId, true);
    }

    @Override
    public void onComicClicked(ComicModel comicModel) {
        if (comicModel != null) {
            if (twoPanel) {
                replaceFragment(R.id.item_detail_container, ComicDetailFragment.newInstance(comicModel.getId()), DETAIL_FRAGMENT);
            } else {
                navigator.goToDetail(this, comicModel.getId());
            }
        } else {
            showMessage(null);
        }
    }
}
