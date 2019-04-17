package com.hyperdesign.alabbadauto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.ModelActivity;
import com.hyperdesign.alabbadauto.classes.Brands;
import com.hyperdesign.alabbadauto.fragments.searchModel.BrandsFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_BRAND;

/**
 * Created by Hyper Design Hussien on 2/4/2018.
 */

public class BrandsAdapter extends
        RecyclerView.Adapter<BrandsAdapter.BrandsAdapterViewHolder> {

    // COMPLETED (23) Create a private string array called mBrandsData
    //private String[] mBrandsData;
    // COMPLETED (23) Create a private string array called mBrandsData
    private ArrayList<Brands> mBrandsData;

    private Brands mBrands = new Brands();

    Context mBrandsFragment;

    private Context context = null;

    private int mNumberItems;

    private SessionManager session;
    private String sessionLan;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public BrandsAdapter(){

    }

    // COMPLETED (47) Create the constructor (we will pass in parameters(numberOfItems))
    public BrandsAdapter(ArrayList<Brands> brands, Context brandsFragment) {
        this.mBrandsData = brands;
        this.mBrandsFragment = brandsFragment;
    }

    // COMPLETED (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // COMPLETED (17) Extend RecyclerView.ViewHolder
    /**
     * Cache of the children views for a forecast list item.
     */
    class BrandsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Within BrandsAdapterViewHolder ///////////////////////////////////////////////////////
        public final CardView cardViewBrands;

        // COMPLETED (18) Create a public final TextView variable called mBrandsImageView
        public final ImageView mBrandsImageView;

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mBrandsTextView
        public final TextView mBrandsTextView;


        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public BrandsAdapterViewHolder(View view) {
            super(view);
            mBrandsImageView = (ImageView) view.findViewById(R.id.iv_brands);
            mBrandsTextView = (TextView) view.findViewById(R.id.tv_brands);
            cardViewBrands = (CardView) view.findViewById(R.id.cv_brands);
            cardViewBrands.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position =(int) view.getTag();
            mBrands = mBrandsData.get(position);

            Intent intent = new Intent(mBrandsFragment, ModelActivity.class);
            Log.d("bbb", mBrands.getId() + " bb");
            intent.putExtra("idBrand", mBrands.getId());
            mBrandsFragment.startActivity(intent);

        }

        /*void bind(int listIndex){
            mCategoriesTextView.setText(String.valueOf(listIndex));
            //mCategoriesImageView.setImageResource(listIndex);
        }*/

    }

    @Override
    public BrandsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.brands_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        BrandsAdapterViewHolder viewHolder = new BrandsAdapterViewHolder(view);
        return viewHolder; //new CategoriesAdapterViewHolder(view)
    }

    @Override
    public void onBindViewHolder(BrandsAdapterViewHolder holder, int position) {
        mBrands = mBrandsData.get(position);

        holder.cardViewBrands.setTag(position);

        String urlOfImage = mBrands.getImageUrlBrands();

        // Session manager
        session = new SessionManager(mBrandsFragment);

        sessionLan = session.isLanguageIn();

        //holder.mBrandsImageView.setImageResource(R.drawable.porsche_logo);

        if(urlOfImage.equals(null)){
            holder.mBrandsImageView.setImageResource(R.drawable.porsche_logo);
        } else {
            Picasso.with(context).load(BASE_URL_API + BASE_URL_IMAGE_BRAND + urlOfImage).into(holder.mBrandsImageView);//
        }

        if (sessionLan.equals("en")){
            holder.mBrandsTextView.setText(mBrands.getBrandNameEng());
        } else {
            holder.mBrandsTextView.setText(mBrands.getBrandNameArab());
        }

        /*String categoryText = mVehiclesData[position];
        String categoryImage = mVehiclesData[position];
        holder.mCategoriesImageView.setImageResource(Integer.valueOf(categoryText));
        holder.mCategoriesTextView.setText(categoryImage);
    */}

    @Override
    public int getItemCount() {
        if (null == mBrandsData)
            return 1;
        return mBrandsData.size(); //mNumberItems
    }

    public void setBrandsData(ArrayList<Brands> brandsData) {
        mBrandsData = brandsData;
        notifyDataSetChanged();
    }

}
