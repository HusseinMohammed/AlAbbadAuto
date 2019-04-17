package com.hyperdesign.alabbadauto.fragments.vehicle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.hyperdesign.alabbadauto.adapters.VehiclesDataAdapter;
import com.hyperdesign.alabbadauto.api.model.VDataModelResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesDataResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.VehiclesData;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehiclesDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehiclesDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehiclesDataFragment extends Fragment {

    OnHeadlineSelectedListener mCallback;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Start Of Fragment

    private RecyclerView.LayoutManager layoutManager;
    // COMPLETED (34) Add a private ArrayList<Vehicles> variable called mVehiclesData
    private ArrayList<VehiclesData> mVehiclesData;

    // COMPLETED (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // COMPLETED (35) Add a private ForecastAdapter variable called mForecastAdapter
    private VehiclesDataAdapter mVehiclesDataAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;
    //Define View
    View view;

    ArrayList<String> vehicleDataString;

    private Map<String, Integer> vehicleDataMap;

    ArrayList<VehiclesData> vehicleDataList;

    VehiclesData vehicleData;

    private String mTime;

    int cat, brand, model, year, modelId;
    String caseRadio, name, frag;

    private SessionManager session;
    private String sessionLan;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public VehiclesDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehiclesDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VehiclesDataFragment newInstance(String param1, String param2) {
        VehiclesDataFragment fragment = new VehiclesDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_vehicles_data, container, false);
        mVehiclesData = new ArrayList<>();

        if (savedInstanceState != null) {
            // Restore last state
            //mTime = savedInstanceState.getString("time_key");
        } else {
            //mTime = "" + Calendar.getInstance().getTimeInMillis();
        }

        vehicleDataList = new ArrayList<>();
        vehicleDataString = new ArrayList<>();
        vehicleDataMap = new HashMap<>();
        vehicleData = new VehiclesData();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        cat = preferences.getInt("cat",0);
        brand = preferences.getInt("brand",0);
        model = preferences.getInt("model",0);
        year = preferences.getInt("year",0);
        name = preferences.getString("name","");
        caseRadio = preferences.getString("case","new");
        modelId = preferences.getInt("modelId", 0);

        Bundle bundle = getArguments();
        frag = bundle.getString("frag");

        if(frag.equals("search")){
            cat = bundle.getInt("cat");
            brand = bundle.getInt("brand");
            model = bundle.getInt("model");
            year = bundle.getInt("year");
            caseRadio = bundle.getString("case");
            name = bundle.getString("name");
        } else{
            modelId = bundle.getInt("idModel");
        }

        Log.i("bundle", cat + " c"+ brand + " b"  + model + " m" + year + " y"+ caseRadio + " c" + name + " m" + modelId);

        return view; //super.onCreateView(inflater, container, savedInstanceState)
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("time_key", mTime);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Session manager
        session = new SessionManager(getContext());

        sessionLan = session.isLanguageIn();

        // COMPLETED (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_vehicles_data);

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
        if(frag.equals("search")){
            getVehiclesData();
        } else {
            getVDataByModelId();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

        /*VehiclesData vehiclesData = new VehiclesData(0,"","Mazada", "sport3", "Abaza",120000.0);
        VehiclesData vehiclesData1 = new VehiclesData(1,"","Mazada", "sport3", "Masrya",120000.0);
        VehiclesData vehiclesData2 = new VehiclesData(2,"","Mazada", "sport3","Showroom" ,120000.0);
        VehiclesData vehiclesData3 = new VehiclesData(3,"","Mazada", "sport3", "Showroom",120000.0);

        mVehiclesData.add(vehiclesData);
        mVehiclesData.add(vehiclesData1);
        mVehiclesData.add(vehiclesData2);
        mVehiclesData.add(vehiclesData3);*/

        recyclerMain();

        //getVehiclesData();
        mVehiclesDataAdapter.notifyDataSetChanged();

    }

    private void recyclerMain(){
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //System.out.println(jsonParser.getDoctorContactsArrayList().get(0).getDoctorName());
        System.out.println("Size " + mVehiclesData.size());
        mVehiclesDataAdapter = new VehiclesDataAdapter(mVehiclesData, this, null, "vehicle");
        mRecyclerView.setAdapter(mVehiclesDataAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    //TODO Second Function to call Api(Post Vehicles About IDs of Cat, Brand, Model, year, case and name)
    private void getVehiclesData(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<VehiclesDataResponse> call = apiService.getVehiclesData(cat,brand,model,year,name,caseRadio);

        call.enqueue(new Callback<VehiclesDataResponse>() {
            @Override
            public void onResponse(Call<VehiclesDataResponse> call, Response<VehiclesDataResponse> response) {
                //brandsList = new ArrayList<>();
                //Toast.makeText(getContext(), "success :)", Toast.LENGTH_LONG).show();
                vehicleDataList = response.body().getVehiclesDataResponseList();
                if(vehicleDataList.size() == 0){
                    mErrorMessageDisplay.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "Number of movies received: " + vehicleDataList.get(0).getVehiclesBrandNameEn() + vehicleDataList.size());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getShowroomsResponseList().getNameEng());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getBrands().getBrandNameEng());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getModel().getNameModelEn());

                    getVehiclesLoopData();

                    loadVehiclesData();

                }
            }

            @Override
            public void onFailure(Call<VehiclesDataResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                Log.e(TAG, "error");
            }
        });
    }

    //TODO Second Function to call Api(Post Vehicles About ID of Model)
    private void getVDataByModelId(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<VDataModelResponse> call = apiService.getVModelData(modelId);

        call.enqueue(new Callback<VDataModelResponse>() {
            @Override
            public void onResponse(Call<VDataModelResponse> call, Response<VDataModelResponse> response) {
                //brandsList = new ArrayList<>();
                Toast.makeText(getContext(), "success :)", Toast.LENGTH_LONG).show();
                vehicleDataList = response.body().getVehiclesDataModel();
                if(vehicleDataList.size() == 0){
                    mErrorMessageDisplay.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "Number of movies received: " + vehicleDataList.get(0).getVehiclesBrandNameEn() + vehicleDataList.size());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getShowroomsResponseList().getNameEng());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getBrands().getBrandNameEng());
                    Log.d(TAG, "Number of Showrooms: " + vehicleDataList.get(0).getModel().getNameModelEn());

                    getVehiclesLoopData();

                    loadVehiclesData();

                }
            }

            @Override
            public void onFailure(Call<VDataModelResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                Log.e(TAG, "error");
            }
        });
    }

    //TODO Sixth Function to get json array brands from Api(Post Vehicles About IDs of Cat, Brand, Model, year, case and name)
    private void getVehiclesLoopData(){

        for(int i=0; i < vehicleDataList.size(); i++){
            //vehicleData.vehiclesNameEn = vehicleDataList.get(i).getVehiclesNameEn();

            if(sessionLan.equals("en")){
                vehicleData = new VehiclesData(vehicleDataList.get(i).getIdVehicleData(), vehicleDataList.get(i).getVehiclesUrlImage(),
                        vehicleDataList.get(i).getVehiclesNameEn(),
                        vehicleDataList.get(i).getBrands().getBrandNameEng(), vehicleDataList.get(i).getModel().getNameModelEn(),
                        vehicleDataList.get(i).getShowroomsResponseList().getNameEng(), vehicleDataList.get(i).getVehiclesPrice());

                vehicleDataMap.put(vehicleDataList.get(i).getVehiclesBrandNameEn(), vehicleDataList.get(i).getIdVehicleData());

                vehicleDataString.add(vehicleData.getVehiclesBrandNameEn());
                mVehiclesData.add(vehicleData);
            } else {
                vehicleData = new VehiclesData(vehicleDataList.get(i).getIdVehicleData(), vehicleDataList.get(i).getVehiclesUrlImage(),
                        vehicleDataList.get(i).getVehiclesNameAr(),
                        vehicleDataList.get(i).getBrands().getBrandNameArab(), vehicleDataList.get(i).getModel().getNameModelAr(),
                        vehicleDataList.get(i).getShowroomsResponseList().getNameArab(), vehicleDataList.get(i).getVehiclesPrice());

                vehicleDataMap.put(vehicleDataList.get(i).getVehiclesBrandNameAr(), vehicleDataList.get(i).getIdVehicleData());

                vehicleDataString.add(vehicleData.getVehiclesBrandNameAr());
                mVehiclesData.add(vehicleData);
            }


            System.out.println(vehicleDataString);

        }

        /*for(char ch: vehicleData.getVehiclesNameEn().toCharArray()){
         */

        /*if(ch == ' '){
                }
            }*/

        //String brand = "", model = "";
        //String vehicleName = vehicleData.getVehiclesNameEn();
            /*String[] split = vehicleName.split("\\s+");
            System.out.println(split[0] + "split"+ split[1]);*/
            /*for (int y = 0; y < vehicleName.length(); y++){
                char c = vehicleName.charAt(y);
                brand += c;
                System.out.println(brand);
                if(c == ' '){
                    model += c;
                    break;
                    //model += c;
                    //System.out.println(model);
                }
                //Process char
            }
            System.out.println("b" + brand);
            System.out.println("m" + model);*/

        /*adapterBrands = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, brandString);
        spinnerBrand.setDropDownWidth(970);
        spinnerBrand.setAdapter(adapterBrands);
        spinnerBrand.setOnItemSelectedListener(this);
        customOnItemSelected("brand");*/
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }


}
