
package com.siziksu.gallery.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("url")
    @Expose
    private String url;

    public Image(String url) {
        this.url = url;
    }

    private Image(Parcel in) {
        this.url = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {dest.writeString(this.url);}

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {return new Image(source);}

        @Override
        public Image[] newArray(int size) {return new Image[size];}
    };
}
