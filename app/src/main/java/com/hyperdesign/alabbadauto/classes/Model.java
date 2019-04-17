package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 2/4/2018.
 */

public class Model {

    @SerializedName("id")
    public int id;

    public String imageUrlModel;
    @SerializedName("name_en")
    public String nameModelEn;

    @SerializedName("name_ar")
    public String nameModelAr;

    public Model() {
    }

    public Model(int id, String nameModelEn, String nameModelAr) {
        this.id = id;
        this.nameModelEn = nameModelEn;
        this.nameModelAr = nameModelAr;
    }

    public void setImageUrlModel(String imageUrlModel) {
        this.imageUrlModel = imageUrlModel;
    }

    public String getImageUrlModel() {
        return imageUrlModel;
    }

    public int getId() {
        return id;
    }

    public String getNameModelEn() {
        return nameModelEn;
    }

    public String getNameModelAr() {
        return nameModelAr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameModelEn(String nameModelEn) {
        this.nameModelEn = nameModelEn;
    }

    public void setNameModelAr(String nameModelAr) {
        this.nameModelAr = nameModelAr;
    }
}
