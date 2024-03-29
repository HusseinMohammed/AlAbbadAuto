package com.hyperdesign.alabbadauto.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.api.model.TenderMessageResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tender.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tender#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tender extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText etTenderMessage;

    Button btnSendMessage;

    String tenderMessage;

    UserLocalStore userLocalStore;

    String message;

    public Tender() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tender.
     */
    // TODO: Rename and change types and number of parameters
    public static Tender newInstance(String param1, String param2) {
        Tender fragment = new Tender();
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
        etTenderMessage = (EditText) getActivity().findViewById(R.id.etSendMessage);
        btnSendMessage = (Button) getActivity().findViewById(R.id.btnSendMessage);
        userLocalStore = new UserLocalStore(getContext());
        return inflater.inflate(R.layout.fragment_tender, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTenderMessage = (EditText) getActivity().findViewById(R.id.etSendMessage);
        btnSendMessage = (Button) getActivity().findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(this);

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
        int position = view.getId();
        message = etTenderMessage.getText().toString();

        switch (position){
            case R.id.btnSendMessage:
                if(message.equals("")){
                    etTenderMessage.setError(getString(R.string.error_field_required));
                } else {
                    getSignInUser();
                }
                break;
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

    //TODO First Function to call Api(Post User Sign Up)
    private void getSignInUser(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<TenderMessageResponse> call = apiService.sendTenderMessage(userLocalStore.getLoggedInUser().getId(),
                etTenderMessage.getText().toString());

        call.enqueue(new Callback<TenderMessageResponse>() {
            @Override
            public void onResponse(Call<TenderMessageResponse> call, Response<TenderMessageResponse> response) {
                tenderMessage = response.body().getMessage();

                Log.d(TAG , "Number of movies received: " + tenderMessage);

                Toast.makeText(getContext(), tenderMessage, Toast.LENGTH_LONG).show();
                etTenderMessage.getText().clear();

            }

            @Override
            public void onFailure(Call<TenderMessageResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }

        });
    }

}
