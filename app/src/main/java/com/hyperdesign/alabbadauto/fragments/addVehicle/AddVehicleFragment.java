package com.hyperdesign.alabbadauto.fragments.addVehicle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.AddPriceActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVehicleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVehicleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVehicleFragment extends Fragment
        implements View.OnClickListener {

    private ImageView addVehicleImage;

    private EditText vehicleName, vehicleDescription;

    //Define and Initialize array of string(COUNTRIES)
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Egypt", "Russia", "Brazil", "Japan", "China"
    };

    //Define ArrayAdapter to handle Category, Brands, Model, Year and Status spinners
    private ArrayAdapter<String>
            adapter;

    //Define BetterSpinner of Category, Brands, Model, Year, Status(Yes, No)
    private MaterialBetterSpinner
            textViewCategory, textViewBrands,
            textViewModel, textViewYear, textViewStatus;

    //Define RadioGroup
    private RadioGroup radioGroup;
    //Define RadioButton
    private RadioButton newRadioButton, usedRadioButton;

    //Define Search Button
    private Button nextToPrice;

    //Define View
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int RESULT_LOAD_IMG = 111; //fragment result load 111
    //private ImageView imageViewVehicle, imageViewShowroom;
    String encodedString;
    String imgPath, fileName;
    Bitmap bitmap;

    //Define fragment
    Fragment fragment;

    /*CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
*/
    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 22131;

    private EditText textViewShowroomName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVehicleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVehicleFragment newInstance(String param1, String param2) {
        AddVehicleFragment fragment = new AddVehicleFragment();
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
        view = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        addVehicleImage = (ImageView) getActivity().findViewById(R.id.addVehicleImage);

        vehicleName = (EditText) getActivity().findViewById(R.id.vehicleName);
        vehicleDescription = (EditText) getActivity().findViewById(R.id.vehicleDescription);

        textViewCategory = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.category_spinner);
        textViewCategory.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewBrands = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.brands_spinner);
        textViewBrands.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewModel = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.model_spinner);
        textViewModel.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewYear = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.year_spinner);
        textViewYear.setAdapter(adapter);

        //Initialize Better Spinner Of brands_spinner
        textViewStatus = (MaterialBetterSpinner)
                getActivity().findViewById(R.id.status_spinner);
        textViewStatus.setAdapter(adapter);

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radio_type_search);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.radio_new:
                        Toast.makeText(getContext(), "New Vehicle", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.radio_used:
                        Toast.makeText(getContext(), "Used Vehicle", Toast.LENGTH_LONG).show();
                        break;
                    default:

                }
            }
        });

        newRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_new);
        usedRadioButton = (RadioButton) getActivity().findViewById(R.id.radio_used);

        addVehicleImage = (ImageView) getActivity().findViewById(R.id.addVehicleImage);

        addVehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                //startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        nextToPrice = (Button) getActivity().findViewById(R.id.nextToPrice);

        nextToPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*fragment = new AddPriceFragment();
                loadFragment(fragment);*/
                Intent intent = new Intent(getActivity(), AddPriceActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                addVehicleImage.setImageBitmap(selectedImage);


                Log.d(TAG, imageToString(selectedImage));


                /*Intent intent = new Intent();
                intent.putExtra("gallery", "success");*/
                /*fragment = new AddVehicleFragment();
                loadFragment(fragment);*/

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this.getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this.getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.your_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.addVehicleImage:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
            default:
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
