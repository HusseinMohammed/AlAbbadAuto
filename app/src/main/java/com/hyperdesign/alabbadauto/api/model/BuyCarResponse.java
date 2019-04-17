package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.price.Prices;
import com.hyperdesign.alabbadauto.classes.user.User;

import java.util.ArrayList;

public class BuyCarResponse {

    @SerializedName("itemprice")
    private ArrayList<Prices> pricesList;

    @SerializedName("user")
    private User user;

    public ArrayList<Prices> getPricesList() {
        return pricesList;
    }

    public void setPricesList(ArrayList<Prices> pricesList) {
        this.pricesList = pricesList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
