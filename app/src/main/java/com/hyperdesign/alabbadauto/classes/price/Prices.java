package com.hyperdesign.alabbadauto.classes.price;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/26/2018.
 */

public class Prices {

    @SerializedName("id")
    private int id;
    @SerializedName("name_en")
    private String nameEn;
    @SerializedName("name_ar")
    private String nameAr;
    @SerializedName("price")
    private Double price;
    @SerializedName("item_id")
    private int itemId;

    public Prices(){

    }

    public Prices(int id, String nameEn, Double price) {
        this.id = id;
        this.nameEn = nameEn;
        this.price = price;
    }

    public Prices(String nameAr, Double price, int id) {
        this.id = id;
        this.nameAr = nameAr;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
