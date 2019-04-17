package com.hyperdesign.alabbadauto.fragments.searchModel;

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
import com.hyperdesign.alabbadauto.adapters.BrandsAdapter;
import com.hyperdesign.alabbadauto.api.model.BrandsResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Brands;
import com.hyperdesign.alabbadauto.fragments.addVehicle.AddVehicleFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;

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

public class BrandsFragment extends Fragment{

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // COMPLETED (34) Add a private ArrayList<Vehicles> variable called mBrandsData
    private ArrayList<Brands> mBrandsData;

    // COMPLETED (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // COMPLETED (35) Add a private ForecastAdapter variable called mBrandsAdapter
    private BrandsAdapter mBrandsAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    //Define View
    View view;

    //Update 3/16 6:16

    ArrayAdapter<String> adapterBrands;

    ArrayList<String> brandString;

    private Map<String, Integer> brandMap;

    ArrayList<Brands> brandsList;

    Brands brands;

    int idVehicle;

    private SessionManager session;
    private String sessionLan;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public BrandsFragment(){
    }

    public static AddVehicleFragment newInstance(String param1, String param2) {
        AddVehicleFragment fragment = new AddVehicleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        } else {
            Log.d("hamdaGanzabile", "nullll" + "");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_brands, container, false);
        mBrandsData = new ArrayList<>();
        brandsList = new ArrayList<>();
        brandString = new ArrayList<>();
        brandMap = new HashMap<>();
        brands = new Brands();

        Bundle bundle = getArguments();
        idVehicle =  bundle.getInt("idVehicle");
        Log.d("lll", idVehicle + "");


        return view; //super.onCreateView(inflater, container, savedInstanceState)
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Session manager
        session = new SessionManager(getContext());

        sessionLan = session.isLanguageIn();

        // COMPLETED (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_brands);

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
        //        loadBrandsData();
        getBrands();

    }

    private void loadBrandsData() {
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

        /*Brands Brands = new Brands(0,"","Porsche");
        Brands Brands1 = new Brands(1,"","Porsche");
        Brands Brands2 = new Brands(2,"","Porsche");
        Brands Brands3 = new Brands(3,"","Porsche");
        mBrandsData.add(Brands);
        mBrandsData.add(Brands1);
        mBrandsData.add(Brands2);
        mBrandsData.add(Brands3);*/
        System.out.println("Size " + mBrandsData.size());

        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), numberOfColumns));

        mBrandsAdapter = new BrandsAdapter(mBrandsData, getContext());
        mRecyclerView.setAdapter(mBrandsAdapter);
        mBrandsAdapter.notifyDataSetChanged();
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


    //TODO Second Function to call Api(Post Brands About ID of Category)
    private void getBrands(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Log.e("id", idVehicle + "");

        Call<BrandsResponse> call = apiService.getBrands(idVehicle);

        call.enqueue(new Callback<BrandsResponse>() {

            @Override
            public void onResponse(Call<BrandsResponse> call, Response<BrandsResponse> response) {
                //brandsList = new ArrayList<>();
                brandsList = response.body().getBrandsList();
                Log.d(TAG, "Number of movies received: " + brandsList.get(0).getBrandNameEng() + brandsList.size());

                getBrandsData();

                loadBrandsData();
            }

            @Override
            public void onFailure(Call<BrandsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                Log.e(TAG, "error");
            }
        });
    }

    //TODO Sixth Function to get json array brands from Api(Post Brands About ID of Category)
    private void getBrandsData(){

        for(int i=0; i < brandsList.size(); i++){
            brands = new Brands(brandsList.get(i).getId(), brandsList.get(i).getBrandNameEng(),
                    brandsList.get(i).getBrandNameArab(), brandsList.get(i).getImageUrlBrands());

            brandMap.put(brandsList.get(i).getBrandNameEng(), brandsList.get(i).getId());

            brandString.add(brands.getBrandNameEng());
            mBrandsData.add(brands);
            System.out.println(brandString);
        }

        /*adapterBrands = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, brandString);
        spinnerBrand.setDropDownWidth(970);
        spinnerBrand.setAdapter(adapterBrands);
        spinnerBrand.setOnItemSelectedListener(this);
        customOnItemSelected("brand");*/
    }

    public interface OnFragmentInteractionListener {
    }
}
