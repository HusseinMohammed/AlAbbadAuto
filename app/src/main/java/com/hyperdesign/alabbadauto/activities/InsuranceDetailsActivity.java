package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.insurance.InsuranceDetailsFragment;

public class InsuranceDetailsActivity extends AppCompatActivity
        implements InsuranceDetailsFragment.OnFragmentInteractionListener {

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_detials);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_brands));

        Intent intent = getIntent();
        id = getIntent().getIntExtra("id",0);
        Log.d("latitude",  id + "");

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        fragment = new InsuranceDetailsFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);

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
        Intent intent = new Intent(this, InsuranceCompaniesActivity.class);
        startActivity(intent);
    }


}
