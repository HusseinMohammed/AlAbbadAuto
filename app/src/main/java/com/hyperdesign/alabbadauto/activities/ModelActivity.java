package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.searchModel.ModelFragment;

public class ModelActivity extends AppCompatActivity {

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    int idBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        idBrand = intent.getIntExtra("idBrand",0);

        Log.d("hamdaaaa", idBrand + "");
        Bundle bundle = new Bundle();
        bundle.putInt("idBrand", idBrand);

        toolbar.setTitle(getResources().getString(R.string.title_brands));
        fragment = new ModelFragment();
        fragment.setArguments(bundle);
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
        Intent intent = new Intent(this, BrandsActivity.class);
        intent.putExtra("idBrand", idBrand);
        startActivity(intent);
    }

}
