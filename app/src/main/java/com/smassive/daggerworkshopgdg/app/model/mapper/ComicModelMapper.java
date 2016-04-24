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
package com.smassive.daggerworkshopgdg.app.model.mapper;

import com.smassive.daggerworkshopgdg.app.model.ComicModel;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ComicModelMapper {

    private ComicModelMapper() {
    }

    public static ComicModel toModel(ComicBo bo) {
        ComicModel model = null;

        if (bo != null) {
            model = new ComicModel();

            model.setId(bo.getId());
            model.setDescription(bo.getDescription());
            model.setPageCount(bo.getPageCount());
            model.setTitle(bo.getTitle());
            model.setThumbnailUrl(bo.getThumbnailUrl());

            model.setImageUrls(bo.getImageUrls());
        }

        return model;
    }

    public static List<ComicModel> toModel(Collection<ComicBo> bos) {
        List<ComicModel> models = null;

        if (bos != null && !bos.isEmpty()) {
            models = new ArrayList<>(bos.size());

            for (ComicBo bo : bos) {
                models.add(toModel(bo));
            }
        }

        return models;
    }


}
