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
package com.smassive.daggerworkshopgdg.app.view.activity;

import com.smassive.daggerworkshopgdg.app.R;
import com.smassive.daggerworkshopgdg.app.injector.component.ApplicationComponent;
import com.smassive.daggerworkshopgdg.app.view.fragment.ComicDetailFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Detail of a specific comic screen.
 */
public class ComicDetailActivity extends BaseActivity {

    private static final String EXTRA_COMIC_ID = "EXTRA_COMIC_ID";

    @Bind(R.id.comic_image)
    ImageView comicImage;

    private int comicId;

    public static Intent getCallingIntent(Context context, int comicId) {
        Intent callingIntent = new Intent(context, ComicDetailActivity.class);
        callingIntent.putExtra(EXTRA_COMIC_ID, comicId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.bind(this);

        setUpToolbar(true);

        if (savedInstanceState == null) {
            comicId = getIntent().getIntExtra(EXTRA_COMIC_ID, -1);
            addFragment(R.id.item_detail_container, ComicDetailFragment.newInstance(comicId));
        } else {
            comicId = savedInstanceState.getInt(EXTRA_COMIC_ID);
        }
    }

    @Override
    protected void initializeInjector(ApplicationComponent applicationComponent) {
        applicationComponent.inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(EXTRA_COMIC_ID, comicId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigator.goToList(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
