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

import com.smassive.daggerworkshopgdg.data.bean.dto.ListResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.dto.comic.ComicResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.dto.comic.ComicsResponseDto;
import com.smassive.daggerworkshopgdg.data.bean.vo.comic.ComicVo;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.ArrayList;
import java.util.List;

public final class ComicsResponseDtoMapper {

    private ComicsResponseDtoMapper() {
    }

    public static List<ComicBo> toBo(ComicsResponseDto dtos) {
        List<ComicBo> bos = null;

        if (dtos != null) {
            ListResponseDto<ComicResponseDto> data = dtos.getData();
            if (data != null) {
                List<ComicResponseDto> results = data.getResults();
                if (results != null && !results.isEmpty()) {
                    bos = new ArrayList<>();
                    for (ComicResponseDto dto : results) {
                        bos.add(ComicResponseDtoMapper.toBo(dto));
                    }
                }
            }
        }

        return bos;
    }

    public static List<ComicVo> toVo(ComicsResponseDto dtos, int characterId) {
        List<ComicVo> vos = null;

        if (dtos != null) {
            ListResponseDto<ComicResponseDto> data = dtos.getData();
            if (data != null) {
                List<ComicResponseDto> results = data.getResults();
                if (results != null && !results.isEmpty()) {
                    vos = new ArrayList<>();
                    for (ComicResponseDto dto : results) {
                        vos.add(ComicResponseDtoMapper.toVo(dto, characterId));
                    }
                }
            }
        }

        return vos;
    }
}
