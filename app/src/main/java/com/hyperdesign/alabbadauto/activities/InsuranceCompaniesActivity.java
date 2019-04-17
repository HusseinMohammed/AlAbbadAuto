package com.hyperdesign.alabbadauto.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hyperdesign.alabbadauto.ui.MainActivity;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.insurance.InsuranceCompaniesFragment;

public class InsuranceCompaniesActivity extends AppCompatActivity {

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    private ProgressBar progressBar;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_companies);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_brands));

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //constraintLayout = (ConstraintLayout) findViewById(R.id.clInsuranceDetails);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        fragment = new InsuranceCompaniesFragment();
        loadFragment(fragment);

        /*if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new BrandsFragment()).commit();
        }*/

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            frameLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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
            frameLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/

}
