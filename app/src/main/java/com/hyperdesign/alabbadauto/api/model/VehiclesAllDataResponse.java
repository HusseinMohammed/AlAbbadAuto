package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.vehicleImages.Images;
import com.hyperdesign.alabbadauto.classes.VehiclesData;
import com.hyperdesign.alabbadauto.classes.auction.Auction;
import com.hyperdesign.alabbadauto.classes.options.Options;
import com.hyperdesign.alabbadauto.classes.price.Prices;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 3/26/2018.
 */

public class VehiclesAllDataResponse {

    //First Json Object
    @SerializedName("item")
    private VehiclesData allVDataResponseList;

    //First Json Array
    @SerializedName("itemoptions")
    private ArrayList<Options> optionsList;

    //Second Json Array
    @SerializedName("itemimages")
    private ArrayList<Images> imagesList;

    //Third Json Array
    @SerializedName("itemprice")
    private ArrayList<Prices> pricesList;

    //Second Json Object
    @SerializedName("itembids")
    private Auction auctionList;

    //First Double
    @SerializedName("totalamount")
    private Double totalAmount;

    public VehiclesData getVehiclesDataResponseList() {
        return allVDataResponseList;
    }

    public void setVehiclesDataResponseList(VehiclesData vehiclesDataResponseList) {
        this.allVDataResponseList = vehiclesDataResponseList;
    }

    public ArrayList<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public VehiclesData getAllVDataResponseList() {
        return allVDataResponseList;
    }

    public void setAllVDataResponseList(VehiclesData allVDataResponseList) {
        this.allVDataResponseList = allVDataResponseList;
    }

    public ArrayList<Prices> getPricesList() {
        return pricesList;
    }

    public void setPricesList(ArrayList<Prices> pricesList) {
        this.pricesList = pricesList;
    }

    public Auction getAuctionList() {
        return auctionList;
    }

    public void setAuctionList(Auction auctionList) {
        this.auctionList = auctionList;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ArrayList<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(ArrayList<Options> optionsList) {
        this.optionsList = optionsList;
    }

}
