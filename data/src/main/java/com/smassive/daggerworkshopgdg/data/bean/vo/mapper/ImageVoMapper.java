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
package com.smassive.daggerworkshopgdg.data.bean.vo.mapper;

import com.smassive.daggerworkshopgdg.data.bean.vo.ImageVo;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public final class ImageVoMapper {

    private ImageVoMapper() {
    }

    public static String toString(ImageVo vo) {
        String url = null;

        if (vo != null) {
            url = vo.getUrl();
        }

        return url;
    }

    public static List<String> toString(List<ImageVo> vos) {
        List<String> urls = null;

        if (vos != null && !vos.isEmpty()) {
            urls = new ArrayList<>();

            for (ImageVo vo : vos) {
                urls.add(toString(vo));
            }
        }

        return urls;
    }

    public static ImageVo toVo(String imageUrl) {
        ImageVo vo = null;

        if (imageUrl != null) {
            vo = new ImageVo();

            vo.setUrl(imageUrl);
        }

        return vo;
    }

    public static RealmList<ImageVo> toVo(List<String> imageUrls) {
        RealmList<ImageVo> vos = null;

        if (imageUrls != null && !imageUrls.isEmpty()) {
            vos = new RealmList<>();

            for (String imageUrl : imageUrls) {
                vos.add(toVo(imageUrl));
            }
        }

        return vos;
    }
}
