package com.hyperdesign.alabbadauto.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.addVehicle.AddOptionsFragment;

public class AddOptionsActivity extends FragmentActivity
        implements AddOptionsFragment.OnHeadlineSelectedListener{

    //Define fragment
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AddOptionsFragment firstFragment = new AddOptionsFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            /*getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, firstFragment).commit();*/  // required android.app.fragment
            fragment = new AddOptionsFragment();
            loadFragment(fragment);

        }

    }

    @Override
    public void onArticleSelected(int position) {

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
