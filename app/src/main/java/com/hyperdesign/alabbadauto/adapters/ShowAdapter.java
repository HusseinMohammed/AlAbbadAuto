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

import com.bumptech.glide.Glide;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.ShowroomsData;
import com.hyperdesign.alabbadauto.classes.Showrooms;
import com.hyperdesign.alabbadauto.fragments.showrooms.ShowroomsFrag;
import com.hyperdesign.alabbadauto.localize.SessionManager;

import java.util.ArrayList;

import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_API;
import static com.hyperdesign.alabbadauto.apiConstants.ApiConstants.BASE_URL_IMAGE_SHOW;

public class ShowAdapter extends
                RecyclerView.Adapter<ShowAdapter.ShowViewHolder>{

    private ArrayList<Showrooms> showroomsList;

    Showrooms mShowrooms = new Showrooms();

    Context mShowroomsFragment;

    final private ListItemClickListener mOnClickListener;

    private SessionManager session;
    private String sessionLan;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ShowAdapter(Context mShowroomsFragment, ArrayList<Showrooms> showrooms, ListItemClickListener mOnClickListener){
        this.mShowroomsFragment = mShowroomsFragment;
        this.showroomsList = showrooms;
        this.mOnClickListener = mOnClickListener;
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        public TextView name, cars;
        public ImageView logo, overflow;

        public ShowViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.showroomName);
            cars = (TextView) view.findViewById(R.id.showroomCars);
            logo = (ImageView) view.findViewById(R.id.showroomLogo);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = (CardView) view.findViewById(R.id.card_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            //int position =(int) view.getTag();
            //Log.d("ids", position + "");
            mShowrooms = showroomsList.get(clickedPosition);

            Intent intent = new Intent(mShowroomsFragment, ShowroomsData.class);
            Log.d("idShow", mShowrooms.getId() + " show");
            intent.putExtra("id", mShowrooms.getId());
            mShowroomsFragment.startActivity(intent);
            //loadFragment(fragment);
        }

    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.shoowrooms_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ShowViewHolder viewHolder = new ShowViewHolder(view);
        /*View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoowrooms_list_item, parent, false);*/
        return viewHolder; //new MyViewHolder(itemView)
    }

    @Override
    public void onBindViewHolder(final ShowViewHolder holder, int position) {
        System.out.println(showroomsList);
        Showrooms showrooms = showroomsList.get(position);


        // Session manager
        session = new SessionManager(mShowroomsFragment);

        sessionLan = session.isLanguageIn();

        if(sessionLan.equals("en")){
            holder.name.setText(showrooms.getNameEng());
        } else {
            holder.name.setText(showrooms.getNameArab());
        }


        //holder.cars.setText(String.valueOf(5)); //showrooms.getShowroomCars()

        String url = showrooms.getLogo();

        if(url.equals(null)){
            //loading cars albums using Glide Library
            Glide.with(mShowroomsFragment).load(BASE_URL_API + BASE_URL_IMAGE_SHOW + url).into(holder.logo);
        } else {
            //loading cars albums using Glide Library
            Glide.with(mShowroomsFragment).load(BASE_URL_API + BASE_URL_IMAGE_SHOW + url).into(holder.logo);
        }

    }

    @Override
    public int getItemCount() {
        if (null == showroomsList)
            return 1;
        return showroomsList.size();
    }
}
