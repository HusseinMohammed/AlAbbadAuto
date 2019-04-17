package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.insurance.InsuranceDetailsFragment;
import com.hyperdesign.alabbadauto.fragments.insurance.SendRequestFragment;

public class SendRequestActivity extends AppCompatActivity
        implements InsuranceDetailsFragment.OnFragmentInteractionListener,
                   SendRequestFragment.OnFragmentInteractionListener {

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    Intent intent;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);

        intent = getIntent();
        id = intent.getIntExtra("id",0);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        fragment = new SendRequestFragment();
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
    public void onFragmentInteraction(Uri uri) {

    }
   /*
   @Override
    public void onBackPressed() {
        super.onBackPressed();
        *//*VehiclesFragment vehiclesFragment = new VehiclesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.your_placeholder, vehiclesFragment)
                .addToBackStack(null)
                .commit();*//*

        //Handle click of button search(What we do?)
        Intent intent = new Intent(this, InsuranceDetailsFragment.class);
        startActivity(intent);
    }
    */

}
