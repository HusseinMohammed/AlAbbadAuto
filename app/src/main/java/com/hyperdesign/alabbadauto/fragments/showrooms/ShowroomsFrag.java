package com.hyperdesign.alabbadauto.fragments.showrooms;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.adapters.ShowAdapter;
import com.hyperdesign.alabbadauto.api.model.ShowroomsResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.Showrooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ShowroomsFrag extends Fragment implements ShowAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private ShowAdapter adapter;
    private ArrayList<Showrooms> mShowroomsList;

    private ArrayList<String> showroomsString;

    private Map<String, Integer> showroomsMap;

    //ArrayList<Showrooms> showroomsList;

    Showrooms showrooms;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toast mToast;

    public ShowroomsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowroomsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowroomsFragment newInstance(String param1, String param2) {
        ShowroomsFragment fragment = new ShowroomsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showroomsString = new ArrayList<>();
        showroomsMap = new HashMap<>();
        showrooms = new Showrooms();
        mShowroomsList = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_showrooms, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rvShowrooms);

        getShowrooms();

    }

    private void recycleView(){
        System.out.println(mShowroomsList);
        adapter = new ShowAdapter(getContext(), mShowroomsList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ShowroomsFrag.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    //TODO Showrooms Function to call Api(Get Showrooms)
    private void getShowrooms(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ShowroomsResponse> call = apiService.getShowrooms();
        call.enqueue(new Callback<ShowroomsResponse>() {
            @Override
            public void onResponse(Call<ShowroomsResponse> call, Response<ShowroomsResponse> response) {
                mShowroomsList = response.body().getShowroomsList();

                Log.d(TAG, "Number of movies received: " + mShowroomsList.get(0).getNameEng() + mShowroomsList.size());

                getShowroomsData();

                recycleView();
            }

            @Override
            public void onFailure(Call<ShowroomsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    //TODO Showrooms Function to get json array category from Api(Get Showrooms)
    private void getShowroomsData(){

        for(int i = 0; i < mShowroomsList.size(); i++){

            showrooms = new Showrooms(mShowroomsList.get(i).getId(), mShowroomsList.get(i).getNameEng(),
                    mShowroomsList.get(i).getNameArab(), mShowroomsList.get(i).getShowroomCars(), mShowroomsList.get(i).getLogo());

            showroomsMap.put(mShowroomsList.get(i).getNameEng(), mShowroomsList.get(i).getId());

            showroomsString.add(showrooms.getNameEng());
            //showroomsList.add(showrooms);
            //mShowroomsList.add(showrooms);
            System.out.println(showroomsString);
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        //mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);

        //mToast.show();
    }

    public interface OnFragmentInteractionListener {
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}
