package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.location.Year;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hyper Design Hussien on 2/6/2018.
 */

public class VehiclesData {

    @SerializedName("id")
    public int idVehicleData;

    //Vehicles Card
    @SerializedName("image")
    public String vehiclesUrlImage;

    @SerializedName("name_en")
    public String vehiclesNameEn;

    @SerializedName("name_ar")
    public String vehiclesNameAr;

    public String vehiclesBrandNameEn;
    public String vehiclesBrandNameAr;

    public String vehiclesModelEn;
    public String vehiclesModelAr;

    public String vehiclesShowroomEn;
    public String vehiclesShowroomAr;

    @SerializedName("price")
    public Double vehiclesPrice;

    //Vehicles Complement Data
    public String[] vehiclesUrlOfImages;
    public String vehiclesYear;
    public HashMap<String, Double> vehiclesPrices;

    public String vehiclesStatus;
    public String vehiclesMotionVector;
    public String vehiclesDescription;

    public String vehiclesMotorName;
    public String vehiclesChairsNumber;
    public String vehiclesAirCondition;

    public String vehiclesEngineCapacityCC;
    public String vehiclesDoorNumbers;

    @SerializedName("dealer")
    private Showrooms showroomsResponseList;

    @SerializedName("brand")
    private Brands brands;

    @SerializedName("model")
    private Model model;

    @SerializedName("type")
    private String statusEn;

    @SerializedName("mod_year")
    private Year year;

    @SerializedName("price_type")
    private String priceType;

    public VehiclesData() {
    }

    public VehiclesData(int idVehicleData, String vehiclesUrlImage, String vehiclesNameEn, String vehiclesBrandNameEn,
                        String vehiclesModelEn ,String vehiclesShowroomEn, Double vehiclesPrice) {
        this.idVehicleData = idVehicleData;
        this.vehiclesUrlImage = vehiclesUrlImage;
        this.vehiclesNameEn = vehiclesNameEn;
        this.vehiclesBrandNameEn = vehiclesBrandNameEn;
        this.vehiclesModelEn = vehiclesModelEn;
        this.vehiclesShowroomEn = vehiclesShowroomEn;
        this.vehiclesPrice = vehiclesPrice;
    }

    public VehiclesData(String vehiclesUrlImage, String vehiclesNameAr, String vehiclesBrandNameAr, String vehiclesModelAr ,String vehiclesShowroomAr, Double vehiclesPrice, int idVehicleData) {
        this.vehiclesUrlImage = vehiclesUrlImage;
        this.vehiclesNameAr = vehiclesNameAr;
        this.vehiclesBrandNameAr = vehiclesBrandNameAr;
        this.vehiclesModelAr = vehiclesModelAr;
        this.vehiclesShowroomAr = vehiclesShowroomAr;
        this.vehiclesPrice = vehiclesPrice;
        this.idVehicleData = idVehicleData;
    }

    public VehiclesData(String vehiclesUrlImage, String vehiclesBrandName, String vehiclesModel, Double vehiclesPrice, String[] vehiclesUrlOfImages, String vehiclesYear, HashMap<String, Double> vehiclesPrices, String vehiclesStatus, String vehiclesMotionVector, String vehiclesDescription, String vehiclesMotorName, String vehiclesChairsNumber, String vehiclesAirCondition, String vehiclesEngineCapacityCC, String vehiclesDoorNumbers) {
        this.vehiclesUrlImage = vehiclesUrlImage;
        this.vehiclesBrandNameEn = vehiclesBrandName;
        this.vehiclesModelEn = vehiclesModel;
        this.vehiclesPrice = vehiclesPrice;
        this.vehiclesUrlOfImages = vehiclesUrlOfImages;
        this.vehiclesYear = vehiclesYear;
        this.vehiclesPrices = vehiclesPrices;
        this.vehiclesStatus = vehiclesStatus;
        this.vehiclesMotionVector = vehiclesMotionVector;
        this.vehiclesDescription = vehiclesDescription;
        this.vehiclesMotorName = vehiclesMotorName;
        this.vehiclesChairsNumber = vehiclesChairsNumber;
        this.vehiclesAirCondition = vehiclesAirCondition;
        this.vehiclesEngineCapacityCC = vehiclesEngineCapacityCC;
        this.vehiclesDoorNumbers = vehiclesDoorNumbers;
    }

    public void setVehiclesUrlOfImages(String[] vehiclesUrlOfImages) {
        this.vehiclesUrlOfImages = vehiclesUrlOfImages;
    }

    public int getIdVehicleData() {
        return idVehicleData;
    }

    public void setIdVehicleData(int idVehicleData) {
        this.idVehicleData = idVehicleData;
    }

    public String getVehiclesUrlImage() {
        return vehiclesUrlImage;
    }

    public String getVehiclesBrandNameEn() {
        return vehiclesBrandNameEn;
    }

    public String getVehiclesModelEn() {
        return vehiclesModelEn;
    }

    public String getVehiclesShowroomEn() {
        return vehiclesShowroomEn;
    }

    public Double getVehiclesPrice() {
        return vehiclesPrice;
    }

    public String[] getVehiclesUrlOfImages() {
        return vehiclesUrlOfImages;
    }

    public String getVehiclesYear() {
        return vehiclesYear;
    }

    public HashMap<String, Double> getVehiclesPrices() {
        return vehiclesPrices;
    }

    public String getVehiclesStatus() {
        return vehiclesStatus;
    }

    public String getVehiclesMotionVector() {
        return vehiclesMotionVector;
    }

    public String getVehiclesDescription() {
        return vehiclesDescription;
    }

    public String getVehiclesMotorName() {
        return vehiclesMotorName;
    }

    public String getVehiclesChairsNumber() {
        return vehiclesChairsNumber;
    }

    public String getVehiclesAirCondition() {
        return vehiclesAirCondition;
    }

    public String getVehiclesEngineCapacityCC() {
        return vehiclesEngineCapacityCC;
    }

    public String getVehiclesDoorNumbers() {
        return vehiclesDoorNumbers;
    }

    public Showrooms getShowroomsResponseList() {
        return showroomsResponseList;
    }

    public void setShowroomsResponseList(Showrooms showroomsResponseList) {
        this.showroomsResponseList = showroomsResponseList;
    }

    public String getVehiclesNameEn() {
        return vehiclesNameEn;
    }

    public String getVehiclesNameAr() {
        return vehiclesNameAr;
    }

    public String getVehiclesBrandNameAr() {
        return vehiclesBrandNameAr;
    }

    public Brands getBrands() {
        return brands;
    }

    public void setBrands(Brands brands) {
        this.brands = brands;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
