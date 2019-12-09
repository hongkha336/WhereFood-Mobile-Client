package com.wtf.wherefood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageSenderInfo{

    @SerializedName("token")
    private String token;


    public ImageSenderInfo() {
    }

    public ImageSenderInfo(String to) {
        this.token = to;
    }
}