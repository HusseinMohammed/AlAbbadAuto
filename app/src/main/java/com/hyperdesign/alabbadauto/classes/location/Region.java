package com.hyperdesign.alabbadauto.classes.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/20/2018.
 */

public class Region {
    @SerializedName("id")
    private String regionId;
    @SerializedName("name_en")
    private String regionNameEn;
    @SerializedName("name_ar")
    private String regionNameAr;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionNameEn() {
        return regionNameEn;
    }

    public void setRegionNameEn(String regionNameEn) {
        this.regionNameEn = regionNameEn;
    }

    public String getRegionNameAr() {
        return regionNameAr;
    }

    public void setRegionNameAr(String regionNameAr) {
        this.regionNameAr = regionNameAr;
    }
}
