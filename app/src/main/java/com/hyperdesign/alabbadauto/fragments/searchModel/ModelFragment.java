package com.hyperdesign.alabbadauto.fragments.searchModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.ModelAdapter;
import com.hyperdesign.alabbadauto.adapters.VehiclesDataAdapter;
import com.hyperdesign.alabbadauto.api.model.ModelsResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Model;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * Created by Hyper Design Hussien on 2/4/2018.
 */

public class ModelFragment extends Fragment{

    private RecyclerView.LayoutManager layoutManager;

    // COMPLETED (34) Add a private ArrayList<Vehicles> variable called mModelData
    private ArrayList<Model> mModelData;

    // COMPLETED (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;

    // COMPLETED (35) Add a private ForecastAdapter variable called mModelAdapter
    private ModelAdapter mModelAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    //Define View
    View view;

    ArrayList<String> modelsString;

    private Map<String, Integer> modelMap;

    ArrayList<Model> modelList;

    Model model;

    int idBrand;

    private SessionManager session;
    private String sessionLan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_model, container, false);
        mModelData = new ArrayList<>();

        modelList = new ArrayList<>();
        modelsString = new ArrayList<>();
        modelMap = new HashMap<>();
        model = new Model();

        Bundle bundle = getArguments();
        idBrand =  bundle.getInt("idBrand");
        Log.d("bbb", idBrand + "");

        return view; //super.onCreateView(inflater, container, savedInstanceState)
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Session manager
        session = new SessionManager(getContext());

        sessionLan = session.isLanguageIn();

        // COMPLETED (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_model);

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
        //loadBrandsData();

        getModels();

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

        /*Model model = new Model(1,"","Porsche C3");
        Model model1 = new Model(2,"","Porsche C3");
        Model model2 = new Model(3,"","Porsche C3");
        Model model3 = new Model(4,"","Porsche C3");
        mModelData.add(model);
        mModelData.add(model1);
        mModelData.add(model2);
        mModelData.add(model3);*/

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        //System.out.println(jsonParser.getDoctorContactsArrayList().get(0).getDoctorName());
        System.out.println("Size " + mModelData.size());
        mModelAdapter = new ModelAdapter(mModelData, getContext());
        mRecyclerView.setAdapter(mModelAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        /*int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), numberOfColumns));

        mModelAdapter = new ModelAdapter(mModelData, this);
        mRecyclerView.setAdapter(mModelAdapter);*/
        mModelAdapter.notifyDataSetChanged();
        //String location = SunshinePreferences.getPreferredWeatherLocation(this);
        //new FetchWeatherTask().execute(location);
    }

    //TODO Third Function to call Api(Post Models About ID of Brands)
    private void getModels(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ModelsResponse> call = apiService.getModels(idBrand);

        call.enqueue(new Callback<ModelsResponse>() {
            @Override
            public void onResponse(Call<ModelsResponse> call, Response<ModelsResponse> response) {
                //brandsList = new ArrayList<>();
                modelList = response.body().getModelsList();
                if(modelList.size() > 1){
                    Log.d(TAG, "Number of movies received: " + modelList.get(0).getNameModelEn() + modelList.size());
                } else {
                    Log.d(TAG, "Number of movies received: " + modelList.size());
                }

                getModelsData();

                loadBrandsData();
            }

            @Override
            public void onFailure(Call<ModelsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                Log.e(TAG, "error :(");
            }
        });
    }

    //TODO Seventh Function to get json array models from Api(Post Models About ID of Brands)
    private void getModelsData(){
        for(int i=0; i < modelList.size(); i++){
            model = new Model(modelList.get(i).getId(), modelList.get(i).getNameModelEn(), modelList.get(i).getNameModelAr());

            modelMap.put(modelList.get(i).getNameModelAr(), modelList.get(i).getId());

            modelsString.add(model.getNameModelEn());
            mModelData.add(model);
            System.out.println(modelsString);
        }

        /*adapterModels = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, modelsString);
        spinnerModel.setDropDownWidth(970);
        spinnerModel.setAdapter(adapterModels);
        spinnerModel.setOnItemSelectedListener(this);
        customOnItemSelected("model");*/
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

}
