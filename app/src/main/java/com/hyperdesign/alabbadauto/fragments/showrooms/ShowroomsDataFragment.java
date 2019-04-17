package com.hyperdesign.alabbadauto.fragments.showrooms;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.VehiclesDataAdapter;
import com.hyperdesign.alabbadauto.api.model.ShowDataResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Showrooms;
import com.hyperdesign.alabbadauto.classes.VehiclesData;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_SHOW_DATA;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowroomsDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowroomsDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowroomsDataFragment extends Fragment {


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

    private ImageView ivLogo;

    private TextView tvName, tvLocation, tvEmail, tvPhone, tvTimes, tvAbout, tvItems;

    String url, name, loc, email, phone, times, about;

    ArrayList<String> vehicleDataString;

    private Map<String, Integer> vehicleDataMap;

    ArrayList<VehiclesData> vehicleDataList;

    VehiclesData vehicleData;

    private ArrayList<String> showroomsString;

    private Map<String, Integer> showroomsMap;

    ArrayList<Showrooms> showroomsList;

    Showrooms showrooms;

    private SessionManager session;
    private String sessionLan;

    int id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ScrollView scrollView;

    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    public ShowroomsDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowroomsDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowroomsDataFragment newInstance(String param1, String param2) {
        ShowroomsDataFragment fragment = new ShowroomsDataFragment();
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
        vehicleDataList = new ArrayList<>();
        vehicleDataString = new ArrayList<>();
        vehicleDataMap = new HashMap<>();
        vehicleData = new VehiclesData();
        mVehiclesData = new ArrayList<>();
        Bundle bundle = getArguments();
        id = bundle.getInt("id");

        return inflater.inflate(R.layout.fragment_showrooms_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Session manager
        session = new SessionManager(getContext());
        sessionLan = session.isLanguageIn();

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_showroom_data);

        mErrorMessageDisplay = (TextView) getActivity().findViewById(R.id.tv_error_message_display);

        ivLogo = (ImageView) getActivity().findViewById(R.id.ivShowroom);

        tvName = (TextView) getActivity().findViewById(R.id.tvName);
        tvLocation = (TextView) getActivity().findViewById(R.id.tvLocation);
        tvEmail = (TextView) getActivity().findViewById(R.id.tvEmail);
        tvPhone = (TextView) getActivity().findViewById(R.id.tvPhone);
        tvTimes = (TextView) getActivity().findViewById(R.id.tvWorkTimes);
        tvAbout = (TextView) getActivity().findViewById(R.id.tvInfo);

        tvItems = (TextView) getActivity().findViewById(R.id.tvItems);

        scrollView = (ScrollView) getActivity().findViewById(R.id.scroll_view_brands);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar4);

        showProgress(true);

        getShowroomData();

    }

    private void loadShowroomData(){
        url = showrooms.getLogo();

        Glide.with(this).load(BASE_URL_API + BASE_URL_IMAGE_SHOW_DATA + url).into(ivLogo);

        if(sessionLan.equals("en")){
            tvName.setText(showrooms.getNameEng());
            tvLocation.setText(showrooms.getCountry().getCountryNameEn() + " - " + showrooms.getRegion().getRegionNameEn() + " - " +
                    showrooms.getArea().getAreaNameEn());
            tvEmail.setText(showrooms.getAddressEn());
            tvPhone.setText(showrooms.getPhone());
            tvTimes.setText(showrooms.getWorkTimes());
            tvAbout.setText(Html.fromHtml(showrooms.getAboutEn()));
        } else {
            tvName.setText(showrooms.getNameArab());
            tvLocation.setText(showrooms.getCountry().getCountryNameAr() + " - " + showrooms.getRegion().getRegionNameAr() + " - " +
                    showrooms.getArea().getAreaNameAr());
            tvEmail.setText(showrooms.getAddressAr());
            tvPhone.setText(showrooms.getPhone());
            tvTimes.setText(showrooms.getWorkTimes());
            tvAbout.setText(Html.fromHtml(showrooms.getAboutAr()));
        }

    }

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
        mVehiclesDataAdapter = new VehiclesDataAdapter(mVehiclesData, null, this, "showroom");
        mRecyclerView.setAdapter(mVehiclesDataAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

    }


    //TODO First Function to call Api(Get Images & Categories)
    private void getShowroomData(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ShowDataResponse> call = apiService.getShowroomData(id);
        call.enqueue(new Callback<ShowDataResponse>() {
            @Override
            public void onResponse(Call<ShowDataResponse> call, Response<ShowDataResponse> response) {

                showrooms = response.body().getShowroomData();
                vehicleDataList = response.body().getVehiclesDataList();

                Log.d(TAG, "Number of movies received: " + showrooms.getNameEng() );
                if(showrooms.equals(null)){
                    showProgress(false);
                } else {
                    showProgress(false);
                    getShowroomsData();

                    loadShowroomData();
                }

                if(vehicleDataList.size() == 0){
                    showProgress(false);
                    tvItems.setText(getResources().getString(R.string.noItems));
                } else {
                    showProgress(false);
                    Log.d(TAG, "Number of movies images: " + vehicleDataList.get(0).getBrands().getBrandNameEng());
                    getVehiclesLoopData();

                    loadVehiclesData();
                }

            }

            @Override
            public void onFailure(Call<ShowDataResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });

    }

    //TODO Showrooms Function to get json array category from Api(Get Showrooms)
    private void getShowroomsData(){

        if(sessionLan.equals("en")){
            showrooms = new Showrooms(showrooms.getId(), showrooms.getNameEng(), showrooms.getLogo(),
                    showrooms.getPhone(), showrooms.getAddressEn(), showrooms.getAboutEn(),
                    showrooms.getWorkTimes(), showrooms.getCountry(), showrooms.getRegion(),
                    showrooms.getArea(), showrooms.getLatitude(), showrooms.getLongitude());
        } else {
            showrooms = new Showrooms(showrooms.getNameArab(), showrooms.getLogo(),
                    showrooms.getPhone(), showrooms.getAddressAr(), showrooms.getAboutAr(),
                    showrooms.getWorkTimes(), showrooms.getCountry(), showrooms.getRegion(),
                    showrooms.getArea(), showrooms.getLatitude(), showrooms.getLongitude(), showrooms.getId());
        }


        //showroomsMap.put(showroomsList.get(i).getNameEng(), showroomsList.get(i).getId());

        //showroomsString.add(showrooms.getNameEng());
        //showroomsList.add(showrooms);
        //System.out.println(showroomsString);
    }

    //TODO Sixth Function to get json array brands from Api(Post Brands About ID of Category)
    private void getVehiclesLoopData() {

        for (int i = 0; i < vehicleDataList.size(); i++) {
            //vehicleData.vehiclesNameEn = vehicleDataList.get(i).getVehiclesNameEn();

            if(sessionLan.equals("en")){
                vehicleData = new VehiclesData(vehicleDataList.get(i).getIdVehicleData(), vehicleDataList.get(i).getVehiclesUrlImage(),
                        vehicleDataList.get(i).getVehiclesNameEn(),
                        vehicleDataList.get(i).getBrands().getBrandNameEng(), vehicleDataList.get(i).getModel().getNameModelEn(),
                        vehicleDataList.get(i).getShowroomsResponseList().getNameEng(), vehicleDataList.get(i).getVehiclesPrice());
            } else {
                vehicleData = new VehiclesData(vehicleDataList.get(i).getIdVehicleData(), vehicleDataList.get(i).getVehiclesUrlImage(),
                        vehicleDataList.get(i).getVehiclesNameAr(),
                        vehicleDataList.get(i).getBrands().getBrandNameArab(), vehicleDataList.get(i).getModel().getNameModelAr(),
                        vehicleDataList.get(i).getShowroomsResponseList().getNameArab(), vehicleDataList.get(i).getVehiclesPrice());
            }


            Log.d("brand", vehicleDataList.get(i).getBrands().getBrandNameEng());
            Log.d("brand", vehicleDataList.get(i).getModel().getNameModelEn());
            Log.d("brand", vehicleDataList.get(i).getModel().getNameModelEn());

            vehicleDataMap.put(vehicleDataList.get(i).getVehiclesBrandNameEn(), vehicleDataList.get(i).getIdVehicleData());

            vehicleDataString.add(vehicleData.getVehiclesBrandNameEn());
            mVehiclesData.add(vehicleData);
            System.out.println(vehicleDataString);

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            scrollView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            scrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
