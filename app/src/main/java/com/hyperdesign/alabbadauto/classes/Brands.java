package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 2/4/2018.
 */

public class Brands {

    @SerializedName("id")
    public int id;

    @SerializedName("logo")
    public String imageUrlBrands;

    @SerializedName("name_en")
    public String brandNameEng;

    @SerializedName("name_ar")
    public String brandNameArab;

    public Brands() {
    }

    public Brands(int id, String brandNameEng, String textNameArab, String imageUrlBrands) {
        this.id = id;
        this.brandNameEng= brandNameEng;
        this.brandNameArab = textNameArab;
        this.imageUrlBrands = imageUrlBrands;
    }

    public void setImageUrlBrands(String imageUrlBrands) {
        this.imageUrlBrands = imageUrlBrands;
    }

    public void setTextDefineBrands(String textDefineBrands) {
        this.brandNameEng = textDefineBrands;
    }

    public int getId() {
        return id;
    }

    public String getImageUrlBrands() {
        return imageUrlBrands;
    }

    public String getBrandNameEng() {
        return brandNameEng;
    }

    public String getBrandNameArab() {
        return brandNameArab;
    }


}
