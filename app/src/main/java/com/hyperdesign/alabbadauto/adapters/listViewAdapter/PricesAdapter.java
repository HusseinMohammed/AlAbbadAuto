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
import com.hyperdesign.alabbadauto.classes.price.Prices;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.List;

public class PricesAdapter extends BaseAdapter {

    Context context;
    List<Prices> pricesList;

    private SessionManager session;
    private String sessionLan;


    public PricesAdapter(Context context, List<Prices> pricesList) {
        this.context = context;
        this.pricesList = pricesList;
        //session = new SessionManager(context); //getApplicationContext()
    }

    @Override
    public int getCount() {
        return pricesList.size();
    }

    @Override
    public Object getItem(int i) {
        return pricesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pricesList.indexOf(i);
    }

    //Private view holder class
    private class ViewHolder{
        TextView tvType, tvPrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(view == null) {

            // Session manager
            session = new SessionManager(context);

            sessionLan = session.isLanguageIn();

            view = layoutInflater.inflate(R.layout.price_row, null);
            viewHolder = new ViewHolder();

            viewHolder.tvType = (TextView) view.findViewById(R.id.tv_type);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tv_price);

            viewHolder.tvType.setTextColor(Color.BLACK);
            viewHolder.tvPrice.setTextColor(Color.BLACK);

            Prices prices = pricesList.get(i);
            if(sessionLan.equals("en")){
                viewHolder.tvType.setText(prices.getNameEn());
                viewHolder.tvPrice.setText(prices.getPrice() + "");
            } else {
                viewHolder.tvType.setText(prices.getNameAr());
                viewHolder.tvPrice.setText(prices.getPrice() + "");
            }


            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        return view;
    }

}
