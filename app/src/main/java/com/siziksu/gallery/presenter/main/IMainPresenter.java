package com.siziksu.gallery.presenter.main;

import com.siziksu.gallery.ui.adapter.GalleryAdapter;

public abstract class IMainPresenter<V> {

    protected V view;

    public void register(final V view) {
        this.view = view;
    }

    public void unregister() {
        this.view = null;
    }

    public abstract void findImages();

    public abstract GalleryAdapter.ClickListener getListener();
}
