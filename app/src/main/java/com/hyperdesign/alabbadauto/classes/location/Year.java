package com.hyperdesign.alabbadauto.classes.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/27/2018.
 */

public class Year {

    @SerializedName("id")
    private String id;

    @SerializedName("year")
    private String year;

    public Year() {
    }

    public Year(String id, String year) {
        this.id = id;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
