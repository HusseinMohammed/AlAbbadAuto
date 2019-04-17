package com.hyperdesign.alabbadauto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.localize.SessionManager;
import com.hyperdesign.alabbadauto.ui.MainActivity;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.BrandsActivity;
import com.hyperdesign.alabbadauto.classes.Vehicles;
import com.hyperdesign.alabbadauto.fragments.searchModel.BrandsFragment;
import com.hyperdesign.alabbadauto.fragments.searchModel.VehiclesFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_CAT;


/**
 * Created by Hyper Design Hussien on 2/3/2018.
 */

public class VehiclesAdapter extends
        RecyclerView.Adapter<VehiclesAdapter.VehiclesAdapterViewHolder> {

    // COMPLETED (23) Create a private string array called mWeatherData
    //private String[] mVehiclesData;

    // COMPLETED (23) Create a private string array called mWeatherData
    private ArrayList<Vehicles> mVehiclesData;

    private Vehicles mVehicles = new Vehicles();

    Context mVehiclesFragment;

    private MainActivity mMainActivity = new MainActivity();

    private Context context = null;

    private int mNumberItems;

    //Define fragment
    Fragment fragment;

    private SessionManager session;
    private String sessionLan;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public VehiclesAdapter(){

    }

    // COMPLETED (47) Create the constructor (we will pass in parameters(numberOfItems))
    public VehiclesAdapter(ArrayList<Vehicles> vehicles, Context vehiclesFragment) {
        this.mVehiclesData = vehicles;
        this.mVehiclesFragment = vehiclesFragment;
    }




    // COMPLETED (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // COMPLETED (17) Extend RecyclerView.ViewHolder
    /**
     * Cache of the children views for a forecast list item.
     */
    class VehiclesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        public final CardView cardViewVehicles;

        // COMPLETED (18) Create a public final TextView variable called mCategoriesImageView
        public final ImageView mVehiclesImageView;

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mCategoriesTextView
        public final TextView mVehiclesTextView;

        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public VehiclesAdapterViewHolder(View view) {
            super(view);
            mVehiclesImageView = (ImageView) view.findViewById(R.id.iv_vehicles);
            mVehiclesTextView = (TextView) view.findViewById(R.id.tv_vehicles);
            cardViewVehicles = (CardView) view.findViewById(R.id.cv_vehicles);
            /*mVehiclesImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //loadFragment(mVehiclesFragment);
                    Intent intent = new Intent(mVehiclesFragment.getActivity(), BrandsActivity.class);
                    mVehiclesFragment.startActivity(intent);
                }
            });*/
            cardViewVehicles.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position =(int) view.getTag();
            mVehicles = mVehiclesData.get(position);

            Intent intent = new Intent(mVehiclesFragment, BrandsActivity.class);
            Log.d("sss", mVehicles.getVehicleId() + " ss");
            intent.putExtra("idVehicle", mVehicles.getVehicleId());
            mVehiclesFragment.startActivity(intent);
            //loadFragment(mVehiclesFragment);
            /*Intent intent = new Intent(mMainActivity, BrandsActivity.class);
            intent.putExtra("spinCategory", "spin");
            mMainActivity.startActivity(intent);*/
        }

        private void loadFragment(Fragment fragment) {
            fragment = new BrandsFragment();
            FragmentManager fragmentManager = mMainActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.your_placeholder, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // load fragment
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.your_placeholder, fragment);
            transaction.addToBackStack(null);
            transaction.commit();*/
        }

        /*void bind(int listIndex){
            mCategoriesTextView.setText(String.valueOf(listIndex));
            //mCategoriesImageView.setImageResource(listIndex);
        }*/

    }

    @Override
    public VehiclesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.vehicles_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        VehiclesAdapterViewHolder viewHolder = new VehiclesAdapterViewHolder(view);
        return viewHolder; //new CategoriesAdapterViewHolder(view)
    }

    @Override
    public void onBindViewHolder(VehiclesAdapterViewHolder holder, int position) {
        System.out.println(mVehiclesData);
        mVehicles = mVehiclesData.get(position);

        holder.cardViewVehicles.setTag(position);

        String urlOfImage = mVehicles.getImageUrlVehicles();

        // Session manager
        session = new SessionManager(mVehiclesFragment);

        sessionLan = session.isLanguageIn();

        //holder.mVehiclesImageView.setImageResource(R.drawable.school_bus);
        if(urlOfImage.equals("")){
            holder.mVehiclesImageView.setImageResource(R.drawable.school_bus);
        } else {
            Picasso.with(context).load(BASE_URL_API + BASE_URL_IMAGE_CAT + urlOfImage).into(holder.mVehiclesImageView);//
        }

        if(sessionLan.equals("en")){
            holder.mVehiclesTextView.setText(mVehicles.getNameVehicleEng());
        } else {
            holder.mVehiclesTextView.setText(mVehicles.getNameVehicleArab());
        }

        /*String categoryText = mVehiclesData[position];
        String categoryImage = mVehiclesData[position];
        holder.mCategoriesImageView.setImageResource(Integer.valueOf(categoryText));
        holder.mCategoriesTextView.setText(categoryImage);
    */}

    @Override
    public int getItemCount() {
        if (null == mVehiclesData)
            return 1;
        return mVehiclesData.size(); //mNumberItems
    }

    public void setCategoriesData(ArrayList<Vehicles> vehiclesData) {
        mVehiclesData = vehiclesData;
        notifyDataSetChanged();
    }

}
