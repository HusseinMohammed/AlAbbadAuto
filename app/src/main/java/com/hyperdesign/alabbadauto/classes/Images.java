package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.utilities.Image;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/24/2018.
 */

public class Images {
    @SerializedName("images")
    public ArrayList<String> urlImages;
    @SerializedName("header_photo1")
    public String firstImage;
    @SerializedName("header_photo2")
    public String secondImage;
    @SerializedName("header_photo3")
    public String thirdImage;
    @SerializedName("lang")
    public String lang;

    public Images(){}

    public Images(ArrayList<String> urlImages) {
        this.urlImages = urlImages;
    }

    public ArrayList<String> getUrlImages() {
        return urlImages;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public String getThirdImage() {
        return thirdImage;
    }

    public String getLang() {
        return lang;
    }

    public Images(String firstImage, String secondImage, String thirdImage, String lang) {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        this.thirdImage = thirdImage;
        this.lang = lang;
    }
}
