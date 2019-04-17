package com.hyperdesign.alabbadauto.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsDataFragment;

public class ShowroomsData extends AppCompatActivity
        implements ShowroomsDataFragment.OnFragmentInteractionListener{

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrooms_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_brands));

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.your_placeholder, createCustomFragment()).commit();
        }
    }

    private Fragment createCustomFragment() {
        Bundle bundle = new Bundle();
        Log.d("hamdaaaa", id + "");
        bundle.putInt("id", id);

        ShowroomsDataFragment brandsFragment = new ShowroomsDataFragment();
        brandsFragment.setArguments(bundle);
        return brandsFragment;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
