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
package com.smassive.daggerworkshopgdg.data.bean.vo.comic;

import com.smassive.daggerworkshopgdg.data.bean.vo.ImageVo;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Value Object that represents the comic data in the database.
 */
public class ComicVo extends RealmObject {

    public static final String PRIMARY_KEY = "id";

    public static final String FIELD_CHARACTER_ID = "characterId";

    @PrimaryKey
    private int id;

    private String title;

    private String description;

    private int pageCount;

    private ImageVo thumbnail;

    private RealmList<ImageVo> images;

    private int characterId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public ImageVo getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageVo thumbnail) {
        this.thumbnail = thumbnail;
    }

    public RealmList<ImageVo> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageVo> images) {
        this.images = images;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
}
