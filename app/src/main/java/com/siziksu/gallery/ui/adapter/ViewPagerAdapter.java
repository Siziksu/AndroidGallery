package com.siziksu.gallery.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private final List<Image> images;

    public ViewPagerAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_fullscreen, container, false);
        ImageView imageViewPreview = (ImageView) view.findViewById(R.id.fullscreenImage);
        Image image = images.get(position);
        File file = new File(image.getUrl());
        if (file.exists()) {
            Glide.with(context).load(file)
                 .thumbnail(Constants.THUMBNAIL_RATIO)
                 .crossFade()
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .into(imageViewPreview);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
