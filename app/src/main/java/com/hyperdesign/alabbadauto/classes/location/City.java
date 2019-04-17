package com.hyperdesign.alabbadauto.classes.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/20/2018.
 */

public class City {
    @SerializedName("id")
    private String cityId;
    @SerializedName("name_en")
    private String cityNameEn;
    @SerializedName("name_ar")
    private String cityNameAr;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public String getCityNameAr() {
        return cityNameAr;
    }

    public void setCityNameAr(String cityNameAr) {
        this.cityNameAr = cityNameAr;
    }
}
