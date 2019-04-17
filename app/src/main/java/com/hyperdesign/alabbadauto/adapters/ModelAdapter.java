package com.hyperdesign.alabbadauto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.VehiclesDataActivity;
import com.hyperdesign.alabbadauto.classes.Model;
import com.hyperdesign.alabbadauto.fragments.searchModel.ModelFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/4/2018.
 */

public class ModelAdapter extends
        RecyclerView.Adapter<ModelAdapter.ModelAdapterViewHolder>{

    // COMPLETED (23) Create a private string array called mModelData
    //private String[] mBrandsData;
    // COMPLETED (23) Create a private string array called mModelData
    private ArrayList<Model> mModelData;

    private Model mModel = new Model();

    Context mModelFragment;

    private Context context = null;

    private int mNumberItems;

    private SessionManager session;
    private String sessionLan;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public ModelAdapter(){

    }

    // COMPLETED (47) Create the constructor (we will pass in parameters(numberOfItems))
    public ModelAdapter(ArrayList<Model> models, Context modelFragment) {
        this.mModelData = models;
        this.mModelFragment = modelFragment;
    }

    // COMPLETED (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // COMPLETED (17) Extend RecyclerView.ViewHolder
    /**
     * Cache of the children views for a forecast list item.
     */
    class ModelAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Within BrandsAdapterViewHolder ///////////////////////////////////////////////////////
        public final CardView cardViewModel;

        // COMPLETED (18) Create a public final TextView variable called mBrandsImageView
        //public final ImageView mModelImageView;

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mBrandsTextView
        public final TextView mModelTextView;


        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public ModelAdapterViewHolder(View view) {
            super(view);
            //mModelImageView = (ImageView) view.findViewById(R.id.iv_model);
            mModelTextView = (TextView) view.findViewById(R.id.tv_model);
            cardViewModel = (CardView) view.findViewById(R.id.cv_model);
            cardViewModel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            mModel = mModelData.get(position);
            Log.d("ids", position + "" + mModel.getId());
            Intent intent = new Intent(mModelFragment, VehiclesDataActivity.class);
            intent.putExtra("ModelId", mModel.getId());
            intent.putExtra("frag", "model");
            mModelFragment.startActivity(intent);
        }

        /*void bind(int listIndex){
            mCategoriesTextView.setText(String.valueOf(listIndex));
            //mCategoriesImageView.setImageResource(listIndex);
        }*/

    }

    @Override
    public ModelAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.model_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ModelAdapterViewHolder viewHolder = new ModelAdapterViewHolder(view);
        return viewHolder; //new CategoriesAdapterViewHolder(view)
    }

    @Override
    public void onBindViewHolder(ModelAdapterViewHolder holder, int position) {
        mModel = mModelData.get(position);

        holder.cardViewModel.setTag(position);

        String urlOfImage = mModel.getImageUrlModel();

        // Session manager
        session = new SessionManager(mModelFragment);

        sessionLan = session.isLanguageIn();

        //holder.mModelImageView.setImageResource(R.drawable.porsche_logo);
        /*if(urlOfImage.equals("")){
            holder.mModelImageView.setImageResource(R.drawable.porsche_logo);
        } else {
            Picasso.with(context).load(urlOfImage).into(holder.mModelImageView);//
        }*/

        if(sessionLan.equals("en")){
            holder.mModelTextView.setText(mModel.getNameModelEn());
        } else {
            holder.mModelTextView.setText(mModel.getNameModelAr());
        }

        /*String categoryText = mVehiclesData[position];
        String categoryImage = mVehiclesData[position];
        holder.mCategoriesImageView.setImageResource(Integer.valueOf(categoryText));
        holder.mCategoriesTextView.setText(categoryImage);
    */}

    @Override
    public int getItemCount() {
        if (null == mModelData)
            return 1;
        return mModelData.size(); //mNumberItems
    }

    public void setBrandsData(ArrayList<Model> brandsData) {
        mModelData = brandsData;
        notifyDataSetChanged();
    }


}
