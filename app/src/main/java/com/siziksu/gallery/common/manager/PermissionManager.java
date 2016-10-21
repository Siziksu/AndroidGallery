package com.siziksu.gallery.common.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionManager {

    public static final int REQUEST_EXTERNAL_STORAGE = 13425;

    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static PermissionManager instance;

    private PermissionManager() {
        // Constructor
    }

    public static void init() {
        if (instance == null) {
            instance = new PermissionManager();
        }
    }

    public static PermissionManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("This class must be initialized");
        }
        return instance;
    }

    public boolean verifyStoragePermissions(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int write = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (write != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
                return false;
            }
        }
        return true;
    }

    public boolean checkStoragePermissionsResult(int[] grantResults) {
        return grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }
}
