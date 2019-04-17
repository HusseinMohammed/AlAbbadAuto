package com.hyperdesign.alabbadauto.fragments.insurance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.InsuranceDetailsActivity;
import com.hyperdesign.alabbadauto.activities.SignInAct;
import com.hyperdesign.alabbadauto.api.model.sendMessage.SendInsMessage;
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
 * {@link SendRequestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendRequestFragment extends Fragment
             implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Define toolbar
    Toolbar toolbar;

    //Define fragment
    Fragment fragment;

    private TextView mEmail;

    private EditText mMessage, mPhone;

    private Button mSendRequest, mSignIn;

    private ImageView mBack;

    int id;

    UserLocalStore userLocalStore;

    ConstraintLayout constraintLayout;

    public SendRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendRequestFragment newInstance(String param1, String param2) {
        SendRequestFragment fragment = new SendRequestFragment();
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
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        Log.d("id", id + "");
        return inflater.inflate(R.layout.fragment_send_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintLayout = (ConstraintLayout) getActivity().findViewById(R.id.clSendInsMessage);

        //mEmail = (TextView) getActivity().findViewById(R.id.recipientMail);

        mMessage = (EditText) getActivity().findViewById(R.id.editMessage);

        mPhone = (EditText) getActivity().findViewById(R.id.etPhone);

        mSendRequest = (Button) getActivity().findViewById(R.id.btnSendRequest);
        mSendRequest.setOnClickListener(this);

        mBack = (ImageView) getActivity().findViewById(R.id.back);
        mBack.setOnClickListener(this);

        mSignIn = (Button) getActivity().findViewById(R.id.btnSignIn);
        mSignIn.setOnClickListener(this);

        userLocalStore = new UserLocalStore(getContext());

        checkSignInUser();

    }

    private void checkSignInUser() {
        if(!userLocalStore.getUserLoggedIn()){
            constraintLayout.setVisibility(View.INVISIBLE);
            mSignIn.setVisibility(View.VISIBLE);
        }
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
        switch (position){
            case R.id.btnSendRequest:
                /*toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new InsuranceDetailsFragment();
                loadFragment(fragment);*/
                sendInsMessage();
                Intent intent = new Intent(getActivity(), InsuranceDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.back:
                intent = new Intent(getActivity(), InsuranceDetailsActivity.class);
                startActivity(intent);
                /*toolbar.setTitle(getResources().getString(R.string.title_search));
                fragment = new InsuranceDetailsFragment();
                loadFragment(fragment);*/
                break;
            case R.id.btnSignIn:
                intent = new Intent(getActivity(), SignInAct.class);
                startActivity(intent);
        }
    }

    private void loadFragment(Fragment fragment) {
        fragment = new InsuranceDetailsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.your_placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

    //TODO SEND MESSAGE INSURANCE
    private void sendInsMessage(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SendInsMessage> call = apiService.sendInsMessage(userLocalStore.getLoggedInUser().getId(), id
                , mPhone.getText().toString(), mMessage.getText().toString());
        call.enqueue(new Callback<SendInsMessage>() {
            @Override
            public void onResponse(Call<SendInsMessage> call, Response<SendInsMessage> response) {

                String done = response.body().getDone();
                String message = response.body().getMessage();

                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SendInsMessage> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }
        });
    }

}
