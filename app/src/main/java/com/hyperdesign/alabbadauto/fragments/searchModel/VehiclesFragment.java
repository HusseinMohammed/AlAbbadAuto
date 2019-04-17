package com.hyperdesign.alabbadauto.fragments.searchModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.VehiclesAdapter;
import com.hyperdesign.alabbadauto.api.model.VehiclesImagesResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Vehicles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by Hyper Design Hussien on 2/3/2018.
 */

public class VehiclesFragment extends Fragment {

    // COMPLETED (34) Add a private ArrayList<Vehicles> variable called mVehiclesData
    private ArrayList<Vehicles> mVehiclesData;

    // COMPLETED (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // COMPLETED (35) Add a private ForecastAdapter variable called mForecastAdapter
    private VehiclesAdapter mVehiclesAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;
    //Define View
    View view;

    ArrayAdapter<String> adapter;

    private ArrayList<String> vehicleString;

    private Map<String, Integer> vehicleMap;

    ArrayList<Vehicles> vehiclesList;

    Vehicles vehicles;

    int idVehicle;

    public VehiclesFragment(){

    }



    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_vehicles, container, false);
        mVehiclesData = new ArrayList<>();
        vehiclesList = new ArrayList<>();
        vehicleString = new ArrayList<>();
        vehicleMap = new HashMap<>();
        vehicles = new Vehicles();

        /*Bundle bundle = getArguments();
        idVehicle = bundle.getBundle("bundle").getInt("idVehicle");
        Log.d("idVehicle", idVehicle + "");*/

        return view; //super.onCreateView(inflater, container, savedInstanceState)
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // COMPLETED (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_vehicles);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) getActivity().findViewById(R.id.tv_error_message_display);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) getActivity().findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data. */
        getVehicles();
    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadVehiclesData() {

        //showWeatherDataView();
        // COMPLETED (38) Create layoutManager, a GridLayoutManager with HORIZONTAL orientation and shouldReverseLayout == false
        /*GridLayoutManager layoutManager
                = new GridLayoutManager(this.getActivity(), GridLayoutManager.HORIZONTAL);*/

        // COMPLETED (41) Set the layoutManager on mRecyclerView
        //mRecyclerView.setLayoutManager(layoutManager);

        // COMPLETED (42) Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        // COMPLETED (43) set mVehiclesAdapter equal to a new VehiclesAdapter
        //mVehiclesAdapter = new VehiclesAdapter();

        // COMPLETED (44) Use mRecyclerView.setAdapter and pass in mVehiclesAdapter
        //mRecyclerView.setAdapter(mVehiclesAdapter);

        Vehicles vehicles = new Vehicles("","Plans","Plans");
        Vehicles vehicles1 = new Vehicles("","Cars","Cars");
        Vehicles vehicles2 = new Vehicles("","Trucks","Trucks");
        Vehicles vehicles3 = new Vehicles("","MotorBikes","MotorBikes");
        /*mVehiclesData.add(vehicles);
        mVehiclesData.add(vehicles1);
        mVehiclesData.add(vehicles2);
        mVehiclesData.add(vehicles3);*/

        System.out.println("Size " + mVehiclesData.size());

        int numberOfColumns = 3;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), numberOfColumns));

        mVehiclesAdapter = new VehiclesAdapter(mVehiclesData, getContext());
        mRecyclerView.setAdapter(mVehiclesAdapter);
        mVehiclesAdapter.notifyDataSetChanged();
        //String location = SunshinePreferences.getPreferredWeatherLocation(this);
        //new FetchWeatherTask().execute(location);
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // COMPLETED (44) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // COMPLETED (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    public interface OnFragmentInteractionListener {
    }

    //TODO First Function to call Api(Get Images & Categories)
    public void getVehicles(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<VehiclesImagesResponse> call = apiService.getVehiclesImages();

        call.enqueue(new Callback<VehiclesImagesResponse>() {
            @Override
            public void onResponse(Call<VehiclesImagesResponse> call, Response<VehiclesImagesResponse> response) {
                vehiclesList = response.body().getVehiclesList();
                //imagesList = response.body().getImagesList();
                Log.d(TAG, "Number of movies received vehicles: " + vehiclesList.get(0).getNameVehicleEng() + vehiclesList.size());
                //Log.d(TAG, "Number of movies images: " + imagesList.get(0).getFirstImage());
                /*images = new Images(imagesList.get(0).getFirstImage(), imagesList.get(0).getSecondImage()
                        , imagesList.get(0).getThirdImage(), imagesList.get(0).getLang());*/

                /*addImagesUrlOnline();

                loopUrlImages();*/

                getVehiclesData();

                loadVehiclesData();

            }

            @Override
            public void onFailure(Call<VehiclesImagesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }

        });

    }

    //TODO Fifth Function to get json array category from Api(Get Images & Categories)
    public void getVehiclesData(){

        for(int i = 0; i < vehiclesList.size(); i++){
            vehicles = new Vehicles(vehiclesList.get(i).getVehicleId(), vehiclesList.get(i).getNameVehicleEng(),
                    vehiclesList.get(i).getNameVehicleArab(), vehiclesList.get(i).getImageUrlVehicles());

            vehicleMap.put(vehiclesList.get(i).getNameVehicleEng(), vehiclesList.get(i).getVehicleId());

            vehicleString.add(vehicles.getNameVehicleEng());

            mVehiclesData.add(vehicles);
            System.out.println(mVehiclesData);
            System.out.println(vehicleString);
        }

        /*adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, vehicleString);
        spinnerCategory.setDropDownWidth(970);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);
        customOnItemSelected("category");*/

    }

}
