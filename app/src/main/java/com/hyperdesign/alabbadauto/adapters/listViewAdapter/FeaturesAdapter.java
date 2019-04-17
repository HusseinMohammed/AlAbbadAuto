package com.hyperdesign.alabbadauto.adapters.listViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.classes.options.Options;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.List;

public class FeaturesAdapter extends BaseAdapter {

    Context context;
    List<Options> optionsList;
    private SessionManager session;
    private String sessionLan;

    public FeaturesAdapter(Context context, List<Options> optionsList) {
        this.context = context;
        this.optionsList = optionsList;
        //session = new SessionManager(context); //getApplicationContext()
    }

    @Override
    public int getCount() {
        return optionsList.size();
    }

    @Override
    public Object getItem(int i) {
        return optionsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return optionsList.indexOf(i);
    }

    //Private view holder class
    private class ViewHolder{
        TextView tvFeature, tvValue;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        FeaturesAdapter.ViewHolder viewHolder;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null) {

            // Session manager
            session = new SessionManager(context);

            sessionLan = session.isLanguageIn();

            view = layoutInflater.inflate(R.layout.features_row, null);
            viewHolder = new FeaturesAdapter.ViewHolder();

            viewHolder.tvFeature = (TextView) view.findViewById(R.id.tv_feature);
            viewHolder.tvValue = (TextView) view.findViewById(R.id.tv_value);

            viewHolder.tvFeature.setTextColor(Color.BLACK);
            viewHolder.tvValue.setTextColor(Color.BLACK);

            Options options = optionsList.get(i);

            if(sessionLan.equals("en")){
                viewHolder.tvFeature.setText(options.getNameEn() + "");
                viewHolder.tvValue.setText(options.getValueEn());
            } else {
                viewHolder.tvFeature.setText(options.getNameAr() + "");
                viewHolder.tvValue.setText(options.getValueAr());
            }
            view.setTag(viewHolder);

        } else {
            viewHolder = (FeaturesAdapter.ViewHolder) view.getTag();
        }

        return view;
    }
}
