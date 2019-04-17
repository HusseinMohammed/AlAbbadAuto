package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Brands;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/25/2018.
 */

public class BrandsResponse {
    @SerializedName("brands")
    private ArrayList<Brands> brandsList;

    public void setBrandsList(ArrayList<Brands> brandsList) {
        this.brandsList = brandsList;
    }

    public ArrayList<Brands> getBrandsList() {
        return brandsList;
    }
}
