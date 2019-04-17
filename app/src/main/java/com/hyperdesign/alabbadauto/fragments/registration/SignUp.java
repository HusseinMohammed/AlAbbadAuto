package com.hyperdesign.alabbadauto.fragments.registration;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.plus.PlusOneButton;
import com.hyperdesign.alabbadauto.activities.SignInAct;
import com.hyperdesign.alabbadauto.api.model.SignUpResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.user.User;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link SignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUp extends Fragment
        implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;

    private OnFragmentInteractionListener mListener;

    private ArrayList<User> users;

    private User user;

    private String message;

    private Boolean success;

    private EditText etFullName, etEmail, etPassword, etConfirm;

    private Button signUp;

    String fullName, email, password, confirmPass;

    UserLocalStore userLocalStore;



    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final String TEST_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    public SignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUp newInstance(String param1, String param2) {
        SignUp fragment = new SignUp();
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
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        etFullName = (EditText) getActivity().findViewById(R.id.etFullName);
        etEmail = (EditText) getActivity().findViewById(R.id.etEmail);
        etPassword = (EditText) getActivity().findViewById(R.id.etPassword);
        signUp = (Button) getActivity().findViewById(R.id.btnSignUp);
        userLocalStore = new UserLocalStore(getContext());

        //signUp.setOnClickListener(this);

        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignUpUser();
            }

        });*/

        //Find the +1 button
        //mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFullName = (EditText) getActivity().findViewById(R.id.etFullName);
        etEmail = (EditText) getActivity().findViewById(R.id.etEmail);
        etPassword = (EditText) getActivity().findViewById(R.id.etPassword);
        etConfirm = (EditText) getActivity().findViewById(R.id.etConfirmPassword);
        signUp = (Button) getActivity().findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        //mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
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
        fullName = etFullName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        confirmPass = etConfirm.getText().toString();

        switch (id){
            case R.id.btnSignUp:
                if (fullName.equals("")){
                    etFullName.setError(getString(R.string.error_field_required));
                } else if(email.equals("")){
                    etEmail.setError(getString(R.string.error_field_required));
                } else if(!validateEmail(email)){
                    etEmail.setError(getString(R.string.error_invalid_email));
                } else if (password.equals("")){
                    etPassword.setError(getString(R.string.error_field_required));
                } else if(!isPasswordValid(password)){
                    etPassword.setError(getString(R.string.error_incorrect_password));
                } else if (confirmPass.equals("")){
                    etConfirm.setError(getString(R.string.error_field_required));
                } else if (!checkConfirmPass()){
                    etConfirm.setError(getString(R.string.error_incorrect_password));
                } else {
                    getSignUpUser();
                }

        }
    }

    private boolean checkConfirmPass() {
        if(confirmPass.equals(password)){
            return true;
        }
        return false;
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
    private void getSignUpUser(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SignUpResponse> call = apiService.getSignUpUser(etFullName.getText().toString(), etEmail.getText().toString(),
                etPassword.getText().toString());

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                user = response.body().getUser();
                message = response.body().getMessage();
                success = response.body().getSuccess();
                if(message.equals("E-Mail token")){
                    existedEmail();
                } else{
                    successSignUp();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }

        });
    }

    //Email Existed
    private void existedEmail(){
        Toast.makeText(getContext(), "Email Existed", Toast.LENGTH_LONG).show();
    }

    //Success Sign Up
    private void successSignUp(){
        /*user = new User(user.getId(), user.getName(),
                user.getEmail(), user.getType());*/

        user = new User(user.getEmail());
        userLocalStore.storedUserData(user);
        userLocalStore.setUserLoggedIn(true);

        Intent intent = new Intent(getContext(), SignInAct.class);
        startActivity(intent);
        Toast.makeText(getContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
    }

    //Handle Success Email
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //Handle Valid Password
    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isConfirmPassValid(String confirm, String password) {
        //TODO: Replace this with your own logic
        //for(String )
        return confirm.contains("@");
    }

}
