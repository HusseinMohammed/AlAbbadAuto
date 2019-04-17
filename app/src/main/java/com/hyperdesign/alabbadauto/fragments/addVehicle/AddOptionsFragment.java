package com.hyperdesign.alabbadauto.fragments.addVehicle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/19/2018.
 */

public class AddOptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View view;

    private ImageView mBack;

    private TextView mTextView;

    //Define and Initialize array of string(COUNTRIES)
    private static final String[] COUNTRIES = new String[] {
            "Cars", "France", "Italy", "Germany", "Spain", "Egypt", "Russia", "Brazil", "Japan", "China"
    };

    private MaterialBetterSpinner mStatus;

    private ArrayAdapter<String> mAdapter;

    private ArrayList<String> mCategoryType;

    private LinearLayout linearContainer;

    Spinner spinner;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    OnHeadlineSelectedListener mCallback;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_options, container, false);
        mCategoryType = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBack = (ImageView) getActivity().findViewById(R.id.back);

        mTextView = (TextView) getActivity().findViewById(R.id.addOptions);

        mAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        mStatus = (MaterialBetterSpinner) getActivity().findViewById(R.id.category_spinner);

        linearContainer = (LinearLayout) getActivity().findViewById(R.id.container);

        mStatus.setAdapter(mAdapter);
        //mStatus.setOnItemSelectedListener(this);

        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().equals("Cars")){
                    inflateLayout();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner = (Spinner) getActivity().findViewById(R.id.spinner);

        spinner.setAdapter(mAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getSelectedItem().toString();

                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();

                if(item.equals("Cars")){
                    Toast.makeText(getActivity(), adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                    inflateLayout();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void inflateLayout(){
        //Define Layout
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addViewCheck = layoutInflater.inflate(R.layout.car_options, null);

        final View addViewRadio = layoutInflater.inflate(R.layout.car_options, null);

        final View addViewEdit = layoutInflater.inflate(R.layout.car_options, null);

        //Define XML Layout TextView, CheckBox, RadioGroup, RadioButton, EditText
        TextView mTextRadio, mTextEdit, mTextCheck;
        CheckBox mCheckBox;

        RadioGroup mRadioGroup;
        RadioButton mRadioButton;

        EditText mEditText;

        mTextCheck = addViewCheck.findViewById(R.id.tvCheck);

        mCheckBox = addViewCheck.findViewById(R.id.checkbox);

        mTextRadio = addViewRadio.findViewById(R.id.tvRadio);

        mRadioGroup = addViewRadio.findViewById(R.id.radioGroup);
        mRadioButton = addViewRadio.findViewById(R.id.radioButton);

        mTextEdit = addViewEdit.findViewById(R.id.tvEdit);
        mEditText = addViewEdit.findViewById(R.id.editText);

        //Add layout in Linear Container
        linearContainer.addView(addViewCheck);

        linearContainer.addView(addViewRadio);

        linearContainer.addView(addViewEdit);

        listCheckBox();

        listRadioButton();

        listEditText();

        //Duplicate Layout Elements(TextView, CheckBox, RadioGroup, RadioButton, EditText)

    }

    private void listCheckBox(){
    }

    private void listRadioButton(){

    }

    private void listEditText(){

    }

    private void listTextView(){
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        inflateLayout();
        Toast.makeText(getActivity(), view.getId() + "", Toast.LENGTH_LONG).show(); //Toast.makeText(getActivity(), view.getId() + view.getId(), Toast.LENGTH_LONG).show();
        switch (view.getId()){
            case R.id.category_spinner:
                String item = adapterView.getSelectedItem().toString();
                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
                if(adapterView.getSelectedItem().toString().equals("Cars")){
                    inflateLayout();
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
