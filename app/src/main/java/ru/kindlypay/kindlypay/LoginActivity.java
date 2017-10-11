package ru.kindlypay.kindlypay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private TextView mRegistration;
    private boolean isNotConnection;
    private LinearLayout.LayoutParams paramWrongPhone,paramErrorLogin;

    private FetchItemsTask conn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mUser=new User();

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
                    Log.i(TAG,"WrongPhone is Invisible");
                    mUser.setPhoneNumber(phone);
                    if (mUser.getPassword()!=null&&mUser.getPassword().length()>5)
                        mLogin.setEnabled(true);

                }else{
                    Log.i(TAG,"WrongPhone is Visible");
                    mPhone.setError(getString(R.string.incorrect_phone));
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

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (conn!=null)
                        return;
                    conn=new FetchItemsTask();
                    conn.execute();
//                    while (conn.getStatus()!= AsyncTask.Status.FINISHED){
                    while (conn!= null){

                    }
                    if (isNotConnection) {
                        Toast.makeText(LoginActivity.this,R.string.no_connection, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Нет соединения ");
                        return;
                    }
                    if (mUser.getId()==0){
                        mPhone.setError(getString(R.string.error_login));
                        mPassword.setError(getString(R.string.error_login));
                    }else
                    {
                        Log.i(TAG,"Start user activity");
                        Intent intent =new Intent(LoginActivity.this,UserPage.class);
                        startActivity(intent);
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

    private class FetchItemsTask  extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String result = new KindlyPayFetchr()
                        .getUserId(mUser,getString(R.string.login));
                isNotConnection=false;
                Log.i(TAG, "Fetched contents of URL: " + result);

            } catch (IOException ioe) {
                isNotConnection=true;
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            conn = null;
//            showProgress(false);

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            conn = null;
//            showProgress(false);
        }

    }

}
