package com.hyperdesign.alabbadauto.classes.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/20/2018.
 */

public class Country {
    @SerializedName("id")
    private String countryId;

    @SerializedName("name_en")
    private String countryNameEn;

    @SerializedName("name_ar")
    private String countryNameAr;

    public Country(){

    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public void setCountryNameAr(String countryNameAr) {
        this.countryNameAr = countryNameAr;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public String getCountryNameAr() {
        return countryNameAr;
    }
}
