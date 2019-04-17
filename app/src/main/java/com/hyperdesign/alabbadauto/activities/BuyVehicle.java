package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.api.model.BuyCarResponse;
import com.hyperdesign.alabbadauto.api.model.SubmitBuyCardResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.price.Prices;
import com.hyperdesign.alabbadauto.classes.user.User;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyVehicle extends AppCompatActivity implements View.OnClickListener {

    //Define cursor Toolbar
    Toolbar toolbar;

    private EditText etName, etEmail, etPhone, etLoc;
    private Button btnBuy;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    UserLocalStore userLocalStore;

    Intent intent;
    int vehicleId;

    ArrayList<Prices> pricesList;
    User user;

    private ArrayList<String> nameEn, nameAr;
    private ArrayList<Double> pricesArr;

    private Prices prices;

    private int priceId;

    private HashMap<String,Integer> pricesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_vehicle);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_buy_car));

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        intent = getIntent();
        vehicleId = intent.getIntExtra("itemId", 0);

        pricesList = new ArrayList<>();
        user = new User();
        userLocalStore = new UserLocalStore(getBaseContext());
        prices = new Prices();
        pricesArr = new ArrayList<>();

        nameEn = new ArrayList<>();
        nameAr = new ArrayList<>();
        pricesId = new HashMap<>();

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etLoc = (EditText) findViewById(R.id.etLocation);

        radioButton = new RadioButton(this);
        radioGroup = findViewById(R.id.radioGroupPrices);


        btnBuy = (Button) findViewById(R.id.btnSubmitBuy);


        final int id = radioGroup.getCheckedRadioButtonId();

        Log.d("idPrice", id + "");

        buyCard();
        //createRadioButtons();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitBuyCard();
                intent = new Intent(getApplicationContext(), VehiclesAllDataActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }

    private void createRadioButtons(ArrayList<Double> vehiclePrices, ArrayList<String> nameEn) {
        radioGroup = findViewById(R.id.radioGroupPrices);
        //int[] vehiclePrices = getResources().getIntArray(R.array.vehiclePrices);


        //TODO Create Radio Buttons

        for(int i = 0; i < vehiclePrices.size(); i++){
            final double numPrice = vehiclePrices.get(i);
            final String name = nameEn.get(i);

            radioButton = new RadioButton(this);
            radioButton.setText(numPrice + " - " + name);

            //TODO Set on-click callbacks
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceId = pricesId.get(name);
                    Toast.makeText(getApplicationContext(), "You Clicked on" + numPrice + pricesId.get(name), Toast.LENGTH_LONG).show();
                }
            });

            //Add to radio group
            radioGroup.addView(radioButton);
        }
    }

    private void buyCard(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Log.d("id", vehicleId + "as");
        Call<BuyCarResponse> call = apiService.buyCar(vehicleId,
                userLocalStore.getLoggedInUser().getId());

        call.enqueue(new Callback<BuyCarResponse>() {

            @Override
            public void onResponse(Call<BuyCarResponse> call, Response<BuyCarResponse> response) {

                pricesList = response.body().getPricesList();
                user = response.body().getUser();

                getUser();

                getPrices();

                //Log.d("prices", prices.length + ":");

                createRadioButtons(pricesArr, nameEn);

                Log.d("priceList", pricesArr.size() + ":");

            }

            @Override
            public void onFailure(Call<BuyCarResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("m", t.toString());
                Log.e("M", "error");
                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitBuyCard(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SubmitBuyCardResponse> call = apiService.buyCarSubmit(vehicleId,
                userLocalStore.getLoggedInUser().getId(), priceId
                , etPhone.getText().toString(), etEmail.getText().toString()
                , etLoc.getText().toString(), etLoc.getText().toString());

        call.enqueue(new Callback<SubmitBuyCardResponse>() {

            @Override
            public void onResponse(Call<SubmitBuyCardResponse> call, Response<SubmitBuyCardResponse> response) {

                String done = response.body().getDone();
                String message = response.body().getMessage();

                Log.d("done", done + ":" + message);

            }

            @Override
            public void onFailure(Call<SubmitBuyCardResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("m", t.toString());
                Log.e("M", "error");
                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getPrices(){

        for (int i = 0; i < pricesList.size(); i++) {

            prices.setId(pricesList.get(i).getId());
            prices.setPrice(pricesList.get(i).getPrice());
            prices.setNameEn(pricesList.get(i).getNameEn());
            prices.setNameAr(pricesList.get(i).getNameAr());

            pricesArr.add(prices.getPrice());
            nameEn.add(prices.getNameEn());
            nameAr.add(prices.getNameAr());

            pricesId.put(prices.getNameEn(), prices.getId());

        }

    }

    private void getUser(){
        String name, email, phone, loc;
        name = user.getName();
        email = user.getEmail();
        phone = user.getPhone();
        loc = user.getAddEn();

        etName.setText(name);
        etEmail.setText(email);
        etPhone.setText(phone);
        etLoc.setText(loc);
    }

    @Override
    public void onClick(View view) {
        int position = view.getId();

        switch (position){
            case R.id.btnSubmitBuy:

        }
    }
}
