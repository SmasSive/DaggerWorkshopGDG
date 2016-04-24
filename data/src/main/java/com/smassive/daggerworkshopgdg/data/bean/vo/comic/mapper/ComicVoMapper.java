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
package com.smassive.daggerworkshopgdg.data.bean.vo.comic.mapper;


import com.smassive.daggerworkshopgdg.data.bean.vo.comic.ComicVo;
import com.smassive.daggerworkshopgdg.data.bean.vo.mapper.ImageVoMapper;
import com.smassive.daggerworkshopgdg.domain.bean.ComicBo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmResults;

public final class ComicVoMapper {

    private ComicVoMapper() {
    }

    public static ComicBo toBo(ComicVo vo) {
        ComicBo bo = null;

        if (vo != null) {
            bo = new ComicBo();

            bo.setId(vo.getId());
            bo.setTitle(vo.getTitle());
            bo.setPageCount(vo.getPageCount());
            bo.setDescription(vo.getDescription());
            bo.setThumbnailUrl(ImageVoMapper.toString(vo.getThumbnail()));
            bo.setImageUrls(ImageVoMapper.toString(vo.getImages()));
        }

        return bo;
    }

    public static List<ComicBo> toBo(RealmResults<ComicVo> vos) {
        List<ComicBo> bos = null;

        if (vos != null && !vos.isEmpty()) {
            bos = new ArrayList<>();

            for (ComicVo vo : vos) {
                bos.add(toBo(vo));
            }
        }

        return bos;
    }

    public static ComicVo toVo(int characterId, ComicBo bo) {
        ComicVo vo = null;

        if (bo != null) {
            vo = new ComicVo();

            vo.setId(bo.getId());
            vo.setCharacterId(characterId);
            vo.setDescription(bo.getDescription());
            vo.setPageCount(bo.getPageCount());
            vo.setTitle(bo.getTitle());
            vo.setImages(ImageVoMapper.toVo(bo.getImageUrls()));
            vo.setThumbnail(ImageVoMapper.toVo(bo.getThumbnailUrl()));
        }

        return vo;
    }

    public static Collection<ComicVo> toVo(int characterId, Collection<ComicBo> bos) {
        List<ComicVo> vos = null;

        if (bos != null && !bos.isEmpty()) {
            vos = new ArrayList<>();

            for (ComicBo bo : bos) {
                vos.add(toVo(characterId, bo));
            }
        }

        return vos;
    }
}
