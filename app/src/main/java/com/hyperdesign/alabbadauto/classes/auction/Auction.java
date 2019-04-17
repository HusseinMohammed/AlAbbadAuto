package com.hyperdesign.alabbadauto.classes.auction;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/26/2018.
 */

public class Auction {

    @SerializedName("id")
    private int id;

    @SerializedName("start_bid")
    private double startBid;

    @SerializedName("minimum_bid")
    private double minBid;

    @SerializedName("maximum_bid")
    private double maxBid;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endData;

    public Auction(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStartBid() {
        return startBid;
    }

    public void setStartBid(int startBid) {
        this.startBid = startBid;
    }

    public double getMinBid() {
        return minBid;
    }

    public void setMinBid(double minBid) {
        this.minBid = minBid;
    }

    public double getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(double maxBid) {
        this.maxBid = maxBid;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }
}
