package com.siziksu.gallery.ui.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siziksu.gallery.R;
import com.siziksu.gallery.common.Constants;
import com.siziksu.gallery.common.model.Image;
import com.siziksu.gallery.ui.adapter.ViewPagerAdapter;
import com.siziksu.gallery.ui.adapter.ViewPagerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderFragment extends DialogFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Image> images = new ArrayList<>();

    public static SliderFragment newInstance() {
        return new SliderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null
            && getArguments().containsKey(Constants.IMAGES_KEY)
            && getArguments().containsKey(Constants.POSITION_KEY)) {
            List<Image> images = getArguments().getParcelableArrayList(Constants.IMAGES_KEY);
            if (images != null) {
                this.images.addAll(images);
                int selected = getArguments().getInt(Constants.POSITION_KEY);
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), this.images);
                viewPager.setAdapter(viewPagerAdapter);
                viewPager.addOnPageChangeListener(new ViewPagerListener(null));
                viewPager.setCurrentItem(selected, false);
            }
        }
        return view;
    }

}
