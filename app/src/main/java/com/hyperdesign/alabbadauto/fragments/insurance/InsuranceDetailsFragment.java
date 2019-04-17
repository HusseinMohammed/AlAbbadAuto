package com.hyperdesign.alabbadauto.fragments.insurance;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.SendRequestActivity;
import com.hyperdesign.alabbadauto.api.model.InsuranceCompanyResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.InsuranceCompanies;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_INSH;

/**
 * Created by Hyper Design Hussien on 2/12/2018.
 */

public class InsuranceDetailsFragment extends Fragment
        implements View.OnClickListener,
        SendRequestFragment.OnFragmentInteractionListener {

    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    double latitude;
    double longitude;

    //Define toolbar
    Toolbar toolbar;
    //Initialize View
    View myView;
    //Define fragment
    Fragment fragment;

    InsuranceDetailsFragment mInsuranceDetailsFragment;

    private TextView mName, mAddress, mCountry, mCity
            , mRegion, mArea, mWorkTimes, mAboutMe;

    private TextView mSendRequest;

    private ImageView mMapLocation, mImageIcon;

    private ArrayList<String> insuranceCompaniesString;

    private Map<String, Integer> insuranceCompaniesMap;

    //ArrayList<Showrooms> showroomsList;

    InsuranceCompanies insuranceCompanies;

    int id;
    String country, region, area;
    private Context context = getActivity();


    private ProgressBar progressBar;

    private ConstraintLayout constraintLayout;

    private SessionManager session;
    private String sessionLan;

    public InsuranceDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Initialize View of home fragment
        myView = inflater.inflate(R.layout.fragment_insurance_detials, container, false);
        insuranceCompaniesString = new ArrayList<>();
        insuranceCompaniesMap = new HashMap<>();
        insuranceCompanies = new InsuranceCompanies();
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        Log.d("id", id + "");
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Session manager
        session = new SessionManager(getContext());

        sessionLan = session.isLanguageIn();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //get Latitude and Longitude of user location
        //getLocation();
        mImageIcon = (ImageView) getActivity().findViewById(R.id.insuranceLogo);

        mName = (TextView) getActivity().findViewById(R.id.insuranceName);

        mAddress = (TextView) getActivity().findViewById(R.id.insuranceAddress);

        mCountry = (TextView) getActivity().findViewById(R.id.country);

        mCity = (TextView) getActivity().findViewById(R.id.city);

        mRegion = (TextView) getActivity().findViewById(R.id.region);

        //mArea = (TextView) getActivity().findViewById(R.id.area);

        mWorkTimes = (TextView) getActivity().findViewById(R.id.workTimes);

        mAboutMe = (TextView) getActivity().findViewById(R.id.aboutMe);

        mSendRequest = (TextView) getActivity().findViewById(R.id.sendRequest);
        mSendRequest.setOnClickListener(this);

        mMapLocation = (ImageView) getActivity().findViewById(R.id.mapLocation);
        mMapLocation.setOnClickListener(this);

        constraintLayout = (ConstraintLayout) getActivity().findViewById(R.id.clInsuranceDetails);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar3);

        showProgress(true);
        getInsuranceCompany();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            constraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            constraintLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    constraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            constraintLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if( location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                //((EditText) getActivity().findViewById(R.id.etLat)).setText("Latitude: " + latitude);
                //((EditText) getActivity().findViewById(R.id.etLong)).setText("Latitude: " + longitude);
            } else {
                //((EditText) getActivity().findViewById(R.id.etLat)).setText("Unable to find correct location");
                //((EditText) getActivity().findViewById(R.id.etLat)).setText("Unable to find correct location");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int position = view.getId();
        switch (position){
            case R.id.mapLocation:
                //String geoUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + lng + " (" + mTitle + ")";
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intentLoc = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intentLoc);
                break;
            case R.id.sendRequest:
                //toolbar.setTitle(getResources().getString(R.string.title_search));
                /*fragment = new SendRequestFragment();
                loadFragment(fragment);*/
                Intent intent = new Intent(getActivity(), SendRequestActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                /*overridePendingTransition(R.anim.enter, R.anim.exit);*/
                break;
        }
    }


    private void loadFragment(Fragment fragment) {
        fragment = new InsuranceDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //TODO Showrooms Function to call Api(Get Showrooms)
    private void getInsuranceCompany(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<InsuranceCompanyResponse> call = apiService.getInsuranceData(id);
        call.enqueue(new Callback<InsuranceCompanyResponse>() {
            @Override
            public void onResponse(Call<InsuranceCompanyResponse> call, Response<InsuranceCompanyResponse> response) {

                insuranceCompanies = response.body().getInsuranceCompany();

                if(sessionLan.equals("en")){
                    country = insuranceCompanies.getCountry().getCountryNameEn();
                    region = insuranceCompanies.getRegion().getRegionNameEn();
                    area = insuranceCompanies.getArea().getAreaNameEn();
                } else {
                    country = insuranceCompanies.getCountry().getCountryNameAr();
                    region = insuranceCompanies.getRegion().getRegionNameAr();
                    area = insuranceCompanies.getArea().getAreaNameAr();
                }


                Log.d(TAG, "Number of movies received: " + insuranceCompanies.getCountry().getCountryNameEn());

                getInsuranceCompanyData();

                loadInsuranceData();

                showProgress(false);

            }

            @Override
            public void onFailure(Call<InsuranceCompanyResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadInsuranceData() {

        if(sessionLan.equals("en")){
            latitude = insuranceCompanies.getLatitude();
            longitude = insuranceCompanies.getLongitude();
            Glide.with(getContext()).load(BASE_URL_API + BASE_URL_IMAGE_INSH + insuranceCompanies.getImageUrlLogo()).into(mImageIcon);
            mName.setText(insuranceCompanies.getTextNameEn());
            mAddress.setText(insuranceCompanies.getTextAddressEn());
            //mCountry.setText(insuranceCompanies.getCountry().getCountryNameEn());
            //mCity.setText(insuranceCompanies.getTextNameEn());
            //mRegion.setText(insuranceCompanies.getRegion().getRegionNameEn());
            //mArea.setText(insuranceCompanies.getArea().getAreaNameEn());
            mWorkTimes.setText(insuranceCompanies.getWorkTimes());
            mCountry.setText(country);
            mCity.setText(region);
            mRegion.setText(area);
            mAboutMe.setText(Html.fromHtml(insuranceCompanies.getInfoEn()));
        } else {
            latitude = insuranceCompanies.getLatitude();
            longitude = insuranceCompanies.getLongitude();
            Glide.with(getContext()).load(BASE_URL_API + BASE_URL_IMAGE_INSH + insuranceCompanies.getImageUrlLogo()).into(mImageIcon);
            mName.setText(insuranceCompanies.getTextNameAr());
            mAddress.setText(insuranceCompanies.getTextAddressAr());
            //mCountry.setText(insuranceCompanies.getCountry().getCountryNameEn());
            //mCity.setText(insuranceCompanies.getTextNameEn());
            //mRegion.setText(insuranceCompanies.getRegion().getRegionNameEn());
            //mArea.setText(insuranceCompanies.getArea().getAreaNameEn());
            mWorkTimes.setText(insuranceCompanies.getWorkTimes());
            mCountry.setText(country);
            mCity.setText(region);
            mRegion.setText(area);
            mAboutMe.setText(Html.fromHtml(insuranceCompanies.getInfoAr()));
        }


    }

    //TODO Showrooms Function to get json array category from Api(Get Showrooms)
    private void getInsuranceCompanyData(){

        if(sessionLan.equals("en")){
            insuranceCompanies = new InsuranceCompanies(insuranceCompanies.getId(),insuranceCompanies.getImageUrlLogo(),
                    insuranceCompanies.getTextNameEn(), insuranceCompanies.getTextAddressEn(),
                    insuranceCompanies.getInfoEn(), insuranceCompanies.getCountry().getCountryNameEn(),
                    insuranceCompanies.getRegion().getRegionNameEn(), insuranceCompanies.getArea().getAreaNameEn(),
                    insuranceCompanies.getWorkTimes(), insuranceCompanies.getLatitude(),
                    insuranceCompanies.getLongitude());
        } else {
            insuranceCompanies = new InsuranceCompanies(insuranceCompanies.getImageUrlLogo(),
                    insuranceCompanies.getTextNameAr(), insuranceCompanies.getTextAddressAr(),
                    insuranceCompanies.getInfoAr(), insuranceCompanies.getCountry().getCountryNameAr(),
                    insuranceCompanies.getRegion().getRegionNameAr(), insuranceCompanies.getArea().getAreaNameAr(),
                    insuranceCompanies.getWorkTimes(), insuranceCompanies.getLatitude(),
                    insuranceCompanies.getLongitude(), insuranceCompanies.getId());
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
    }

}
