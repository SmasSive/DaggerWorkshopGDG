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
package com.smassive.daggerworkshopgdg.data.bean.dto.comic.mapper;

import com.smassive.daggerworkshopgdg.data.bean.dto.comic.ComicResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.dto.common.mapper.ImageResponseDtoMapper;
import com.smassive.daggerworkshopgdg.data.bean.vo.comic.ComicVo;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

public final class ComicResponseDtoMapper {

    private ComicResponseDtoMapper() {
    }

    public static ComicBo toBo(ComicResponseDto dto) {
        ComicBo bo = null;

        if (dto != null) {
            bo = new ComicBo();

            bo.setId(dto.getId());
            bo.setDescription(dto.getDescription());
            bo.setPageCount(dto.getPageCount());
            bo.setTitle(dto.getTitle());
            bo.setImageUrls(ImageResponseDtoMapper.toString(dto.getImages()));
            bo.setThumbnailUrl(ImageResponseDtoMapper.toString(dto.getThumbnail()));
        }

        return bo;
    }

    public static ComicVo toVo(ComicResponseDto dto, int characterId) {
        ComicVo vo = null;

        if (dto != null) {
            vo = new ComicVo();

            vo.setId(dto.getId());
            vo.setDescription(dto.getDescription());
            vo.setPageCount(dto.getPageCount());
            vo.setTitle(dto.getTitle());
            vo.setImages(ImageResponseDtoMapper.toVo(dto.getImages()));
            vo.setThumbnail(ImageResponseDtoMapper.toVo(dto.getThumbnail()));
            vo.setCharacterId(characterId);
        }

        return vo;
    }
}
