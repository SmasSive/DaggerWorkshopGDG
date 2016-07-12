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

import android.content.Context;
import com.smassive.daggerworkshopgdg.app.AndroidApplication;
import com.smassive.daggerworkshopgdg.app.R;
import com.smassive.daggerworkshopgdg.app.UIThread;
import com.smassive.daggerworkshopgdg.app.navigation.Navigator;
import com.smassive.daggerworkshopgdg.data.executor.JobExecutor;
import com.smassive.daggerworkshopgdg.data.net.ApiConstants;
import com.smassive.daggerworkshopgdg.data.net.ComicApiService;
import com.smassive.daggerworkshopgdg.data.net.interceptor.AuthInterceptor;
import com.smassive.daggerworkshopgdg.data.repository.ComicsRepositoryImpl;
import com.smassive.daggerworkshopgdg.data.repository.datasource.ComicDataStore;
import com.smassive.daggerworkshopgdg.data.repository.datasource.RealmComicDataStore;
import com.smassive.daggerworkshopgdg.data.repository.datasource.RetrofitComicDataStore;
import com.smassive.daggerworkshopgdg.domain.executor.PostExecutionThread;
import com.smassive.daggerworkshopgdg.domain.executor.ThreadExecutor;
import com.smassive.daggerworkshopgdg.domain.repository.ComicsRepository;
import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Navigator provideNavigator() {
    return new Navigator();
  }
  @Provides
  @Singleton
  Context provideContext() {
    return application;
  }

  @Provides
  @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  ComicsRepository provideComicsRepository(ComicsRepositoryImpl comicsRepository) {
    return comicsRepository;
  }

  @Provides
  @Singleton
  @Named("retrofit_comic_datastore") ComicDataStore provideRetrofitComicDataStore(ComicApiService comicApiService) {
    return new RetrofitComicDataStore(comicApiService);
  }

  @Provides
  @Singleton
  ComicApiService provideComicApiService(AuthInterceptor authInterceptor) {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(ApiConstants.ENDPOINT)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(ComicApiService.class);
  }

  @Provides
  @Singleton
  AuthInterceptor provideAuthInterceptor(@Named("public_key") String publicKey, @Named("private_key") String privateKey) {
    return new AuthInterceptor(publicKey, privateKey);
  }

  @Provides
  @Singleton
  @Named("public_key")
  String providePublicKey() {
    return application.getString(R.string.public_key);
  }

  @Provides
  @Singleton
  @Named("private_key")
  String providePrivateKey() {
    return application.getString(R.string.private_key);
  }

  @Provides
  @Singleton
  @Named("realm_comic_datastore")
  ComicDataStore provideRealmComicDataStore(RealmConfiguration realmConfiguration) {
    return new RealmComicDataStore(realmConfiguration);
  }

  @Provides
  @Singleton RealmConfiguration provideRealmConfiguration(Context context) {
    return new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
  }
}
