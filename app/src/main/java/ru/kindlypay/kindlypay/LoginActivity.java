package ru.kindlypay.kindlypay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by User on 21.09.2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private User mUser;
    private EditText mPhone,mPassword;
    private Button mLogin;
    private TextView mRegistration,mErrorLogin;
    private boolean mEndSiteTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mUser=new User();



        mPhone=(EditText)findViewById(R.id.login_user_phone);
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
        mPassword=(EditText)findViewById(R.id.login_password);
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
        mLogin=(Button)findViewById(R.id.login_button);
        mErrorLogin=(TextView)findViewById(R.id.error_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mEndSiteTransaction=false;
                    new FetchItemsTask().execute();
                    while (!mEndSiteTransaction){

                    }
                    if (mUser.getId()==0){
                        mErrorLogin.setVisibility(TextView.VISIBLE);
                    }

                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this,R.string.no_connection, Toast.LENGTH_SHORT);
                    Log.e(TAG, "Return with error ", e);
                    e.printStackTrace();
                }
            }
        });
        mRegistration=(TextView)findViewById(R.id.registration_link);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Нажата кнопка");
                Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });



    }

    private class FetchItemsTask  extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String result = new KindlyPayFetchr()
                        .getUserId(mUser,getString(R.string.login));
                mEndSiteTransaction=true;
                Log.i(TAG, "Fetched contents of URL: " + result);
            } catch (IOException ioe) {
                mEndSiteTransaction=true;
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }

}
