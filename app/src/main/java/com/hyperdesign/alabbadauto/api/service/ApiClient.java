package com.hyperdesign.alabbadauto.api.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;

/**
 * Created by Hyper Design Hussien on 2/24/2018.
 */

public class ApiClient {
    public static final String BASE_URL = BASE_URL_API;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
