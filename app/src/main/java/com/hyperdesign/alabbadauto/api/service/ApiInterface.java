package com.hyperdesign.alabbadauto.api.service;

import com.hyperdesign.alabbadauto.api.model.BrandsResponse;
import com.hyperdesign.alabbadauto.api.model.BuyCarResponse;
import com.hyperdesign.alabbadauto.api.model.IncreaseAuctionResponse;
import com.hyperdesign.alabbadauto.api.model.InsuranceCompaniesResponse;
import com.hyperdesign.alabbadauto.api.model.InsuranceCompanyResponse;
import com.hyperdesign.alabbadauto.api.model.ModelsResponse;
import com.hyperdesign.alabbadauto.api.model.ShowDataResponse;
import com.hyperdesign.alabbadauto.api.model.ShowroomsResponse;
import com.hyperdesign.alabbadauto.api.model.SignInResponse;
import com.hyperdesign.alabbadauto.api.model.SignUpResponse;
import com.hyperdesign.alabbadauto.api.model.SubmitBuyCardResponse;
import com.hyperdesign.alabbadauto.api.model.TenderMessageResponse;
import com.hyperdesign.alabbadauto.api.model.VDataModelResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesAllDataResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesDataResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesImagesResponse;
import com.hyperdesign.alabbadauto.api.model.YearsResponse;
import com.hyperdesign.alabbadauto.api.model.sendMessage.SendInsMessage;
import com.hyperdesign.alabbadauto.api.model.sendMessage.SendShowMessage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by Hyper Design Hussien on 2/24/2018.
 */

public interface ApiInterface {

    @GET("/api")
    Call<VehiclesImagesResponse> getVehiclesImages();

    @FormUrlEncoded
    @POST("/api/getBrand")
    Call<BrandsResponse> getBrands(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/getModel")
    Call<ModelsResponse> getModels(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/getYear")
    Call<YearsResponse> getYears(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/search")
    Call<VehiclesDataResponse> getVehiclesData(@Field("cat") int cat, @Field("brand") int brand,
                                               @Field("model") int model, @Field("year") int year,
                                               @Field("name") String name, @Field("case") String status);

    @GET("/api/showRooms")
    Call<ShowroomsResponse> getShowrooms();

    @GET("/api/insurances")
    Call<InsuranceCompaniesResponse> getInsuranceCompanies();

    @FormUrlEncoded
    @POST("/api/showInsurance")
    Call<InsuranceCompanyResponse> getInsuranceData(@Field("id") int id);


    @FormUrlEncoded
    @POST("/api/showDealer")
    Call<ShowDataResponse> getShowroomData(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/showItem")
    Call<VehiclesAllDataResponse> getAllVehiclesData(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/showItembyModel")
    Call<VDataModelResponse> getVModelData(@Field("id") int id);

    @FormUrlEncoded
    @POST("/api/register")
    Call<SignUpResponse> getSignUpUser(@Field("username") String name, @Field("email") String email
            , @Field("password") String pass);

    @FormUrlEncoded
    @POST("/api/login")
    Call<SignInResponse> getSignInUser(@Field("email") String email
            , @Field("password") String pass);

    @FormUrlEncoded
    @POST("/api/postTender")
    Call<TenderMessageResponse> sendTenderMessage(@Field("id") int id
            , @Field("message") String message);

    @FormUrlEncoded
    @POST("/api/addoffer")
    Call<IncreaseAuctionResponse> increaseAuction(@Field("id") int itemId, @Field("user_id") int userId
            , @Field("value") String value);

    @FormUrlEncoded
    @POST("/api/Buycar")
    Call<BuyCarResponse> buyCar(@Field("id") int id
            , @Field("user_id") int userId);

    @FormUrlEncoded
    @POST("/api/postBuycar")
    Call<SubmitBuyCardResponse> buyCarSubmit(@Field("id") int id
            , @Field("user_id") int userId, @Field("price") int priceId
            , @Field("phone") String phone, @Field("email") String email
            , @Field("address_en") String addEn, @Field("address_ar") String addAr);


    @FormUrlEncoded
    @POST("/api/messageInsurance")
    Call<SendInsMessage> sendInsMessage(@Field("user_id") int userId, @Field("insurance_id") int insId,
                                        @Field("phone") String phone, @Field("message") String message);

    @FormUrlEncoded
    @POST("/api/messageInsurance")
    Call<SendShowMessage> sendShowMessage(@Field("user_id") int userId, @Field("showroom_id") int showId,
                                         @Field("phone") String phone, @Field("message") String message);



}
