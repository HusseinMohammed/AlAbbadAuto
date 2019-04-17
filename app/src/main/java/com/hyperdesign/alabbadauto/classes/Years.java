package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/14/2018.
 */

public class Years {
    @SerializedName("id")
    public int id;

    @SerializedName("year")
    public String year;

    public Years() {
    }

    public Years(int id, String year) {
        this.id = id;
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }
}
