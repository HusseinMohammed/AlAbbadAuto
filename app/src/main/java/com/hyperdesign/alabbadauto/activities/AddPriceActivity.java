package com.hyperdesign.alabbadauto.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.addVehicle.AddPriceFragment;

public class AddPriceActivity extends AppCompatActivity
        implements AddPriceFragment.OnFragmentInteractionListener {

    //Define fragment
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        fragment = new AddPriceFragment();
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


}
