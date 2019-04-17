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

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.VehiclesAllDataActivity;
import com.hyperdesign.alabbadauto.activities.VehiclesDataActivity;
import com.hyperdesign.alabbadauto.classes.VehiclesData;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsDataFragment;
import com.hyperdesign.alabbadauto.fragments.vehicle.VehiclesDataFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_ITEM;

/**
 * Created by Hyper Design Hussien on 2/6/2018.
 */

public class VehiclesDataAdapter extends
        RecyclerView.Adapter<VehiclesDataAdapter.VehiclesDataAdapterViewHolder> {

    // COMPLETED (23) Create a private string array called mWeatherData
    //private String[] mVehiclesData;

    // COMPLETED (23) Create a private string array called mWeatherData
    private ArrayList<VehiclesData> mVehiclesDataArray;

    private VehiclesData mVehiclesData;

    VehiclesDataFragment mVehiclesDataFragment;

    ShowroomsDataFragment showroomsDataFragment;

    private VehiclesDataActivity mVehiclesDataActivity = new VehiclesDataActivity();

    private Context context = null;

    String checkContext;

    private int mNumberItems;

    //Define fragment
    Fragment fragment;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public VehiclesDataAdapter(){

    }

    // COMPLETED (47) Create the constructor (we will pass in parameters(numberOfItems))
    public VehiclesDataAdapter(ArrayList<VehiclesData> vehiclesData,
                               VehiclesDataFragment vehiclesDataFragment, ShowroomsDataFragment showroomsDataFragment, String checkContext) {
        this.mVehiclesDataArray = vehiclesData;
        this.mVehiclesDataFragment = vehiclesDataFragment;
        this.showroomsDataFragment = showroomsDataFragment;
        this.checkContext = checkContext;
    }




    // COMPLETED (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // COMPLETED (17) Extend RecyclerView.ViewHolder
    /**
     * Cache of the children views for a forecast list item.
     */
    class VehiclesDataAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        public final CardView cardViewVehiclesData;

        // COMPLETED (18) Create a public final TextView variable called mCategoriesImageView
        public final ImageView mVehiclesImageView;

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mCategoriesTextView
        public final TextView mVehiclesDataBrand;

        public final TextView mVehiclesDataModel;

        public final TextView mVehiclesDataShowroom;

        public final TextView mVehiclesDataPrice;

        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public VehiclesDataAdapterViewHolder(View view) {
            super(view);
            mVehiclesImageView = (ImageView) view.findViewById(R.id.iv_vehicles_data);
            mVehiclesDataBrand = (TextView) view.findViewById(R.id.tv_brand_vehicles_data);
            mVehiclesDataModel = (TextView) view.findViewById(R.id.tv_model_vehicles_data);
            mVehiclesDataPrice = (TextView) view.findViewById(R.id.tv_price_vehicles_data);
            mVehiclesDataShowroom = (TextView) view.findViewById(R.id.tv_showroom_vehicles_data);
            cardViewVehiclesData = (CardView) view.findViewById(R.id.cv_vehicles_data);
            /*mVehiclesImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //loadFragment(mVehiclesFragment);
                    Intent intent = new Intent(mVehiclesFragment.getActivity(), BrandsActivity.class);
                    mVehiclesFragment.startActivity(intent);
                }
            });*/
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mVehiclesData = mVehiclesDataArray.get(position);
            System.out.println("ids" + position);


            if(checkContext.equals("vehicle")){
                Intent intent = new Intent(mVehiclesDataFragment.getActivity(), VehiclesAllDataActivity.class);
                intent.putExtra("id", mVehiclesData.getIdVehicleData());
                mVehiclesDataFragment.startActivity(intent);
            } else {
                Intent intent = new Intent(showroomsDataFragment.getActivity(), VehiclesAllDataActivity.class);
                intent.putExtra("id", mVehiclesData.getIdVehicleData());
                showroomsDataFragment.startActivity(intent);
            }


            //loadFragment(mVehiclesFragment);
            /*Intent intent = new Intent(mMainActivity, BrandsActivity.class);
            intent.putExtra("spinCategory", "spin");
            mMainActivity.startActivity(intent);*/
        }

        private void loadFragment(Fragment fragment) {
            fragment = new VehiclesDataFragment();
            FragmentManager fragmentManager = mVehiclesDataActivity.getSupportFragmentManager();
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
    public VehiclesDataAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.vehicles_data_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        VehiclesDataAdapterViewHolder viewHolder = new VehiclesDataAdapterViewHolder(view);
        return viewHolder; //new CategoriesAdapterViewHolder(view)
    }

    @Override
    public void onBindViewHolder(VehiclesDataAdapterViewHolder holder, int position) {
        mVehiclesData = mVehiclesDataArray.get(position);

        holder.cardViewVehiclesData.setTag(position);

        String urlOfImage = mVehiclesData.getVehiclesUrlImage();

        if(urlOfImage.equals("")){
            holder.mVehiclesImageView.setImageResource(R.drawable.car_image);
        } else {
            Picasso.with(context).load(BASE_URL_API + BASE_URL_IMAGE_ITEM + urlOfImage).into(holder.mVehiclesImageView);//
        }

        System.out.println("PlaPla" +  mVehiclesData.getVehiclesBrandNameEn());
        holder.mVehiclesDataBrand.setText(mVehiclesData.getVehiclesBrandNameEn());

        holder.mVehiclesDataModel.setText(mVehiclesData.getVehiclesModelEn());

        holder.mVehiclesDataShowroom.setText(mVehiclesData.getVehiclesShowroomEn());

        holder.mVehiclesDataPrice.setText(String.valueOf(mVehiclesData.getVehiclesPrice() + " EGP"));

        /*String categoryText = mVehiclesData[position];
        String categoryImage = mVehiclesData[position];
        holder.mCategoriesImageView.setImageResource(Integer.valueOf(categoryText));
        holder.mCategoriesTextView.setText(categoryImage);
    */}

    @Override
    public int getItemCount() {
        if (null == mVehiclesDataArray)
            return 1;
        return mVehiclesDataArray.size(); //mNumberItems
    }

    public void setCategoriesData(ArrayList<VehiclesData> vehiclesData) {
        mVehiclesDataArray = vehiclesData;
        notifyDataSetChanged();
    }


}
