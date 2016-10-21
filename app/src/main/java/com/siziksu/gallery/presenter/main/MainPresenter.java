package com.siziksu.gallery.presenter.main;

import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;

import com.siziksu.gallery.common.AsyncObject;
import com.siziksu.gallery.common.Constants;
import com.siziksu.gallery.common.model.Image;
import com.siziksu.gallery.ui.activity.SliderFragment;
import com.siziksu.gallery.ui.adapter.GalleryAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter extends IMainPresenter<IMainView> {

    private List<Image> images = new ArrayList<>();

    public MainPresenter() {}

    @Override
    public void findImages() {
        new AsyncObject<List<Image>>()
                .subscribeOnMainThread()
                .action(() -> {
                            List<Image> images = new ArrayList<>();
                            String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            String path = externalPath
                                          + File.separator
                                          + Environment.DIRECTORY_DCIM
                                          + File.separator
                                          + Constants.DCIM_100ANDRO;
                            final File folder = new File(path);
                            if (folder.exists() && folder.isDirectory()) {
                                File[] pics = folder.listFiles(file -> file.exists()
                                                                       && file.canRead()
                                                                       && file.isFile()
                                                                       && !file.isDirectory()
                                                                       && file.getName().matches(Constants.PATTERN_IMAGES));
                                if (pics != null) {
                                    for (File pic : pics) {
                                        images.add(new Image(pic.getAbsolutePath()));
                                    }
                                }
                            }
                            return images;
                        }
                )
                .subscribe(this::showImages);
    }

    @Override
    public GalleryAdapter.ClickListener getListener() {
        return (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(Constants.IMAGES_KEY, (ArrayList<? extends Parcelable>) images);
            bundle.putInt(Constants.POSITION_KEY, position);
            if (this.view != null) {
                FragmentTransaction transaction = this.view.getActivity().getSupportFragmentManager().beginTransaction();
                SliderFragment fragment = SliderFragment.newInstance();
                fragment.setArguments(bundle);
                fragment.show(transaction, Constants.FRAGMENT_SLIDE);
            }
        };
    }

    private void showImages(List<Image> images) {
        this.images.clear();
        this.images = images;
        view.showImages(images);
    }
}
