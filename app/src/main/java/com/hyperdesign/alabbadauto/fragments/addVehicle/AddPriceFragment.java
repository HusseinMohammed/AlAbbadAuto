package com.hyperdesign.alabbadauto.fragments.addVehicle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyperdesign.alabbadauto.ui.MainActivity;
import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.AddOptionsActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * Created by Hyper Design Hussien on 2/19/2018.
 */

public class AddPriceFragment extends Fragment implements View.OnClickListener {

    View view;

    //Define fragment
    Fragment fragment;

    private ImageView mBack;

    private Button mAddPrice, mDeletePrice, mNextToOptions;

    private ConstraintLayout mConstraintLayout;

    private LinearLayout mLinearLayout;

    final int n = 100;

    private LinearLayout[] mNLinearLayout = new LinearLayout[n];

    private EditText mName, mPrice;

    //Define and Initialize array of string(COUNTRIES)
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Egypt", "Russia", "Brazil", "Japan", "China"
    };

    private MaterialBetterSpinner mStatus;

    private ArrayAdapter<String> mAdapter;

    private LinearLayout parentLinearLayout;

    LinearLayout container;

    private Context context = getContext();
    private int your_message_length = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_price, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBack = (ImageView) getActivity().findViewById(R.id.back);

        mAddPrice = (Button) getActivity().findViewById(R.id.btnAddPrice);

        //mLinearLayout = (LinearLayout) getActivity().findViewById(R.id.linearAddPrice);

        mName = (EditText) getActivity().findViewById(R.id.name);

        mAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        mStatus = (MaterialBetterSpinner) getActivity().findViewById(R.id.status_spinner);

        mStatus.setAdapter(mAdapter);

        mPrice = (EditText) getActivity().findViewById(R.id.price);

        mDeletePrice = (Button) getActivity().findViewById(R.id.btnDeletePrice);

        container = (LinearLayout) getActivity().findViewById(R.id.container);

        parentLinearLayout = (LinearLayout) getActivity().findViewById(R.id.parentLinearLayout);

        mAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onAddField(view);
                layoutInflate();
            }
        });

        mDeletePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete(view);
            }
        });

        mBack.setOnClickListener(this);

        mNextToOptions = (Button) getActivity().findViewById(R.id.nextToOptions);
        mNextToOptions.setOnClickListener(this);

    }

    private void layoutInflate(){
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.fields_linear, null);

        TextView mName = (EditText) addView.findViewById(R.id.name);
        MaterialBetterSpinner mStatus = (MaterialBetterSpinner) addView.findViewById(R.id.status_spinner);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        TextView mPrice = (EditText) addView.findViewById(R.id.price);
        Button mDeletePrice = (Button) addView.findViewById(R.id.btnDeletePrice);

        mStatus.setAdapter(mAdapter);

        final View.OnClickListener thisListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
                listAllAddView();
            }
        };

        mDeletePrice.setOnClickListener(thisListener);
        container.addView(addView);

        /*info.append(
                "thisListener:\t" + thisListener + "\n"
                        + "addView:\t" + addView + "\n\n"
        );*/

        listAllAddView();

    }

    private void listAllAddView(){
        //reList.setText("");

        int childCount = container.getChildCount();
        for(int i=0; i < childCount; i++){
            View thisChild = container.getChildAt(i);
            //reList.append(thisChild + "\n");

            /*AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.textout);
            String childTextViewValue = childTextView.getText().toString();*/

            //reList.append("= " + childTextViewValue + "\n");
        }
    }

    /*private void addPriceView(){
        ConstraintLayout dynamicHolderConstraint = (ConstraintLayout) getActivity().findViewById(R.id.constraintAddPrice);

        LinearLayout dynamicHolderLinear = (LinearLayout) getActivity().findViewById(R.id.linearAddPrice);

        View dynamicView = LayoutInflater.from(context).inflate(R.layout.fragment_add_price, null, false);

        mName = (EditText) getActivity().findViewById(R.id.name);
        mStatus = (MaterialBetterSpinner) getActivity().findViewById(R.id.status_spinner);
        mPrice = (EditText) getActivity().findViewById(R.id.price);
        mDeletePrice = (Button) getActivity().findViewById(R.id.btnDeletePrice);

        dynamicHolder.addView(dynamicView);
    }*/

    private void addPrice(){
        // create a new textview
        final LinearLayout myLinearLayout = new LinearLayout(getActivity());

        // set some properties of rowTextView or something
        // rowTextView.setText("This is row #" + i);
        mName = (EditText) getActivity().findViewById(R.id.name);
        mStatus = (MaterialBetterSpinner) getActivity().findViewById(R.id.status_spinner);
        mPrice = (EditText) getActivity().findViewById(R.id.price);
        mDeletePrice = (Button) getActivity().findViewById(R.id.btnDeletePrice);

        // add the textview to the linearlayout
        myLinearLayout.addView(mName);
        myLinearLayout.addView(mStatus);
        myLinearLayout.addView(mPrice);
        myLinearLayout.addView(mDeletePrice);


        // save a reference to the textview for later
        //myLinearLayout[i] = mName;
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.fields_linear, null);

        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.back:
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                /*toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new InsuranceDetailsFragment();
                loadFragment(fragment);*/
                break;
            case R.id.nextToOptions:
                intent = new Intent(getActivity(), AddOptionsActivity.class);
                startActivity(intent);
                /*toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new InsuranceDetailsFragment();
                loadFragment(fragment);*/
                break;
        }

    }

    public interface OnFragmentInteractionListener {
    }

}
