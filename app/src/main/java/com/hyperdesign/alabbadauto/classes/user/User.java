package com.hyperdesign.alabbadauto.classes.user;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("type")
    private String type;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address_en")
    private String addEn;
    @SerializedName("address_ar")
    private String addAr;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(int id, String name, String email, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddEn() {
        return addEn;
    }

    public void setAddEn(String addEn) {
        this.addEn = addEn;
    }

    public String getAddAr() {
        return addAr;
    }

    public void setAddAr(String addAr) {
        this.addAr = addAr;
    }
}
