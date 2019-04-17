package com.hyperdesign.alabbadauto.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.SignInAct;
import com.hyperdesign.alabbadauto.activities.SignupAct;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;
import com.hyperdesign.alabbadauto.fontFamily.TypefaceUtil;
import com.hyperdesign.alabbadauto.fragments.Tender;
import com.hyperdesign.alabbadauto.fragments.addVehicle.AddOptionsFragment;
import com.hyperdesign.alabbadauto.fragments.addVehicle.AddVehicleFragment;
import com.hyperdesign.alabbadauto.fragments.insurance.InsuranceCompaniesFragment;
import com.hyperdesign.alabbadauto.fragments.registration.SignInFrag;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsFrag;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsFragment;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehiclesDataFragment;
import com.hyperdesign.alabbadauto.fragments.searchModel.VehiclesFragment;
import com.hyperdesign.alabbadauto.fragments.SearchFragment;
import com.hyperdesign.alabbadauto.utilities.XmlClickable;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        VehiclesDataFragment.OnFragmentInteractionListener,
        ShowroomsFragment.OnFragmentInteractionListener,
        AddVehicleFragment.OnFragmentInteractionListener,
        SignInFrag.OnFragmentInteractionListener,
        Tender.OnFragmentInteractionListener,
        ShowroomsFragment.OnHeadlineSelectedListener,
        AddOptionsFragment.OnHeadlineSelectedListener, View.OnClickListener {

    //Define BottomNavigationView
    BottomNavigationView mBottomNav;
    //Define FragmentManager
    FragmentManager fragmentManager;
    //Define toolbar
    Toolbar toolbar;
    //Define drawer
    DrawerLayout drawer;
    //Define navigationView
    NavigationView navigationView;
    //Define fragment
    Fragment fragment;

    XmlClickable someFragment;

    //TODO CREATE HEADER INFO
    View header;

    //TODO CREATE TEXTVIEWS AND IMAGEVIEW
    ImageView ivUser;
    TextView tvLogin, tvSignUp, tvWel;

    Intent intent;

    UserLocalStore userLocalStore;

    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onStart() {
        super.onStart();
        /*toolbar.setTitle(getResources().getString(R.string.addOptions));
        fragment = new AddOptionsFragment();
        loadFragment(fragment);*/
        /*String success = "success";
        Intent intent = getIntent();
        if (success.equals(intent.getStringExtra("gallery"))){
            toolbar.setTitle(getResources().getString(R.string.title_add_vehicle));
            fragment = new AddVehicleFragment();
            loadFragment(fragment);
        } else {
            toolbar.setTitle(getResources().getString(R.string.title_search));
            fragment = new SearchFragment();
            loadFragment(fragment);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialogBuilder = new AlertDialog.Builder(
                getBaseContext());

        checkConnection();

        final Intent intent = getIntent();

        userLocalStore = new UserLocalStore(getBaseContext());

        //Initialize BottomNavigationView
        //mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        /*fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.your_placeholder,
                        new SearchFragment()).commit();*/

        //Initialize DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Initialize navigationView
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize header info
        header = navigationView.getHeaderView(0);

        //Initialize header TextViews and ImageView
        ivUser = (ImageView) header.findViewById(R.id.ivUser);
        tvLogin = (TextView) header.findViewById(R.id.tvLogin);
        tvSignUp = (TextView) header.findViewById(R.id.tvSignUp);
        tvWel = (TextView) header.findViewById(R.id.tvSignUp);

        tvLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignInAct.class);
                startActivity(i);
            }
        });

        toolbar.setTitle(getResources().getString(R.string.title_search));
        fragment = new SearchFragment();
        loadFragment(fragment);

        //checkUserLogin();

        setUserData();

        /*mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // handle desired action here
                // One possibility of action is to replace the contents above the nav bar
                // return true if you want the item to be displayed as the selected item
                //selectBottomDrawerItem(item);
                return true;
            }
        });*/
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/your_font_file.ttf");
        AssetManager am = getBaseContext().getApplicationContext().getAssets();

        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Cairo-Black.ttf"));

        tvSignUp.setTypeface(typeface);
        tvLogin.setTypeface(typeface);


    }


    private boolean checkConnection(){

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        System.out.println(is3g + " net " + isWifi);

        /*switch (successOrFail)
        {
            case successOrFail:

        }*/
        if(!isWifi && !is3g) {
            showAlertDialog();
            return isWifi;
        }
        return isWifi;

        /*if ( && )
        {
            *//*try {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(((GooglePlayServicesAvailabilityIOException) mLastError)
                            .getConnectionStatusCode());
                }
            } catch (IOException e){
                e.printStackTrace();
            }*//*
            //Toast.makeText(getApplicationContext(),"Please make sure your Network Connection is ON ",Toast.LENGTH_LONG).show();



        }
        else
        {
            //" Your method what you want to do "
            //onCreate();
        }


        *//*if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                showGooglePlayServicesAvailabilityErrorDialog(
                        ((GooglePlayServicesAvailabilityIOException) mLastError)
                                .getConnectionStatusCode());
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                startActivityForResult(
                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
                        MainActivity.REQUEST_AUTHORIZATION);
            } else {
                mOutputText.setText("The following error occurred:\n"
                        + mLastError.getMessage());
            }
        } else {
            mOutputText.setText("Request cancelled.");
        }*/
    }

    public void showAlertDialog(){
        alertDialogBuilder = new AlertDialog.Builder(
                getBaseContext());



        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.alert_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.exit_message),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        finish();
                        /*startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));*/
                        //enable wifi
                        //wifiMan.setWifiEnabled(true);
                    }
                })
                /*.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        //disable wifi
                        //wifiMan.setWifiEnabled(false);
                    }
                })*/;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        /*// set title
        alertDialogBuilder.setTitle("Network Settings");*/

        /*// set dialog message
        alertDialogBuilder
                .setMessage("Please Check Network Connection")
                .setCancelable(false)
                .setPositiveButton("Open Wi-Fi",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                        //enable wifi
                        //wifiMan.setWifiEnabled(true);
                    }
                })
                .setPositiveButton("Open Mobile Data",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        *//*Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                        startActivity(intent);*//*
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setComponent(new ComponentName("com.android.settings",
                                "com.android.settings.Settings$DataUsageSummaryActivity"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //disable wifi
                        //wifiMan.setWifiEnabled(false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();*/
    }

    private void checkUserLogin() {
        if(userLocalStore.getUserLoggedIn()){
            userLocalStore.clearUserData();
            tvLogin.setText(getResources().getString(R.string.signIn));
            tvSignUp.setText(getResources().getString(R.string.signUp));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void myClickMethod(View v) {
        someFragment.myClickMethod(v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void hideLoginVisibleLogoutItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_my_account).setVisible(false);

        Menu nav_Menu1 = navigationView.getMenu();
        nav_Menu1.findItem(R.id.nav_sign_out).setVisible(true);
    }

    private void hideLogoutVisibleLoginItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_my_account).setVisible(true);

        Menu nav_Menu1 = navigationView.getMenu();
        nav_Menu1.findItem(R.id.nav_sign_out).setVisible(false);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new SearchFragment();
                loadFragment(fragment);
                break;
            /*case R.id.nav_search:
                toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new SearchFragment();
                loadFragment(fragment);
                break;*/
            case R.id.nav_vehicles:
                toolbar.setTitle(getResources().getString(R.string.title_vehicles));
                fragment = new VehiclesFragment();
                loadFragment(fragment);
                break;
            case R.id.nav_insurance_companies:
                toolbar.setTitle(getResources().getString(R.string.title_insurance_companies));
                fragment = new InsuranceCompaniesFragment();
                loadFragment(fragment);
                break;
            case R.id.nav_showrooms:
                toolbar.setTitle(getResources().getString(R.string.title_showrooms));
                fragment = new ShowroomsFrag();
                loadFragment(fragment);
                /*
                Intent intent = new Intent(this, ShowroomsActivity.class);
                finish();
                startActivity(intent);*/
                break;
            case R.id.nav_my_account:
                toolbar.setTitle(getResources().getString(R.string.signIn));
                fragment = new SignInFrag();
                loadFragment(fragment);
                break;
            case R.id.nav_send_message:
                toolbar.setTitle(getResources().getString(R.string.tender));
                fragment = new Tender();
                loadFragment(fragment);
                break;
            case R.id.nav_sign_out:
                hideLogoutVisibleLoginItem();
                checkUserLogin();
                break;
        }

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tvLogin:
                intent = new Intent(this, SignInAct.class);
                startActivity(intent);
            case R.id.tvSignUp:
                intent = new Intent(this, SignupAct.class);
                startActivity(intent);
        }
    }

    private void setUserData(){
        if(userLocalStore.getUserLoggedIn()){
            hideLoginVisibleLogoutItem();
            tvSignUp.setText(userLocalStore.getLoggedInUser().getName());
            tvLogin.setText(userLocalStore.getLoggedInUser().getEmail());
        }
    }

}







//Define Method to handle select of bottom navigation
    /*
        public void selectBottomDrawerItem(MenuItem menuItem) {
            // Create a new fragment and specify the fragment to show based on nav item clicked
            Fragment fragment = new Fragment();
            Class fragmentClass = null;
            fragmentManager = getFragmentManager();
            switch(menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction()
                            .replace(R.id.your_placeholder,
                                    new SearchFragment()).commit();
                    fragmentClass = SearchFragment.class;
                    //toolbar.setTitle(getResources().getString(R.string.title_search));
                break;
                case R.id.navigation_dashboard:
                    //fragmentClass = SecondFragment.class;
                    break;
                case R.id.navigation_notifications:
                    //fragmentClass = ThirdFragment.class;
                    break;
                default:
                    fragmentClass = SearchFragment.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            //FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.your_placeholder, fragment).commit();

        }
    */