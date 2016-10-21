package com.siziksu.gallery.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class FileUtils {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static FileUtils instance;

    private final Context context;

    private FileUtils(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new FileUtils(context);
        }
    }

    public static FileUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("This class must be initialized");
        }
        return instance;
    }

    public String getStringFromFile(String file) {
        String string = "";
        AssetManager manager = context.getAssets();
        try {
            InputStream stream = manager.open(file);
            string = convertStreamToString(stream);
            stream.close();
        } catch (Exception e) {
            Log.d(Constants.TAG, e.getMessage(), e);
        }
        return string;
    }

    private String convertStreamToString(InputStream stream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, CHARSET_UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}