package com.siziksu.gallery.ui.adapter;

import android.support.v4.view.ViewPager;

public class ViewPagerListener implements ViewPager.OnPageChangeListener {

    private final OnPageSelected listener;

    public ViewPagerListener(OnPageSelected listener) {
        this.listener = listener;
    }

    @Override
    public void onPageSelected(int position) {
        if (listener != null) {
            listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnPageSelected {

        void onPageSelected(int position);
    }
}
