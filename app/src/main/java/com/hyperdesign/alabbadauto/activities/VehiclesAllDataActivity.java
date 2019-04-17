package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.listViewAdapter.PricesAdapter;
import com.hyperdesign.alabbadauto.api.model.VehiclesAllDataResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.price.Prices;
import com.hyperdesign.alabbadauto.classes.vehicleImages.Images;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehicleAllDataFragment;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehiclesDataFragment;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_ITEM;

public class VehiclesAllDataActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
            , VehicleAllDataFragment.OnFragmentInteractionListener
            , VehicleAllDataFragment.OnHeadlineSelectedListener
            , VehiclesDataFragment.OnHeadlineSelectedListener
            , VehiclesDataFragment.OnFragmentInteractionListener {

    //Define cursor Toolbar
    Toolbar toolbar;

    //Define Slider of Images Vehicle
    SliderLayout sliderLayoutImages;

    //Define HashMap string urls of images
    HashMap<String, String> hashMapForURL;

    //Define TextSliderView of Images Vehicle
    TextSliderView textSliderView;

    //Define fragment
    Fragment fragment;

    FloatingActionButton floatingActionButton;

    private ArrayList<Images> imagesList = new ArrayList<>();

    private Images images = new Images();

    private Prices price;

    private ArrayList<Prices> prices;

    private PricesAdapter pricesAdapter;

    private ListView lvPrices;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_vehicles_data);

        //Initialize toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize Slider of Images Last Added Recently
        sliderLayoutImages = (SliderLayout) findViewById(R.id.slider_vehicle_images);

        //Call this method if you want to add images from URL .
        addImagesUrlOnline();

        //floatingActionButton = (FloatingActionButton) findViewById(R.id.float_action_button);
        /*floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivity(intent);*//*
            }
        });*/

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        fragment = new VehicleAllDataFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);

        price = new Prices();
        prices = new ArrayList<>();
        lvPrices = findViewById(R.id.lv_prices);

        //Call Function to Loop in urls of images about string names
        //loopUrlImages();
        getImages();

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        /*fragment = new BrandsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, VehiclesDataActivity.class);
        intent.putExtra("frag", "search");
        startActivity(intent);
        /*VehiclesDataFragment vehiclesFragment = new VehiclesDataFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.your_placeholder, vehiclesFragment)
                .addToBackStack(null)
                .commit();*/
    }

    //Create and Define Function to add image name and url in HashMap
    public void addImagesUrlOnline(){
        //Initialize HashMap string urls of images
        hashMapForURL = new HashMap<String, String>();

        int i = 0;

        while (i < imagesList.size()){

            images = new Images(imagesList.get(i).getId(), imagesList.get(i).getImageUrl());

            System.out.println("images" + images.getId() + images.getImageUrl());

            hashMapForURL.put(images.getId() + "", BASE_URL_API + BASE_URL_IMAGE_ITEM + images.getImageUrl());

            i++;
        }


        /*
        hashMapForURL.put("Golf", "https://cdn.24.co.za/files/Cms/General/d/209/a03177b6b3364e2693efcb22b0673350.jpg");
        hashMapForURL.put("Golf", "http://im.rediff.com/money/2015/sep/24kwid1.jpg");
        hashMapForURL.put("Golf", "http://www.thetruthaboutcars.com/wp-content/uploads/2017/07/2017-Kia-Niro.jpg");
        hashMapForURL.put("Golf", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4xgsTo9TwnW3IGxBsrZG_fHSNpkl7iteg66oljru9sgxN6iwn");
        */
    }

    //Create and Define Function to Loop in urls of images about string names
    public void loopUrlImages(){

        for(String urlName : hashMapForURL.keySet()){

            //Initialize TextSliderView of Images Last Added Recently
            textSliderView = new TextSliderView(this);

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

    // Define the behavior for onBackVehicleData
    public void onHeadlineSelectedListener(int position)
    {
        Toast.makeText(this, "Position clicked" + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        sliderLayoutImages.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //push toast message when user click of image from slider
        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onArticleSelected(int position) {

        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        //        VehicleAllDataFragment vehicleAllDataFragment = (VehicleAllDataFragment)
        //                getSupportFragmentManager().findFragmentById(R.id.fragment_all_data);
        //
        //        if (vehicleAllDataFragment != null) {
        //            // If article frag is available, we're in two-pane layout...
        //
        //            // Call a method in the ArticleFragment to update its content
        //            // vehicleAllDataFragment.updateArticleView(position);
        //        } else {
        //            // Otherwise, we're in the one-pane layout and must swap frags...
        //
        //            // Create fragment and give it an argument for the selected article
        //            VehicleAllDataFragment newFragment = new VehicleAllDataFragment();
        //            Bundle args = new Bundle();
        //            //args.putInt(VehicleAllDataFragment.ARG_POSITION, position);
        //            newFragment.setArguments(args);
        //
        //            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //
        //            // Replace whatever is in the fragment_container view with this fragment,
        //            // and add the transaction to the back stack so the user can navigate back
        //            transaction.replace(R.id.your_placeholder, newFragment);
        //            transaction.addToBackStack(null);
        //
        //            // Commit the transaction
        //            transaction.commit();
        //        }
    }

    //TODO Define A Function to get Data from Api
    private void getImages(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        System.out.println(id + "big Cheese");

        Call<VehiclesAllDataResponse> call = apiService.getAllVehiclesData(id);

        call.enqueue(new Callback<VehiclesAllDataResponse>() {
            @Override
            public void onResponse(Call<VehiclesAllDataResponse> call, Response<VehiclesAllDataResponse> response) {

                imagesList = response.body().getImagesList();

                Log.i("A", "Number of movies images: " + imagesList.get(0).getImageUrl());

                addImagesUrlOnline();

                loopUrlImages();

                //prices = response.body().getPricesList();
                Log.d("prices", prices.size() + " size");

                //pricesAdapter = new PricesAdapter(getApplicationContext(), prices);
                //lvPrices.setAdapter(pricesAdapter);


            }

            @Override
            public void onFailure(Call<VehiclesAllDataResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("m", t.toString());
                Log.e("M", "error");
                Toast.makeText(getBaseContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }



}
