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
package com.smassive.daggerworkshopgdg.data.bean.dto.common.mapper;

import com.smassive.daggerworkshopgdg.data.bean.dto.common.ImageResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.vo.ImageVo;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public final class ImageResponseDtoMapper {

    public static final String EXTENSION_SEPARATOR = ".";

    private ImageResponseDtoMapper() {
    }

    public static String toString(ImageResponseDto dto) {
        String imageUrl = null;

        if (dto != null) {
            imageUrl = dto.getPath() + EXTENSION_SEPARATOR + dto.getExtension();
        }

        return imageUrl;
    }

    public static List<String> toString(List<ImageResponseDto> dtos) {
        List<String> imageUrls = null;

        if (dtos != null && !dtos.isEmpty()) {
            imageUrls = new ArrayList<>(dtos.size());

            for (ImageResponseDto dto : dtos) {
                imageUrls.add(toString(dto));
            }
        }

        return imageUrls;
    }

    public static ImageVo toVo(ImageResponseDto dto) {
        ImageVo vo = null;

        if (dto != null) {
            vo = new ImageVo();

            vo.setUrl(toString(dto));
        }

        return vo;
    }

    public static RealmList<ImageVo> toVo(List<ImageResponseDto> dtos) {
        RealmList<ImageVo> vos = null;

        if (dtos != null && !dtos.isEmpty()) {
            vos = new RealmList<>();

            for (ImageResponseDto dto : dtos) {
                vos.add(toVo(dto));
            }
        }

        return vos;
    }
}
