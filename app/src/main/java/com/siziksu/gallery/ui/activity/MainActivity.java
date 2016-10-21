package com.siziksu.gallery.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.siziksu.gallery.R;
import com.siziksu.gallery.common.Constants;
import com.siziksu.gallery.common.manager.PermissionManager;
import com.siziksu.gallery.common.model.Image;
import com.siziksu.gallery.presenter.main.IMainPresenter;
import com.siziksu.gallery.presenter.main.IMainView;
import com.siziksu.gallery.presenter.main.MainPresenter;
import com.siziksu.gallery.ui.adapter.GalleryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyView)
    TextView emptyView;

    private IMainPresenter<IMainView> presenter;
    private boolean onRequestPermissions;
    private GalleryAdapter adapter;

    private static final int GRID_SPAN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.init();
        presenter = new MainPresenter();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        adapter = new GalleryAdapter(getApplicationContext(), presenter.getListener());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), GRID_SPAN);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register(this);
        if (!onRequestPermissions) {
            if (PermissionManager.getInstance().verifyStoragePermissions(this)) {
                presenter.findImages();
            }
        } else {
            onRequestPermissions = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregister();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void showImages(List<Image> images) {
        adapter.showImages(images);
        emptyView.setVisibility(images.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissions = true;
        switch (requestCode) {
            case PermissionManager.REQUEST_EXTERNAL_STORAGE:
                if (PermissionManager.getInstance().checkStoragePermissionsResult(grantResults)) {
                    presenter.findImages();
                } else {
                    Log.d(Constants.TAG, "Backup denied because permissions are denied");
                }
                break;
            default:
                break;
        }
    }
}