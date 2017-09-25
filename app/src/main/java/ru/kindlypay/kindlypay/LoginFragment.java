package ru.kindlypay.kindlypay;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by User on 21.09.2017.
 */

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private User mUser;
    private EditText mPhone,mPassword;
    private Button mLogin;
    private TextView mRegistration;
    private boolean mEndSiteTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser=new User();


    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.fragment_login,container,false);

        mPhone=(EditText)v.findViewById(R.id.user_phone);
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setPhoneNumber(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassword=(EditText)v.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setPassword(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLogin=(Button)v.findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mEndSiteTransaction=false;
                    new FetchItemsTask().execute();
                    while (!mEndSiteTransaction){

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mRegistration=(TextView)v.findViewById(R.id.registration_link);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Нажата кнопка");
            }
        });

        return v;

    }

    private class FetchItemsTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String result = new KindlyPayFetchr()
                        .getUserId(mUser,getContext().getString(R.string.login));
                mEndSiteTransaction=true;
                Log.i(TAG, "Fetched contents of URL: " + result);
            } catch (IOException ioe) {
                Toast.makeText(getContext(),R.string.no_connection,Toast.LENGTH_SHORT).show();
                mEndSiteTransaction=true;
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }

}
