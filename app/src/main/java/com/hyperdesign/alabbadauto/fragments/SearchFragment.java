package com.hyperdesign.alabbadauto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
/*fr*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.VehiclesDataActivity;
import com.hyperdesign.alabbadauto.api.model.BrandsResponse;
import com.hyperdesign.alabbadauto.api.model.ModelsResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesImagesResponse;
import com.hyperdesign.alabbadauto.api.model.YearsResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Brands;
import com.hyperdesign.alabbadauto.classes.Images;
import com.hyperdesign.alabbadauto.classes.Model;
import com.hyperdesign.alabbadauto.classes.Vehicles;
import com.hyperdesign.alabbadauto.classes.Years;
import com.hyperdesign.alabbadauto.fragments.searchModel.VehiclesFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;
import com.hyperdesign.alabbadauto.utilities.XmlClickable;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE;

/**
 * Created by Hyper Design Hussein on 1/28/2018.
 */

public class SearchFragment extends Fragment implements
        View.OnClickListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, XmlClickable,
        VehiclesFragment.OnFragmentInteractionListener,
        AdapterView.OnItemSelectedListener {

    private static final String TAG = SearchFragment.class.getSimpleName();

    //Define and Initialize array of string(COUNTRIES)
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Egypt", "Russia", "Brazil", "Japan", "China"
    };

    ArrayList<String> countries = new ArrayList<String>();

    //Define ArrayAdapter to handle Category, Brands, Model, Year and Status spinners
    ArrayAdapter<String>
            adapter, adapterBrands, adapterModels, adapterYears;

    //Define BetterSpinner of Category, Brands, Model, Year, Status
    MaterialBetterSpinner
            textViewCategory, textViewBrands,
            textViewModel, textViewYear, textViewStatus;

    //Define Slider of Images Last Added Recently
    SliderLayout sliderLayoutImages;

    //Define HashMap string urls of images
    HashMap<String, String> hashMapForURL;

    //Define TextSliderView of Images Last Added Recently
    TextSliderView textSliderView;

    //Define RadioGroup
    private RadioGroup radioGroup;
    //Define RadioButton
    private RadioButton sellRadioButton, buyRadioButton, rentRadioButton;
    //Define Search Button
    private Button searchButton;

    //Define View
    View view;

    Spinner spinner;
    //List of url images
    public ArrayList<Images> imagesList = new ArrayList<>();

    public Images images = new Images();

    private Spinner spinnerCategory, spinnerBrand, spinnerModel, spinnerYear;

    //Define RadioButton
    private RadioButton newRadioButton, usedRadioButton;

    //Begin with
    ArrayList<String> vehicleString, brandString, modelsString, yearsString;

    private Map<String, Integer> categoryMap, brandMap, modelMap, yearMap;

    ArrayList<Vehicles> vehiclesList;

    Vehicles vehicles;

    ArrayList<Brands> brandsList;

    Brands brands;

    ArrayList<Model> modelList;

    Model model;

    ArrayList<Years> yearsList;

    Years years;

    Queue<Fragment> classQueue = new LinkedList<>();

    SearchFragment searchFragment;

    EditText carName;

    private SessionManager session;
    private String sessionLan;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countries.add("Belgium");
        countries.add("France");
        countries.add("Italy");

        System.out.println("size of queue-" + classQueue.size());
        classQueue.add(searchFragment);
        System.out.println("size of queue-" + classQueue.size());

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_search, parent, false);
        view = inflater.inflate(R.layout.fragment_search, parent, false);
        vehiclesList = new ArrayList<>();
        vehicleString = new ArrayList<>();
        categoryMap = new HashMap<>();
        vehicles = new Vehicles();

        brandsList = new ArrayList<>();
        brandString = new ArrayList<>();
        brandMap = new HashMap<>();
        brands = new Brands();

        modelList = new ArrayList<>();
        modelsString = new ArrayList<>();
        modelMap = new HashMap<>();
        model = new Model();

        yearsList = new ArrayList<>();
        yearsString = new ArrayList<>();
        yearMap = new HashMap<>();
        years = new Years();
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        //Initialize one adapter ta handle all spinners(Exception More Than Adapter false to handle each spinner)
        //and Better Spinner Of category_spinner

        // Session manager
        session = new SessionManager(getContext());

        sessionLan = session.isLanguageIn();

        spinnerCategory = (Spinner) getActivity().findViewById(R.id.spinner_category_search);
        spinnerCategory.setDropDownWidth(1020);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);
        customOnItemSelected("category");

        spinnerBrand = (Spinner) getActivity().findViewById(R.id.spinner_brand_search);


        spinnerModel = (Spinner) getActivity().findViewById(R.id.spinner_model_search);
        spinnerModel.setDropDownWidth(1020);
        spinnerModel.setAdapter(adapter);
        spinnerModel.setOnItemSelectedListener(this);
        customOnItemSelected("model");

        spinnerYear = (Spinner) getActivity().findViewById(R.id.spinner_year_search);
        spinnerYear.setDropDownWidth(1020);
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(this);
        customOnItemSelected("year");

        /*textViewCategory = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.category_spinner_search);
        textViewCategory.setAdapter(adapter);*/

        /*textViewCategory = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.category_spinner_search);
        textViewCategory.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewBrands = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.brands_spinner);
        textViewBrands.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewModel = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.model_spinner);
        textViewModel.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewYear = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.year_spinner);
        textViewYear.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewStatus = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.status_spinner);
        textViewStatus.setAdapter(adapter);*/

        carName = (EditText) getActivity().findViewById(R.id.car_name);

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_type_search);

        /*sellRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_sell);
        buyRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_buy);
        rentRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_rent);*/
        newRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_new);
        usedRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_used);

        searchButton = (Button) getActivity().findViewById(R.id.btn_search_button);

        //Initialize Slider of Images Last Added Recently
        sliderLayoutImages = (SliderLayout) getActivity().findViewById(R.id.slider_last_added_recently);



        //Get Vehicles
        getVehicles();

        //Call this method if you want to add images from URL .
        //addImagesUrlOnline();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();

        //Call Function to Loop in urls of images about string names
        //loopUrlImages();

        //Call Function to handle click on radio button
        //onRadioClicked(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.radio_new:
                        Log.i(TAG, newRadioButton.getText().toString());
                        break;
                    case R.id.radio_used:
                        Log.i(TAG, usedRadioButton.getText().toString());
                        break;
                    default:
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Handle click of button search(What we do?)

                if(spinnerCategory.getSelectedItem().equals("All Categories")
                        || spinnerCategory.getSelectedItem().equals("جميع الفئات")){
                    if(sessionLan.equals("en")){
                        Toast.makeText(getContext(), getResources().getString(R.string.category_required), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.category_required), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent intent = new Intent(getActivity(), VehiclesDataActivity.class);
                    intent.putExtra("frag", "search");
                    intent.putExtra("cat", categoryMap.get(spinnerCategory.getSelectedItem()));
                    intent.putExtra("brand", brandMap.get(spinnerBrand.getSelectedItem()));
                    intent.putExtra("model", modelMap.get(spinnerModel.getSelectedItem()));
                    intent.putExtra("year", yearMap.get(spinnerYear.getSelectedItem()));
                    intent.putExtra("name", carName.getText().toString());
                    intent.putExtra("case", getRadioChecked());
                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        //switch of button click
        switch (view.getId()){
            case R.id.btn_search_button:

                /*if(spinnerCategory.getSelectedItem().equals("All Categories")
                        || spinnerCategory.getSelectedItem().equals("جميع الفئات")){
                    if(sessionLan.equals("en")){
                        Toast.makeText(getContext(), getResources().getString(R.string.category_required), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.category_required), Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Handle click of button search(What we do?)
                    Intent intent = new Intent(getActivity(), VehiclesDataActivity.class);
                    startActivity(intent);
                }*/
                break;
        }
    }

    @Override
    public void onStop() {
        sliderLayoutImages.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //push toast message when user click of image from slider
        Toast.makeText(this.getActivity(),slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // TODO Create and Define Function to add image name and url in HashMap
    public void addImagesUrlOnline(){

        //Initialize HashMap string urls of images
        hashMapForURL = new HashMap<String, String>();
        //imagesList.add(images);

        /*for(int j = 0; j < hashMapForURL.size(); j++){
            hashMapForURL.put("Golf", BASE_URL_API + BASE_URL_IMAGE + images.getFirstImage());
        }*/

        /*for(int i : hashMapForURL.size()){

        }*/

        Log.i(TAG, images.getFirstImage() + " " + images.getSecondImage() + " " + images.getThirdImage());
        hashMapForURL.put("Golf", BASE_URL_API + BASE_URL_IMAGE + images.getFirstImage());
        hashMapForURL.put("M", BASE_URL_API + BASE_URL_IMAGE + images.getSecondImage());
        hashMapForURL.put("C", BASE_URL_API + BASE_URL_IMAGE + images.getThirdImage());

    }

    // TODO and Define Function to Loop in urls of images about string names
    public void loopUrlImages(){

        for(String urlName : hashMapForURL.keySet()){

            //Initialize TextSliderView of Images Last Added Recently
            textSliderView = new TextSliderView(this.getActivity());

            //get urlName, url of Image of urlName from HashMap, make image to fit in scale type of image
            //and put setOnSliderClickListener of images
            textSliderView
                    .description(urlName)
                    .image(hashMapForURL.get(urlName))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //call bundle method to put image when swap between images
            textSliderView.bundle(new Bundle());

            //getBundle() of image put in bundle
            textSliderView.getBundle()
                    .putString("extra", urlName);

            //add textSliderView of image to layout slider
            sliderLayoutImages.addSlider(textSliderView);

            //setPresetTransformer to sliderLayout of Images
            sliderLayoutImages.setPresetTransformer(SliderLayout.Transformer.DepthPage);

            //setPresetIndicator to sliderLayout of Images
            sliderLayoutImages.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

            //setCustomAnimation to sliderLayout of Images
            sliderLayoutImages.setCustomAnimation(new DescriptionAnimation());

            //setDuration to sliderLayout of Images
            sliderLayoutImages.setDuration(3000);

            //add change listener to slider layout of images
            sliderLayoutImages.addOnPageChangeListener(this);

        }

    }

    @Override
    public void myClickMethod(View v) {
        switch(v.getId()){
            // Just like you were doing
            case R.id.btn_search_button:
                //Handle click of button search(What we do?)
                Intent intent = new Intent(getActivity(), VehiclesDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()){
            case R.id.category_spinner:
                String item = spinner.getSelectedItem().toString();

                Toast.makeText(this.getActivity(), item, Toast.LENGTH_LONG).show();

                if(adapterView.getSelectedItem().toString().equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            case R.id.model_spinner:
                item = adapterView.getSelectedItem().toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(adapterView.getSelectedItem().toString().equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            case R.id.brands_spinner:
                item = adapterView.getSelectedItem().toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(adapterView.getSelectedItem().toString().equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            case R.id.year_spinner:
                item = adapterView.getSelectedItem().toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(adapterView.getSelectedItem().toString().equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //TODO First Function to call Api(Get Images & Categories)
    private void getVehicles(){
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
    }

    //TODO Second Function to call Api(Post Brands About ID of Category)
    private void getBrands(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


            Call<BrandsResponse> call = apiService.getBrands(categoryMap.get(spinnerCategory.getSelectedItem()));

            call.enqueue(new Callback<BrandsResponse>() {
                @Override
                public void onResponse(Call<BrandsResponse> call, Response<BrandsResponse> response) {
                    //brandsList = new ArrayList<>();
                    brandsList = response.body().getBrandsList();
                    if(brandsList.size() == 0){

                    } else {
                        Log.d(TAG, "Number of movies received: " + brandsList.get(0).getBrandNameEng() + brandsList.size());

                        getBrandsData();
                    }

                }

                @Override
                public void onFailure(Call<BrandsResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "error");
                }

            });

    }

    //TODO Third Function to call Api(Post Models About ID of Brands)
    private void getModels(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


            Call<ModelsResponse> call = apiService.getModels(brandMap.get(spinnerBrand.getSelectedItem()));

            call.enqueue(new Callback<ModelsResponse>() {
                @Override
                public void onResponse(Call<ModelsResponse> call, Response<ModelsResponse> response) {
                    //brandsList = new ArrayList<>();
                    modelList = response.body().getModelsList();
                    if(modelList.size() == 0){

                    } else {
                        getModelsData();
                    }
                    /*if (modelList.size() > 1) {
                        Log.d(TAG, "Number of movies received: " + modelList.get(0).getNameModelEn() + modelList.size());
                    } else {
                        Log.d(TAG, "Number of movies received: " + modelList.size());
                    }*/

                }

                @Override
                public void onFailure(Call<ModelsResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "error :(");
                }
            });

    }

    //TODO Fourth Function to call Api(Post Years About ID of Model)
    private void getYears(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


            Call<YearsResponse> call = apiService.getYears(modelMap.get(spinnerModel.getSelectedItem()));

            call.enqueue(new Callback<YearsResponse>() {
                @Override
                public void onResponse(Call<YearsResponse> call, Response<YearsResponse> response) {
                    //brandsList = new ArrayList<>();
                    yearsList = response.body().getYearsList();
                    /*if (yearsList.size() > 1) {
                        Log.d(TAG, "Number of movies received: " + yearsList.get(0).getYear() + yearsList.size());
                    } else {
                        Log.d(TAG, "Number of movies received: " + yearsList.size());
                    }*/
                    if(yearsList.size() == 0){

                    } else {
                        getYearsData();
                    }
                }

                @Override
                public void onFailure(Call<YearsResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "error :(");
                }
            });

    }

    ///////////////////////////////////////////////////

    //TODO Fifth Function to get json array category from Api(Get Images & Categories)
    private void getVehiclesData(){

        if(sessionLan.equals("en")){

        }
        categoryMap.put(getResources().getString(R.string.allCategories), 0);
        vehicleString.add(getResources().getString(R.string.allCategories));

            for (int i = 0; i < vehiclesList.size(); i++) {

                if(sessionLan.equals("en")){
                    vehicles = new Vehicles(vehiclesList.get(i).getVehicleId(), vehiclesList.get(i).getNameVehicleEng(), vehiclesList.get(i).getNameVehicleArab(), vehiclesList.get(i).getImageUrlVehicles());

                    categoryMap.put(vehiclesList.get(i).getNameVehicleEng(), vehiclesList.get(i).getVehicleId());

                    vehicleString.add(vehicles.getNameVehicleEng());
                } else {
                    vehicles = new Vehicles(vehiclesList.get(i).getVehicleId(), vehiclesList.get(i).getNameVehicleEng(), vehiclesList.get(i).getNameVehicleArab(), vehiclesList.get(i).getImageUrlVehicles());

                    categoryMap.put(vehiclesList.get(i).getNameVehicleArab(), vehiclesList.get(i).getVehicleId());

                    vehicleString.add(vehicles.getNameVehicleArab());
                }

                System.out.println(vehicleString);

            }

        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, vehicleString);
        spinnerCategory.setDropDownWidth(1020);
        adapter.notifyDataSetChanged();
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);
        //spinnerCategory.setSelection(adapter.getCount());
        customOnItemSelected("category");

    }

    //TODO Sixth Function to get json array brands from Api(Post Brands About ID of Category)
    private void getBrandsData(){

        brandMap.put(getResources().getString(R.string.allBrands), 0);
        brandString.add(getResources().getString(R.string.allBrands));


            for (int i = 0; i < brandsList.size(); i++) {


                if(sessionLan.equals("en")){
                    brands = new Brands(brandsList.get(i).getId(), brandsList.get(i).getBrandNameEng(),
                            brandsList.get(i).getBrandNameArab(), brandsList.get(i).getImageUrlBrands());

                    brandMap.put(brandsList.get(i).getBrandNameEng(), brandsList.get(i).getId());

                    brandString.add(brands.getBrandNameEng());
                } else {
                    brands = new Brands(brandsList.get(i).getId(), brandsList.get(i).getBrandNameEng(),
                            brandsList.get(i).getBrandNameArab(), brandsList.get(i).getImageUrlBrands());

                    brandMap.put(brandsList.get(i).getBrandNameArab(), brandsList.get(i).getId());

                    brandString.add(brands.getBrandNameArab());
                }

                System.out.println(brandString);
            }

        adapterBrands = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, brandString);
        spinnerBrand.setDropDownWidth(1020);
        adapterBrands.notifyDataSetChanged();
        spinnerBrand.setAdapter(adapterBrands);
        spinnerBrand.setOnItemSelectedListener(this);
        customOnItemSelected("brand");


    }

    //TODO Seventh Function to get json array models from Api(Post Models About ID of Brands)
    private void getModelsData(){

        modelMap.put(getResources().getString(R.string.allModels), 0);
        modelsString.add(getResources().getString(R.string.allModels));

            for (int i = 0; i < modelList.size(); i++) {

                if(sessionLan.equals("en")){
                    model = new Model(modelList.get(i).getId(), modelList.get(i).getNameModelEn(), modelList.get(i).getNameModelAr());

                    modelMap.put(modelList.get(i).getNameModelEn(), modelList.get(i).getId());

                    modelsString.add(model.getNameModelEn());
                } else {
                    model = new Model(modelList.get(i).getId(), modelList.get(i).getNameModelEn(), modelList.get(i).getNameModelAr());

                    modelMap.put(modelList.get(i).getNameModelAr(), modelList.get(i).getId());

                    modelsString.add(model.getNameModelAr());
                }

                System.out.println(modelsString);
            }

        adapterModels = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, modelsString);
        spinnerModel.setDropDownWidth(1020);
        adapterModels.notifyDataSetChanged();
        spinnerModel.setAdapter(adapterModels);
        spinnerModel.setOnItemSelectedListener(this);
        customOnItemSelected("model");
    }

    //TODO Eighth Function to get json array years from Api(Post Years About ID of Models)
    private void getYearsData(){

        yearMap.put(getResources().getString(R.string.allYears), 0);
        yearsString.add(getResources().getString(R.string.allYears));


            for (int i = 0; i < yearsList.size(); i++) {

                if(sessionLan.equals("en")){

                    years = new Years(yearsList.get(i).getId(), yearsList.get(i).getYear());

                    yearMap.put(yearsList.get(i).getYear(), yearsList.get(i).getId());

                    yearsString.add(years.getYear());
                } else {

                    years = new Years(yearsList.get(i).getId(), yearsList.get(i).getYear());

                    yearMap.put(yearsList.get(i).getYear(), yearsList.get(i).getId());

                    yearsString.add(years.getYear());
                }
                System.out.println(yearsString);
            }

        adapterYears = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, yearsString);
        spinnerYear.setDropDownWidth(1020);
        adapterYears.notifyDataSetChanged();
        spinnerYear.setAdapter(adapterYears);
        spinnerYear.setOnItemSelectedListener(this);
        customOnItemSelected("year");

    }

    private void setAllBrands(){
        brandString.add(getResources().getString(R.string.allBrands));

        adapterBrands = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, brandString);
        spinnerBrand.setDropDownWidth(1020);
        adapterBrands.notifyDataSetChanged();
        spinnerBrand.setAdapter(adapterBrands);
        spinnerBrand.setOnItemSelectedListener(this);
        customOnItemSelected("brand");
    }

    private void setAllModels(){

    }

    private void setAllYears(){

    }

    //TODO Function to customize on item selected on spinners("category", "brand", "model", "year") dynamic
    private void customOnItemSelected(String spinner){
        switch (spinner){
            case "category":
                categoryOnItemSelected();
                break;
            case "brand":
                brandOnItemSelected();
                break;
            case "model":
                modelOnItemSelected();
                break;
            case "year":
                yearOnItemSelected();
                break;
            default:
        }
    }

    private void categoryOnItemSelected(){

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                /*if(spinnerCategory.getSelectedItem() == "All Categories") {

                }
                else {
                    Toast.makeText(getContext(), spinnerCategory.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }*/
                //Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                getBrands();
                if (brandString.size() > 1) {
                    //spinnerBrand.setAdapter(null);  // Fatal Error Exception Make Spinner Not Visible and Visible to 2 Second
                    brandString.clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void brandOnItemSelected(){

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                getModels();

                if (modelsString.size() > 1) {
                    //spinnerBrand.setAdapter(null);  // Fatal Error Exception Make Spinner Not Visible and Visible to 2 Second
                    modelsString.clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void modelOnItemSelected(){

        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                //getModels(); loop on spinners years
                getYears();

                if (yearsString.size() > 1) {
                    //spinnerBrand.setAdapter(null);  // Fatal Error Exception Make Spinner Not Visible and Visible to 2 Second
                    yearsString.clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void yearOnItemSelected(){

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private String getRadioChecked(){
        String radioChecked = null;
        if(radioGroup.getCheckedRadioButtonId() == R.id.radio_new){
            if(sessionLan.equals("ar")){
                radioChecked = "new";
            } else {
                radioChecked = newRadioButton.getText().toString();
            }
            return radioChecked;
        } else {
            if(sessionLan.equals("ar")){
                radioChecked = "used";
            } else {
                radioChecked = usedRadioButton.getText().toString();
            }
            return radioChecked;
        }
    }

    public int getCount() {

        // TODO Auto-generated method stub
        int count = getCount();

        return count > 0 ? count-1 : count ;


    }

}

/*
public void onRadioClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_sell:
                break;
            case R.id.radio_buy:

                break;
            case R.id.radio_rent:

                break;
            default:

        }
    }*/

    /*public void SearchButtonClick(View view){
        switch (view.getId()){

        }
}
*/


//textViewCategory.setOnItemSelectedListener(this);

        /*textViewCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(item.equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*spinner = (Spinner) getActivity().findViewById(R.id.spinner);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(item.equals("Belgium")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/