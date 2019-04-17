package com.hyperdesign.alabbadauto.fragments.insurance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.InsuranceCompaniesAdapter;
import com.hyperdesign.alabbadauto.api.model.InsuranceCompaniesResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.InsuranceCompanies;
import com.hyperdesign.alabbadauto.classes.location.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Hyper Design Hussien on 2/11/2018.
 */

public class InsuranceCompaniesFragment extends Fragment {

    //Define View
    View view;

    // COMPLETED (34) Add a private ArrayList<Vehicles> variable called mModelData
    private ArrayList<InsuranceCompanies> mInsuranceCompaniesList;

    // COMPLETED (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // COMPLETED (35) Add a private ForecastAdapter variable called mModelAdapter
    private InsuranceCompaniesAdapter mInsuranceCompaniesAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> insuranceCompaniesString;

    private Map<String, Integer> insuranceCompaniesMap;

    //ArrayList<Showrooms> showroomsList;

    InsuranceCompanies insuranceCompanies;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_insurance_companies, container, false);
        mInsuranceCompaniesList = new ArrayList<>();
        insuranceCompaniesString = new ArrayList<>();
        insuranceCompaniesMap = new HashMap<>();
        insuranceCompanies = new InsuranceCompanies();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // COMPLETED (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_insurance_companies);

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
        //loadInsuranceData();

        getInsuranceCompanies();

    }

    private void loadInsuranceData() {
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

        /*InsuranceCompanies insuranceCompanies = new InsuranceCompanies("","Insurance Name", "Address", "Info...");
        InsuranceCompanies insuranceCompanies1 = new InsuranceCompanies("","Insurance Name", "Address", "Info...");
        InsuranceCompanies insuranceCompanies2 = new InsuranceCompanies("","Insurance Name", "Address","Info...");
        InsuranceCompanies insuranceCompanies3 = new InsuranceCompanies("","Insurance Name", "Address", "Info...");

        mInsuranceCompaniesData.add(insuranceCompanies);
        mInsuranceCompaniesData.add(insuranceCompanies1);
        mInsuranceCompaniesData.add(insuranceCompanies2);
        mInsuranceCompaniesData.add(insuranceCompanies3);
        mInsuranceCompaniesData.add(insuranceCompanies3);
        mInsuranceCompaniesData.add(insuranceCompanies3);*/

        recyclerMain();

    }

    private void recyclerMain(){
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //System.out.println(jsonParser.getDoctorContactsArrayList().get(0).getDoctorName());
        mInsuranceCompaniesAdapter = new InsuranceCompaniesAdapter(mInsuranceCompaniesList, getContext());
        mRecyclerView.setAdapter(mInsuranceCompaniesAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mInsuranceCompaniesAdapter.notifyDataSetChanged();
    }

    //TODO Showrooms Function to call Api(Get Showrooms)
    private void getInsuranceCompanies(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<InsuranceCompaniesResponse> call = apiService.getInsuranceCompanies();
        call.enqueue(new Callback<InsuranceCompaniesResponse>() {
            @Override
            public void onResponse(Call<InsuranceCompaniesResponse> call, Response<InsuranceCompaniesResponse> response) {
                mInsuranceCompaniesList = response.body().getInsuranceCompaniesList();

                Log.d(TAG, "Number of movies received: " + mInsuranceCompaniesList.get(0).getTextNameEn() + mInsuranceCompaniesList.size());
                Log.d(TAG, "Number of movies received: " + mInsuranceCompaniesList.get(0).getCountry().getCountryNameEn() + mInsuranceCompaniesList.size());

                getInsuranceCompaniesData();

                loadInsuranceData();

            }

            @Override
            public void onFailure(Call<InsuranceCompaniesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });

    }

    //TODO Showrooms Function to get json array category from Api(Get Showrooms)
    private void getInsuranceCompaniesData(){

        for(int i = 0; i < mInsuranceCompaniesList.size(); i++){

            insuranceCompanies = new InsuranceCompanies(mInsuranceCompaniesList.get(i).getId(), mInsuranceCompaniesList.get(i).getImageUrlLogo(),
                    mInsuranceCompaniesList.get(i).getTextNameEn(), mInsuranceCompaniesList.get(i).getTextAddressEn(),
                    mInsuranceCompaniesList.get(i).getInfoEn());

            /*insuranceCompanies = new InsuranceCompanies(mInsuranceCompaniesList.get(i).getId(), mInsuranceCompaniesList.get(i).getImageUrlLogo(),
                    mInsuranceCompaniesList.get(i).getTextNameEn(), mInsuranceCompaniesList.get(i).getTextAddressEn(),
                    mInsuranceCompaniesList.get(i).getInfoEn(), mInsuranceCompaniesList.get(i).getCountry().getCountryNameEn(),
                    mInsuranceCompaniesList.get(i).getRegion().getRegionNameEn(), mInsuranceCompaniesList.get(i).getArea().getAreaNameEn(),
                    mInsuranceCompaniesList.get(i).getWorkTimes(), mInsuranceCompaniesList.get(i).getLatitude(),
                    mInsuranceCompaniesList.get(i).getLongitude());*/

            insuranceCompaniesMap.put(mInsuranceCompaniesList.get(i).getTextNameEn(), mInsuranceCompaniesList.get(i).getId());

            insuranceCompaniesString.add(insuranceCompanies.getTextNameEn());

            //mInsuranceCompaniesList.add(insuranceCompanies);
            //System.out.println(insuranceCompaniesString);

        }

    }

}
