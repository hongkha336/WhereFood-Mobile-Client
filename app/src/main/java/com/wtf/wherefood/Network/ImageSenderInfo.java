package com.wtf.wherefood.Network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageSenderInfo {

    @SerializedName("token")
    private String token;
    public ImageSenderInfo(String token) {
        this.token = token;
    }

}