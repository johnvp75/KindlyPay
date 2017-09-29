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
    private TextView mRegistration,mErrorLogin,mWrongPhone;
    private boolean isNotConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mUser=new User();


        mWrongPhone=(TextView)findViewById(R.id.login_incorrect_phone);
        mLogin=(Button)findViewById(R.id.login_button);
        mPhone=(EditText)findViewById(R.id.login_user_phone);
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone=ServiceMethods.correctPhone(s.toString());
                if (phone!=null){
                    mUser.setPhoneNumber(phone);
                    if (mUser.getPassword()!=null&&mUser.getPassword().length()>5)
                        mLogin.setEnabled(true);
                    mWrongPhone.setVisibility(TextView.INVISIBLE);
                }else{
                    mWrongPhone.setVisibility(TextView.VISIBLE);
                    mLogin.setEnabled(false);
                }


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
                if (s.toString().length()>5&&mUser.getPhoneNumber()!=null) {
                    mLogin.setEnabled(true);
                }else{
                    mLogin.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mErrorLogin=(TextView)findViewById(R.id.error_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mErrorLogin.setVisibility(TextView.INVISIBLE);
                    FetchItemsTask conn=new FetchItemsTask();
                    conn.execute();
                    while (conn.getStatus()!= AsyncTask.Status.FINISHED){

                    }
                    if (isNotConnection) {
                        Toast.makeText(LoginActivity.this,R.string.no_connection, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Нет соединения ");
                        return;
                    }
                    if (mUser.getId()==0){
                        mErrorLogin.setVisibility(TextView.VISIBLE);
                    }else
                    {

                    }

                } catch (Exception e) {
                    Log.e(TAG, "Ошибка запуска потока ", e);
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
                isNotConnection=false;
                Log.i(TAG, "Fetched contents of URL: " + result);

            } catch (IOException ioe) {
                isNotConnection=true;
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }

}
