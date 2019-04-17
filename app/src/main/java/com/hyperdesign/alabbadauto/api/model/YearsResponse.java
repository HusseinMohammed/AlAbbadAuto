package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Brands;
import com.hyperdesign.alabbadauto.classes.Years;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/25/2018.
 */

public class YearsResponse {
    @SerializedName("years")
    private ArrayList<Years> yearsList;

    public void setYearsList(ArrayList<Years> yearsList) {
        this.yearsList = yearsList;
    }

    public ArrayList<Years> getYearsList() {
        return yearsList;
    }
}
