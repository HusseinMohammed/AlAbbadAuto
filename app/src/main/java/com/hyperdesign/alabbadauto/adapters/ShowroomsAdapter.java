package com.hyperdesign.alabbadauto.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyperdesign.alabbadauto.activities.ShowroomsData;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsFrag;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.classes.Showrooms;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_SHOW;

/**
 * Created by Hyper Design Hussein on 2/14/2018.
 */

public class ShowroomsAdapter extends RecyclerView.Adapter<ShowroomsAdapter.MyViewHolder> {

    //private Context mContext;

    private ArrayList<Showrooms> showroomsList;

    //Define fragment
    Fragment fragment;

    Showrooms mShowrooms = new Showrooms();

    ShowroomsFrag mShowroomsFragment;

    private Context context = null;

    public ShowroomsAdapter(ShowroomsFrag mShowroomsFragment, ArrayList<Showrooms> showrooms){
        this.mShowroomsFragment = mShowroomsFragment;
        this.showroomsList = showrooms;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView name, cars;
        public ImageView logo, overflow;

        public MyViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.showroomName);
            cars = (TextView) view.findViewById(R.id.showroomCars);
            logo = (ImageView) view.findViewById(R.id.showroomLogo);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //int position =(Integer) view.getTag();
            mShowrooms = showroomsList.get(4);

            Intent intent = new Intent(mShowroomsFragment.getActivity(), ShowroomsData.class);
            Log.d("idShow", mShowrooms.getId() + " show");
            intent.putExtra("id", mShowrooms.getId());
            mShowroomsFragment.startActivity(intent);
            //loadFragment(fragment);
        }

    }

    /*private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.shoowrooms_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoowrooms_list_item, parent, false);*/
        return viewHolder; //new MyViewHolder(itemView)
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        System.out.println(showroomsList);
        Showrooms showrooms = showroomsList.get(position);
        holder.name.setText(showrooms.getNameEng());

        holder.cars.setText(String.valueOf(5)); //showrooms.getShowroomCars()

        String url = showrooms.getLogo();

        if(url.equals(null)){
            //loading cars albums using Glide Library
            Glide.with(mShowroomsFragment).load(BASE_URL_API + BASE_URL_IMAGE_SHOW + url).into(holder.logo);
        } else {
            //loading cars albums using Glide Library
            Glide.with(mShowroomsFragment).load(BASE_URL_API + BASE_URL_IMAGE_SHOW + url).into(holder.logo);
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view){
        //inflate menu
        PopupMenu popupMenu = new PopupMenu(mShowroomsFragment.getActivity(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_showrooms, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener(){

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mShowroomsFragment.getActivity(), "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                /*case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;*/
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        if (null == showroomsList)
            return 1;
        return showroomsList.size();
    }

    public void setBrandsData(ArrayList<Showrooms> brandsData) {
        showroomsList = brandsData;
        notifyDataSetChanged();
    }

}
