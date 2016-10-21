package com.siziksu.gallery;

import android.app.Application;

import com.siziksu.gallery.common.FileUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileUtils.init(this);
    }
}
