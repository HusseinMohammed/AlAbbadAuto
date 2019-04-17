package com.hyperdesign.alabbadauto.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyperdesign.alabbadauto.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Hyper Design Hussien on 1/28/2018.
 */

public class ViewpagerAdapter extends PagerAdapter {
    Activity activity;
    String[] images;
    LayoutInflater layoutInflater;

    public ViewpagerAdapter(Activity activity, String[] images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.view_pager_item, container, false);

        ImageView imageView;
        imageView = (ImageView) itemView.findViewById(R.id.iv_slider);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);


        try {
            Picasso.with(activity.getApplicationContext())
                    .load(images[position])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        } catch (Exception e){

        }
        container.addView(itemView);
        return itemView;
    }
}
