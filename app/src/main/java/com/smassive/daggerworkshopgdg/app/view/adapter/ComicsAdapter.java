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
package com.smassive.daggerworkshopgdg.app.view.adapter;

import com.smassive.daggerworkshopgdg.app.R;
import com.smassive.daggerworkshopgdg.app.model.ComicModel;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for comics list.
 */
public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {

    private Context context;

    private List<ComicModel> items;

    private OnComicClickedListener onComicClickedListener;

    public ComicsAdapter(Context context, List<ComicModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        final ComicModel model = items.get(position);
        holder.item = model;
        holder.title.setText(model.getTitle());

        Picasso.with(context).load(model.getThumbnailUrl()).error(R.drawable.default_comic).fit().centerCrop().into(
                holder.thumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onComicClickedListener != null) {
                    onComicClickedListener.onComicClicked(model);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public void setListener(OnComicClickedListener listener) {
        this.onComicClickedListener = listener;
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumbnail)
        ImageView thumbnail;

        @Bind(R.id.title)
        TextView title;

        public ComicModel item;

        public View itemView;

        public ComicViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.itemView = itemView;
        }
    }

    public interface OnComicClickedListener {
        void onComicClicked(ComicModel comicModel);
    }
}
