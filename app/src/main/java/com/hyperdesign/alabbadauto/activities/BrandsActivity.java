package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.searchModel.BrandsFragment;
import com.hyperdesign.alabbadauto.fragments.searchModel.ModelFragment;
import com.hyperdesign.alabbadauto.fragments.searchModel.VehiclesFragment;

public class BrandsActivity extends AppCompatActivity implements
        VehiclesFragment.OnFragmentInteractionListener, ModelFragment.OnFragmentInteractionListener,
        BrandsFragment.OnFragmentInteractionListener {

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    int idVehicle, idBrand;

    @Override
    protected void onStart() {
        super.onStart();
        /*
        toolbar.setTitle(getResources().getString(R.string.title_brands));
        fragment = new BrandsFragment();
        loadFragment(fragment);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_brands));

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        idVehicle = intent.getIntExtra("idVehicle",0);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.your_placeholder, createCustomFragment()).commit();
        }




        //fragment = new BrandsFragment();
        //fragment.setArguments(bundle);
        //loadFragment(fragment);


        /*if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new BrandsFragment()).commit();
        }*/

    }

    private Fragment createCustomFragment() {
        Bundle bundle = new Bundle();
        Log.d("hamdaaaa", idVehicle + "");
        bundle.putInt("idVehicle", idVehicle);

        BrandsFragment brandsFragment = new BrandsFragment();
        brandsFragment.setArguments(bundle);
        return brandsFragment;
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
        /*VehiclesFragment vehiclesFragment = new VehiclesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.your_placeholder, vehiclesFragment)
                .addToBackStack(null)
                .commit();*/

        //Handle click of button search(What we do?)
        Bundle bundle = new Bundle();
        bundle.putInt("idVehicle", idVehicle);

        Intent i = new Intent(this,VehiclesFragment.class);
        i.putExtra("bundle", bundle);
        startActivity(i);
    }

}
