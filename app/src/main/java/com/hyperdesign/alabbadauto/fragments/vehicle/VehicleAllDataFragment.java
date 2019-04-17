package com.hyperdesign.alabbadauto.fragments.vehicle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.BuyVehicle;
import com.hyperdesign.alabbadauto.activities.SignInAct;
import com.hyperdesign.alabbadauto.adapters.listViewAdapter.FeaturesAdapter;
import com.hyperdesign.alabbadauto.adapters.listViewAdapter.PricesAdapter;
import com.hyperdesign.alabbadauto.api.model.IncreaseAuctionResponse;
import com.hyperdesign.alabbadauto.api.model.VehiclesAllDataResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.VehiclesData;
import com.hyperdesign.alabbadauto.classes.auction.Auction;
import com.hyperdesign.alabbadauto.classes.options.Options;
import com.hyperdesign.alabbadauto.classes.price.Prices;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;
import com.hyperdesign.alabbadauto.fragments.registration.SignInFrag;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehiclesDataFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hyper Design Hussien on 2/7/2018.
 */

public class VehicleAllDataFragment extends Fragment
        implements View.OnClickListener {

    OnHeadlineSelectedListener mCallback;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Define View
    View view;

    private TextView mVehicleBrand, mVehicleModel, mVehicleYear, mVehicleStatus,
            tvStartAuction, tvEndAuction, tvIncAuction;

    private TextView tvDays, tvHours, tvMinutes, tvSeconds;

    private EditText etIncAuction;

    private Button btnAddAuction;

    int id;

    private VehiclesData vehiclesData = new VehiclesData();

    private ArrayList<Options> itemOptions = new ArrayList<>();

    private ArrayList<Prices> itemPrices;

    private ArrayList<Prices> putItems;

    private ArrayList<Options> itemFeatures;

    private Prices prices = new Prices();

    private Options options = new Options();

    private Auction auction = new Auction();

    double total;

    private ListView lvPrices, lvFeatures;

    PricesAdapter pricesAdapter;

    FeaturesAdapter featuresAdapter;

    TextView tv_countdown;

    Calendar start_calendar, end_calendar;

    String startDate, endDate;

    private double startBid, minBid, maxBid;

    private long days, hours, minutes, seconds;

    int stYear , stMonth , stDay;

    int enYear , enMonth , enDay;

    long milliSecond = 92 * 24 * 60 * 60 * 1000;

    UserLocalStore userLocalStore;

    private Button btnBuy, btnSignIn;

    private View viewOfAuction;

    private SessionManager session;
    private String sessionLan;

    public VehicleAllDataFragment() {
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

        start_calendar = Calendar.getInstance();
        end_calendar = Calendar.getInstance();

        Log.i("start_calendar", start_calendar + "");
        Log.i("end_calendar", end_calendar + "");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Initialize View of home fragment
        view = inflater.inflate(R.layout.fragment_all_vehicles_data, container, false);

        if (savedInstanceState != null) {
            // Restore last state
            //mTime = savedInstanceState.getString("time_key");
        } else {
            //mTime = "" + Calendar.getInstance().getTimeInMillis();
        }
        putItems = new ArrayList<>();
        itemPrices = new ArrayList<>();
        itemFeatures = new ArrayList<>();
        userLocalStore = new UserLocalStore(getContext());

        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        Log.d("id", bundle.getInt("id") + "");

        mCallback.onArticleSelected(1);

        //tv_countdown = (TextView) getActivity().findViewById(R.id.tvTimer);
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

        mVehicleBrand = (TextView) getActivity().findViewById(R.id.tv_brand_vehicle);

        mVehicleModel = (TextView) getActivity().findViewById(R.id.tv_model_vehicle);

        mVehicleYear = (TextView) getActivity().findViewById(R.id.tv_year_vehicle);

        mVehicleStatus = (TextView) getActivity().findViewById(R.id.tv_status_vehicle);

        //tv_countdown = (TextView) getActivity().findViewById(R.id.tvTimer);

        viewOfAuction = (View) getActivity().findViewById(R.id.viewOfBids);

        btnBuy = (Button) getActivity().findViewById(R.id.btnBuyVehicle);
        btnBuy.setOnClickListener(this);

        btnSignIn = (Button) getActivity().findViewById(R.id.btnSignInV);
        btnSignIn.setOnClickListener(this);

        lvPrices = getActivity().findViewById(R.id.lv_prices);

        lvFeatures = getActivity().findViewById(R.id.lv_features);

        //setDownCountTimer();

        getIncAuction();

        setData();

        getAllVehiclesData();

    }

    private void setData(){

        /*mVehicleATPrice.setText("SSSSS");

        mVehicleMAPrice.setText("SSSSS");

        mVehicleATLPrice.setText("SSSSS");*/

        /*mVehicleMotorType.setText("SSSSS");

        mVehicleMotorCapacity.setText("SSSSS");

        mVehiclePattern.setText("SSSSS");

        mVehicleNumOfCylinder.setText("SSSSS");

        mVehicleEnginePower.setText("SSSSS");

        mVehicleSpeed.setText("SSSSS");

        mVehicleMaxSpeed.setText("SSSSS");

        mVehicleTankConsumption.setText("SSSSS");

        mVehicleTankCapacity.setText("SSSSS");*/
    }

    private void setVehiclesDescription(){

        if(sessionLan.equals("en")){
            mVehicleBrand.setText(vehiclesData.getBrands().getBrandNameEng());

            mVehicleModel.setText(vehiclesData.getModel().getNameModelEn());

            mVehicleStatus.setText(vehiclesData.getStatusEn());

            mVehicleYear.setText(vehiclesData.getYear().getYear());
        } else {
            mVehicleBrand.setText(vehiclesData.getBrands().getBrandNameArab());

            mVehicleModel.setText(vehiclesData.getModel().getNameModelAr());

            mVehicleStatus.setText(vehiclesData.getStatusEn());

            mVehicleYear.setText(vehiclesData.getYear().getYear());
        }


    }

    /*private void setDownCountTimer(){

        tvDays = (TextView) getActivity().findViewById(R.id.tvDaysNum);

        tvHours = (TextView) getActivity().findViewById(R.id.tvHoursNum);

        tvMinutes = (TextView) getActivity().findViewById(R.id.tvMinutesNum);

        tvSeconds = (TextView) getActivity().findViewById(R.id.tvSecondsNum);

    }*/

    private void getIncAuction(){
        tvIncAuction = (TextView) getActivity().findViewById(R.id.tvIncAuction);

        tvStartAuction = (TextView) getActivity().findViewById(R.id.tvStartAuction);

        tvEndAuction = (TextView) getActivity().findViewById(R.id.tvEndAuction);

        etIncAuction = (EditText) getActivity().findViewById(R.id.etIncAuction);

        btnAddAuction = (Button) getActivity().findViewById(R.id.btnAddAuction);

        btnAddAuction.setOnClickListener(this);
    }

    private void setPrices(){

        int i = 0;

        while ( i < itemPrices.size()){

            prices = new Prices(itemPrices.get(i).getId(),
                    itemPrices.get(i).getNameEn(), itemPrices.get(i).getPrice());

            /*prices.setId(itemPrices.get(i).getId());
            prices.setNameEn(itemPrices.get(i).getNameEn());
            prices.setPrice(itemPrices.get(i).getPrice());*/

            //putItems.add(prices);

        }

        pricesAdapter = new PricesAdapter(getActivity().getApplicationContext(), putItems);
        lvPrices.setAdapter(pricesAdapter);

        /*mVehicleATPrice.setText(putItems.get(0).getPrice() + "");

        mVehicleMAPrice.setText("SSSSS");

        mVehicleATLPrice.setText("SSSSS");*/
    }

    private void setFeatures(){

        int i = 0;

        while ( i < itemOptions.size()){

            options = new Options(itemOptions.get(i).getId(),
                    itemOptions.get(i).getValueEn(), itemOptions.get(i).getCatId(), itemOptions.get(i).getNameEn());

            itemFeatures.add(options);

        }

        pricesAdapter = new PricesAdapter(getActivity().getApplicationContext(), putItems);
        lvPrices.setAdapter(pricesAdapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //TODO GET All Vehicles Data
    private void getAllVehiclesData(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        System.out.println(id + "big Cheese");

        Call<VehiclesAllDataResponse> call = apiService.getAllVehiclesData(id);

        call.enqueue(new Callback<VehiclesAllDataResponse>() {

            @Override
            public void onResponse(Call<VehiclesAllDataResponse> call, Response<VehiclesAllDataResponse> response) {

                vehiclesData = response.body().getAllVDataResponseList();

                setVehiclesDescription();

                if(userLocalStore.getUserLoggedIn()) {
                    if (vehiclesData.getPriceType().equals("buy")) {
                        btnBuy.setVisibility(View.VISIBLE);
                    } else {
                        viewOfAuction.setVisibility(View.VISIBLE);
                        auction = response.body().getAuctionList();

                        total = response.body().getTotalAmount();

                        getAuctionData();
                    }
                } else {
                    goToSignIn();
                }

                itemOptions = response.body().getOptionsList();

                featuresAdapter = new FeaturesAdapter(getActivity().getBaseContext(), itemOptions);
                lvFeatures.setAdapter(featuresAdapter);

                itemPrices = response.body().getPricesList();

                pricesAdapter = new PricesAdapter(getActivity().getBaseContext(), itemPrices);
                lvPrices.setAdapter(pricesAdapter);

                //setPrices();

                //setFeatures();

                Log.i("A", "Number of movies images: " + vehiclesData.getPriceType());
                Log.i("A", "Number of movies images: " + vehiclesData.getIdVehicleData());
                Log.i("A", "Number of movies images: " + itemOptions.get(0).getValueEn());
                Log.i("A", "Number of movies images: " + itemPrices.get(0).getPrice());
                Log.i("A", "Number of movies images: " + auction.getStartDate());
                Log.i("A", "Number of movies images: " + total);

            }

            @Override
            public void onFailure(Call<VehiclesAllDataResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("m", t.toString());
                Log.e("M", "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToSignIn(){
        btnSignIn.setVisibility(View.VISIBLE);
    }

    private void getAuctionData(){

        startDate = auction.getStartDate();
        endDate = auction.getEndData();
        //getStartTimes(startDate, endDate);
        //setTimer();
        //getEndTimes(endDate);
        startBid = auction.getStartBid();
        minBid =  auction.getMinBid();
        maxBid = auction.getMaxBid();
        tvIncAuction.setText("Auction: " + total + "");
        tvStartAuction.setText("Min: " + minBid + "");
        tvEndAuction.setText("Max: " + maxBid + "");

        //getDayTimes();
    }

    private void setTimer(){

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        //long total_millis = (end_millis - start_millis); //total time in milliseconds
        long total_millis = milliSecond;

        end_calendar.set(enYear, enMonth, enDay); // 10 = November, month start at 0 = January

        //1000 = 1 second interval
        CountDownTimer cdt = new CountDownTimer(1000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                tvDays.setText(days + "");
                tvHours.setText(hours + "");
                tvMinutes.setText(minutes + "");
                tvSeconds.setText(seconds + "");

                //tv_countdown.setText(days + ":" + hours + ":" + minutes + ":" + seconds); //You can compute the millisUntilFinished on hours/minutes/seconds

            }

            @Override
            public void onFinish() {
                //tv_countdown.setText("Finish!");
            }
        };
        cdt.start();
    }

    private void getStartTimes(String startDate, String endDate){
        //String year = "", month, day;
        String sYear = "", sMonth = "", sDay = "";
        String eYear = "", eMonth = "", eDay = "";
        char[] sCharArray = startDate.toCharArray();
        char[] eCharArray = endDate.toCharArray();
        int years, months, days;
        long yearDays, monthDays;

        for(int i = 0; i < sCharArray.length; i++){
            if( i < 4 ){
                sYear += sCharArray[i];
            } else if(i > 4 && i <= 6){
                sMonth += sCharArray[i];
            } else if(i > 7 && i <= 9){
                sDay += sCharArray[i];
            }
        }

        stYear = Integer.parseInt(sYear);
        stMonth = Integer.parseInt(sMonth);
        stDay = Integer.parseInt(sDay);
        Log.d("year", stYear + stMonth + stDay + "" );

        for(int i = 0; i < eCharArray.length; i++){
            if( i < 4 ){
                eYear += eCharArray[i];
            } else if(i > 4 && i <= 6){
                eMonth += eCharArray[i];
            } else if(i > 7 && i <= 9){
                eDay += eCharArray[i];
            }
        }
        enYear = Integer.parseInt(eYear);
        enMonth = Integer.parseInt(eMonth);
        enDay = Integer.parseInt(eDay);
        Log.d("years", enYear + enMonth + enDay + "" );

        years = stYear - enYear;

        yearDays = years * 365;

        months = stMonth - enMonth;

        Log.d("stMonth", stMonth + "" + enMonth );
        monthDays  = getDaysInStMonth(stYear, enYear, stMonth, enMonth);

        Log.d("month", monthDays + "");



        /*for (char ch : startDate.toCharArray()){
            if(ch == '-'){
            }
            year += ch;
        }*/
        //Log.e("year", year);
    }

    private long getDaysInStMonth(int stYear, int enYear, int stMonth, int enMonth){

        int number_Of_DaysInMonth = 0;
        long number_Of_DaysInMonths = 0;
        String MonthOfName = "Unknown";

        /*System.out.print("Input a month number: ");
        //int month = input.nextInt();

        System.out.print("Input a year: ");
        int year = input.nextInt();*/

        for (int x = stYear; x <= enYear; x++){

            for(int y = stMonth; y <= enMonth; y++){

                switch (y) {
                    case 1:
                        MonthOfName = "January";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 2:
                        MonthOfName = "February";
                        if ((stYear % 400 == 0) || ((stYear % 4 == 0) && (stYear % 100 != 0))) {
                            number_Of_DaysInMonth = 29;
                            number_Of_DaysInMonths += number_Of_DaysInMonth;
                        } else {
                            number_Of_DaysInMonth = 28;
                            number_Of_DaysInMonths += number_Of_DaysInMonth;
                        }
                        break;
                    case 3:
                        MonthOfName = "March";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 4:
                        MonthOfName = "April";
                        number_Of_DaysInMonth = 30;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 5:
                        MonthOfName = "May";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 6:
                        MonthOfName = "June";
                        number_Of_DaysInMonth = 30;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 7:
                        MonthOfName = "July";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 8:
                        MonthOfName = "August";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 9:
                        MonthOfName = "September";
                        number_Of_DaysInMonth = 30;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 10:
                        MonthOfName = "October";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 11:
                        MonthOfName = "November";
                        number_Of_DaysInMonth = 30;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                        break;
                    case 12:
                        MonthOfName = "December";
                        number_Of_DaysInMonth = 31;
                        number_Of_DaysInMonths += number_Of_DaysInMonth;
                }
                System.out.print(MonthOfName + " " + stYear + " has " + number_Of_DaysInMonth + " days\n" + number_Of_DaysInMonths);

            }
        }

        return number_Of_DaysInMonths;

    }

    private void countdownTimer(){

        //for(int i = 0; )
    }

    private void getDaysInEnMonth(int enYear, int enMonth){

        int number_Of_DaysInMonth = 0;
        int number_Of_DaysInMonths = 0;
        String MonthOfName = "Unknown";

        switch (enMonth) {
            case 1:
                MonthOfName = "January";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 2:
                MonthOfName = "February";
                if ((enYear % 400 == 0) || ((enYear % 4 == 0) && (enYear % 100 != 0))) {
                    number_Of_DaysInMonth = 29;
                    number_Of_DaysInMonths += number_Of_DaysInMonth;
                } else {
                    number_Of_DaysInMonth = 28;
                    number_Of_DaysInMonths += number_Of_DaysInMonth;
                }
                break;
            case 3:
                MonthOfName = "March";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 4:
                MonthOfName = "April";
                number_Of_DaysInMonth = 30;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 5:
                MonthOfName = "May";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 6:
                MonthOfName = "June";
                number_Of_DaysInMonth = 30;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 7:
                MonthOfName = "July";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 8:
                MonthOfName = "August";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 9:
                MonthOfName = "September";
                number_Of_DaysInMonth = 30;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 10:
                MonthOfName = "October";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 11:
                MonthOfName = "November";
                number_Of_DaysInMonth = 30;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
                break;
            case 12:
                MonthOfName = "December";
                number_Of_DaysInMonth = 31;
                number_Of_DaysInMonths += number_Of_DaysInMonth;
        }
        System.out.print(MonthOfName + " " + enYear + " has " + number_Of_DaysInMonth + " days\n" + number_Of_DaysInMonths);
    }

    //TODO Increase Auction
    private void increaseAuction(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final String[] totalAuction = {""};
        final String[] message = { "" };

        System.out.println(id + "big Cheese" + vehiclesData.getIdVehicleData() + userLocalStore.getLoggedInUser().getId());

        Call<IncreaseAuctionResponse> call = apiService.increaseAuction(vehiclesData.getIdVehicleData(),
                userLocalStore.getLoggedInUser().getId(), etIncAuction.getText().toString());

        call.enqueue(new Callback<IncreaseAuctionResponse>() {

            @Override
            public void onResponse(Call<IncreaseAuctionResponse> call, Response<IncreaseAuctionResponse> response) {

                totalAuction[0] = response.body().getTotalAuction();

                message[0] = response.body().getMessage();

                Log.d("totalAuction", totalAuction[0] + " " + message[0]);

                tvIncAuction.setText("Auction: " + totalAuction[0]);

            }

            @Override
            public void onFailure(Call<IncreaseAuctionResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("m", t.toString());
                Log.e("M", "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        switch (i){
            case R.id.btnAddAuction:
                double auctionInc = Double.parseDouble(etIncAuction.getText().toString());
                Log.d("auction", minBid + "min" + maxBid);
                if(minBid > auctionInc){
                    etIncAuction.setError(getString(R.string.error_less_than) + minBid);
                } else if(auctionInc > maxBid){
                    etIncAuction.setError(getString(R.string.error_greater_than) + maxBid);
                } else {
                    increaseAuction();
                }
                break;
            case R.id.btnBuyVehicle:
                Intent intent = new Intent(this.getActivity(), BuyVehicle.class);
                intent.putExtra("itemId", vehiclesData.getIdVehicleData());
                startActivity(intent);
            case R.id.btnSignInV:
                intent = new Intent(this.getActivity(), SignInAct.class);
                startActivity(intent);
        }
    }




}
