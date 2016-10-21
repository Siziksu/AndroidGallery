package com.siziksu.gallery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siziksu.gallery.R;
import com.siziksu.gallery.common.Constants;
import com.siziksu.gallery.common.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.LocalViewHolder> {

    private final List<Image> images;
    private final Context context;
    private final ClickListener listener;

    public GalleryAdapter(Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.images = new ArrayList<>();
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thumbnail, parent, false);
        return new LocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        Image image = images.get(position);
        File file = new File(image.getUrl());
        if (file.exists()) {
            Glide.with(context).load(file)
                 .thumbnail(Constants.THUMBNAIL_RATIO)
                 .placeholder(R.drawable.default_placeholder)
                 .crossFade()
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void showImages(List<Image> images) {
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    final class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;

        LocalViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnailImage);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface ClickListener {

        void onClick(View view, int position);
    }
}