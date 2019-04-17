package com.hyperdesign.alabbadauto.classes.options;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/26/2018.
 */

public class Options {

    @SerializedName("id")
    private int id;

    @SerializedName("value_en")
    private String valueEn;

    @SerializedName("value_ar")
    private String valueAr;

    @SerializedName("cat_options_id")
    private int catId;

    @SerializedName("name_en")
    private String nameEn;

    @SerializedName("name_ar")
    private String nameAr;

    public Options(){

    }

    public Options(int id, String valueEn, int catId, String nameEn) {
        this.id = id;
        this.valueEn = valueEn;
        this.catId = catId;
        this.nameEn = nameEn;
    }

    public Options(String valueAr, int catId, int id, String nameAr) {
        this.valueAr = valueAr;
        this.catId = catId;
        this.id = id;
        this.nameAr = nameAr;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    public String getValueAr() {
        return valueAr;
    }

    public void setValueAr(String valueAr) {
        this.valueAr = valueAr;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
}
