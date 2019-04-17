package com.hyperdesign.alabbadauto.fragments.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperdesign.alabbadauto.R;
import com.hyperdesign.alabbadauto.activities.SignupAct;
import com.hyperdesign.alabbadauto.api.model.SignInResponse;
import com.hyperdesign.alabbadauto.api.service.ApiClient;
import com.hyperdesign.alabbadauto.api.service.ApiInterface;
import com.hyperdesign.alabbadauto.classes.user.SQLiteHandler;
import com.hyperdesign.alabbadauto.classes.user.SessionManager;
import com.hyperdesign.alabbadauto.classes.user.User;
import com.hyperdesign.alabbadauto.classes.user.UserLocalStore;
import com.hyperdesign.alabbadauto.ui.MainActivity;

import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.hyperdesign.alabbadauto.fragments.registration.SignUp.validateEmail;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link SignInFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFrag extends Fragment
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
    //private PlusOneButton mPlusOneButton;

    private TextView tvSignUp;

    private EditText etEmail, etPass;

    private Button btnSignIn;

    private User user;

    private String message;

    private Boolean success;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private OnFragmentInteractionListener mListener;

    private SessionManager session;
    private SQLiteHandler db;

    String email, pass;

    UserLocalStore userLocalStore;

    private Switch aSwitch;

    Locale myLocale;

    SharedPreferences settings;

    private RadioGroup radioGroup;
    private RadioButton rbEng, rbArab;

    public SignInFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFrag newInstance(String param1, String param2) {
        SignInFrag fragment = new SignInFrag();
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        // SQLite database handler
        db = new SQLiteHandler(getContext());

        // Session manager
        session = new SessionManager(getContext());

        userLocalStore = new UserLocalStore(getContext());

        System.out.println("Hi" + userLocalStore.getLoggedInUser().getEmail());

        etEmail = (EditText) getActivity().findViewById(R.id.etEmailIn);
        etPass = (EditText) getActivity().findViewById(R.id.etPassword);

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        //getUserData(userLocalStore.getLoggedInUser());

        //Find the +1 button
        //mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = (EditText) getActivity().findViewById(R.id.etEmailIn);
        etPass = (EditText) getActivity().findViewById(R.id.etPassword);
        btnSignIn = (Button) getActivity().findViewById(R.id.btnSignIn);
        tvSignUp = (TextView) getActivity().findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);

        rbEng = (RadioButton) getActivity().findViewById(R.id.rbEng);
        rbEng.setOnClickListener(this);
        rbArab = (RadioButton) getActivity().findViewById(R.id.rbArab);
        rbArab.setOnClickListener(this);

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
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();

        switch (id){
            case R.id.tvSignUp:
                Intent intent = new Intent(this.getActivity(), SignupAct.class);
                startActivity(intent);
                break;
            case R.id.btnSignIn:
                if(email.equals("")){
                    etEmail.setError(getString(R.string.error_field_required));
                } else if(!validateEmail(email)){
                    etEmail.setError(getString(R.string.error_invalid_email));
                } else if (pass.equals("")){
                    etPass.setError(getString(R.string.error_field_required));
                } else if(!isPasswordValid(pass)){
                    etPass.setError(getString(R.string.error_incorrect_password));
                } else {
                    getSignInUser();
                }
                break;
            case R.id.rbEng:
                setLocaleLanguage("en");
                break;
            case R.id.rbArab:
                setLocaleLanguage("ar");
                break;

        }
    }

    public void setLocaleLanguage(String lang){
        /*myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Settings.class);
        startActivity(refresh);*/


        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
        session.setLanguage(lang);

        //db.addLanguage(lang);

        settings = getActivity().getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        settings.edit().putString("locale", lang).commit();
        this.getActivity().finish();
        Intent refresh = new Intent(this.getActivity(), MainActivity.class);
        startActivity(refresh);
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

        Call<SignInResponse> call = apiService.getSignInUser( etEmail.getText().toString(),
                etPass.getText().toString());

        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                user = response.body().getUser();
                message = response.body().getMessage();
                success = response.body().getSuccess();

                //Log.d(TAG , "Number of movies received: " + user.getName());

                if(message.equals("Check your email")){
                    checkEmail();
                } else if(message.equals("Check your password")){
                    checkPassword();
                } else {
                    successSignIn();
                }

            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Log.e(TAG, "error");
                Toast.makeText(getContext(), "error :(", Toast.LENGTH_LONG).show();
            }

        });
    }

    //Handle Valid Password
    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    //Handle Invalid Email
    private void checkEmail(){
        Toast.makeText(getContext(), "Check Your Email", Toast.LENGTH_LONG).show();
    }

    //Handle Invalid Email
    private void checkPassword(){
        Toast.makeText(getContext(), "Check Your Password", Toast.LENGTH_LONG).show();
    }

    //Success Sign Up
    private void successSignIn(){

        user = new User(user.getId(), user.getName(),
                user.getEmail());
        /*session.setLogin(true);

        db.addUser(user.getName(),
                user.getEmail(), String.valueOf(user.getId()),
                user.getCreatedAt());*/

        userLocalStore.setUserLoggedIn(true);
        userLocalStore.storedUserData(user);

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

        Toast.makeText(getContext(), "Sign In Successful", Toast.LENGTH_LONG).show();
    }

}
