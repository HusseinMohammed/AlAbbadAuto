package com.hyperdesign.alabbadauto.utilities;

import android.util.Log;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.api.model.VehiclesImagesResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Images;
import com.hyperdesign.alabbadauto.classes.Vehicles;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by Hyper Design Hussien on 3/14/2018.
 */

public class Apis {

    public ArrayList<Images> imagesList;

    private ArrayList<String> vehicleString;

    private Map<String, Integer> vehicleMap;

    ArrayList<Vehicles> vehiclesList;

    Vehicles vehicles;

    //TODO First Function to call Api(Get Images & Categories)
    /*private void getVehicles(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<VehiclesImagesResponse> call = apiService.getVehiclesImages();
        call.enqueue(new Callback<VehiclesImagesResponse>() {
            @Override
            public void onResponse(Call<VehiclesImagesResponse> call, Response<VehiclesImagesResponse> response) {
                vehiclesList = response.body().getVehiclesList();
                imagesList = response.body().getImagesList();
                Log.d(TAG, "Number of movies received: " + vehiclesList.get(0).getNameVehicleEng() + vehiclesList.size());
                Log.d(TAG, "Number of movies images: " + imagesList.get(0).getFirstImage());
                images = new Images(imagesList.get(0).getFirstImage(), imagesList.get(0).getSecondImage()
                        , imagesList.get(0).getThirdImage(), imagesList.get(0).getLang());

                addImagesUrlOnline();

                loopUrlImages();

                getVehiclesData();

            }

            @Override
            public void onFailure(Call<VehiclesImagesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }*/

}
