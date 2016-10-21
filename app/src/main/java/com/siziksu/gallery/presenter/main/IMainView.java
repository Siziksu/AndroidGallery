package com.siziksu.gallery.presenter.main;

import android.support.v7.app.AppCompatActivity;

import com.siziksu.gallery.common.model.Image;

import java.util.List;

public interface IMainView {

    AppCompatActivity getActivity();

    void showImages(List<Image> images);
}
