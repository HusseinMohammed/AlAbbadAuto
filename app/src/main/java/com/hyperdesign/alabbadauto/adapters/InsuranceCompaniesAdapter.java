package com.hyperdesign.alabbadauto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.InsuranceDetailsActivity;
import com.hyperdesign.alabbadauto.classes.InsuranceCompanies;
import com.hyperdesign.alabbadauto.fragments.insurance.InsuranceCompaniesFragment;
import com.hyperdesign.alabbadauto.localize.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_INSH;

/**
 * Created by Hyper Design Hussien on 2/11/2018.
 */

public class InsuranceCompaniesAdapter extends
        RecyclerView.Adapter<InsuranceCompaniesAdapter.InsuranceAdapterViewHolder> {

    // COMPLETED (23) Create a private string array called mBrandsData
    //private String[] mBrandsData;
    // COMPLETED (23) Create a private string array called mBrandsData
    private ArrayList<InsuranceCompanies> mInsuranceCompaniesData;

    private InsuranceCompanies mInsuranceCompanies = new InsuranceCompanies();

    Context mInsuranceCompaniesFragment;

    private Context context = null;

    private SessionManager session;
    private String sessionLan;

    // COMPLETED (47) Create the default constructor (we will pass in parameters in a later lesson)
    public InsuranceCompaniesAdapter(){

    }

    // COMPLETED (47) Create the constructor (we will pass in parameters(numberOfItems))
    public InsuranceCompaniesAdapter(ArrayList<InsuranceCompanies> insuranceCompanies, Context insuranceCompaniesFragment) {
        this.mInsuranceCompaniesData = insuranceCompanies;
        this.mInsuranceCompaniesFragment = insuranceCompaniesFragment;
    }



    class InsuranceAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Within BrandsAdapterViewHolder ///////////////////////////////////////////////////////
        public final CardView cardViewBrands;

        public final ImageView colorView;

        // COMPLETED (18) Create a public final TextView variable called mBrandsImageView
        public final ImageView mLogoImageView;

        // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////
        // COMPLETED (18) Create a public final TextView variable called mBrandsTextView
        public final TextView mNameTextView;

        public final TextView mAddressTextView;

        public final TextView mInfoTextView;

        //public final TextView mRequestTextView;


        // COMPLETED (19) Create a constructor for this class that accepts a View as a parameter
        // COMPLETED (20) Call super(view)
        // COMPLETED (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
        public InsuranceAdapterViewHolder(View view) {
            super(view);
            mLogoImageView = (ImageView) view.findViewById(R.id.imageViewLogo);
            mNameTextView = (TextView) view.findViewById(R.id.textViewName);
            mAddressTextView = (TextView) view.findViewById(R.id.textViewAddress);
            mInfoTextView = (TextView) view.findViewById(R.id.textViewInfo);
            colorView = (ImageView) view.findViewById(R.id.colorView);

            cardViewBrands = (CardView) view.findViewById(R.id.cardViewInsuranceCompanies);
            cardViewBrands.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position =(int) view.getTag();
            mInsuranceCompanies = mInsuranceCompaniesData.get(position);

            Intent intent = new Intent(mInsuranceCompaniesFragment, InsuranceDetailsActivity.class);
            intent.putExtra("id", mInsuranceCompanies.getId());
            mInsuranceCompaniesFragment.startActivity(intent);
        }

    }

    @Override
    public InsuranceAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.insurance_companies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        InsuranceAdapterViewHolder viewHolder = new InsuranceAdapterViewHolder(view);
        return viewHolder; //new CategoriesAdapterViewHolder(view)
    }

    @Override
    public void onBindViewHolder(InsuranceAdapterViewHolder holder, int position) {
        mInsuranceCompanies = mInsuranceCompaniesData.get(position);

        holder.cardViewBrands.setTag(position);

        String urlOfImage = mInsuranceCompanies.getImageUrlLogo();

        // Session manager
        session = new SessionManager(mInsuranceCompaniesFragment);

        sessionLan = session.isLanguageIn();


        if(urlOfImage.equals("")){
            holder.mLogoImageView.setImageResource(R.drawable.insurance_bus);
        } else {
            Picasso.with(context).load(BASE_URL_API + BASE_URL_IMAGE_INSH + urlOfImage).into(holder.mLogoImageView);//
        }
        String s = "";
        String s1 = "";
        String s2 = "";
        if(sessionLan.equals("en")){
            s = mInsuranceCompanies.getTextNameEn();
            s1 = mInsuranceCompanies.getTextAddressEn();
            s2 = Html.fromHtml(mInsuranceCompanies.getInfoEn()).toString();
        } else {
            s = mInsuranceCompanies.getTextNameAr();
            s1 = mInsuranceCompanies.getTextAddressAr();
            s2 = Html.fromHtml(mInsuranceCompanies.getInfoAr()).toString();
        }

        int n = 15;
        String upToNCharacters = s.substring(0, Math.min(s.length(), n));


        int n1 = 17;
        String upToNCharacters1 = s1.substring(0, Math.min(s1.length(), n1));


        int n2 = 7;
        String upToNCharacters2 = s2.substring(0, Math.min(s2.length(), n2));

        holder.mNameTextView.setText(upToNCharacters);
        holder.mAddressTextView.setText(upToNCharacters1 + "...");
        holder.mInfoTextView.setText(upToNCharacters2 + "...");

    }

    @Override
    public int getItemCount() {
        if (null == mInsuranceCompaniesData)
            return 1;
        return mInsuranceCompaniesData.size();
    }

    public void setBrandsData(ArrayList<InsuranceCompanies> brandsData) {
        mInsuranceCompaniesData = brandsData;
        notifyDataSetChanged();
    }

}
