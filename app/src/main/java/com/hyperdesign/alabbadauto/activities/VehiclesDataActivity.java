package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hyperdesign.alabbadauto.ui.MainActivity;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsFragment;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehiclesDataFragment;

public class VehiclesDataActivity extends AppCompatActivity
        implements VehiclesDataFragment.OnFragmentInteractionListener
                  , ShowroomsFragment.OnFragmentInteractionListener
                  , VehiclesDataFragment.OnHeadlineSelectedListener
                  , ShowroomsFragment.OnHeadlineSelectedListener{

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    int cat, model, brand, year, modelId;
    String caseRadio, name, frag;

    OnHeadlineSelectedListener mCallback;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle bundle = new Bundle();
        //bundle.putInt();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_brands));

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Intent intent = getIntent();
        frag = intent.getStringExtra("frag");

        if(frag.equals("search")){
            cat = intent.getIntExtra("cat",0);
            brand = intent.getIntExtra("brand",0);
            model = intent.getIntExtra("model",0);
            year = intent.getIntExtra("year",0);
            caseRadio = intent.getStringExtra("case");
            name = intent.getStringExtra("name");
        } else {
            modelId = intent.getIntExtra("ModelId", 0);
        }


        Log.i("fff", cat + " c " + brand + model + year + caseRadio + name + frag +" m " + modelId);

        /*if (savedInstanceState == null) {
            VehiclesDataFragment vehiclesDataFragment = new VehiclesDataFragment();
            vehiclesDataFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, vehiclesDataFragment, "your_fragment_tag").commit();
        } else {
            VehiclesDataFragment vehiclesDataFragment = (VehiclesDataFragment) getSupportFragmentManager().findFragmentByTag("your_fragment_tag");
        }*/

        Bundle bundle = new Bundle();
        if(frag.equals("search")){
            bundle.putString("frag", frag);
            bundle.putInt("cat", cat);
            bundle.putInt("brand", brand);
            bundle.putInt("model", model);
            bundle.putInt("year", year);
            bundle.putString("case", caseRadio);
            bundle.putString("name", name);
        } else {
            bundle.putString("frag", frag);
            bundle.putInt("idModel", modelId);
        }


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor spEditor = preferences.edit();
        if(frag.equals("search")){
            spEditor.putInt("cat", cat);
            spEditor.putInt("brand", brand);
            spEditor.putInt("model", model);
            spEditor.putInt("year", year);
            spEditor.putString("name", name);
            spEditor.putString("case", caseRadio);
            spEditor.commit();
        } else {
            spEditor.putInt("modelId", modelId);
            spEditor.commit();
        }

        fragment = new VehiclesDataFragment();
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
        Boolean fromSearch = false;
        /*if(!fromSearch){
            //From recycle view
        } else {
            //From Search

        }*/

        //Handle click of button search(What we do?)
        Intent intent = new Intent(VehiclesDataActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finishActivity(1);
        startActivity(intent);
        finish();

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
    }

}
