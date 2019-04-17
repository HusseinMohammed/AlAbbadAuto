package com.hyperdesign.alabbadauto.classes.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/20/2018.
 */

public class Area {
    @SerializedName("id")
    private String areaId;
    @SerializedName("name_en")
    private String areaNameEn;
    @SerializedName("name_ar")
    private String areaNameAr;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String cityId) {
        this.areaId = cityId;
    }

    public String getAreaNameEn() {
        return areaNameEn;
    }

    public void setAreaNameEn(String areaNameEn) {
        this.areaNameEn = areaNameEn;
    }

    public String getAreaNameAr() {
        return areaNameAr;
    }

    public void setAreaNameAr(String areaNameAr) {
        this.areaNameAr = areaNameAr;
    }
}
