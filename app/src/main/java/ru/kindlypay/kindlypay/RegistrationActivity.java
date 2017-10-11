package ru.kindlypay.kindlypay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 25.09.2017.
 */

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG ="RegistrationActivity";

    private User mUser;
    private EditText mPhone,mPassword,mRepeatPassword;
    private Button mRegistrationButton;
    private boolean isStrongPassword,isMatchedPassword,isCorrectPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mUser=new User();

        mRegistrationButton=(Button)findViewById(R.id.registration_button);
        mPhone=(EditText)findViewById(R.id.registration_user_phone);
        mPassword=(EditText)findViewById(R.id.registration_password);
        mRepeatPassword=(EditText)findViewById(R.id.registration_repeat_password);

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mPhoneTemp=ServiceMethods.correctPhone(s.toString());
                if (mPhoneTemp==null){
                    Log.i(TAG,"Проверка телефона не прошла");
                    isCorrectPhone=false;
                    mPhone.setError(getString(R.string.incorrect_phone));
                }
                else {
                    Log.i(TAG,"Проверка телефона прошла");
                    isCorrectPhone=true;
                    mUser.setPhoneNumber(mPhoneTemp);
                }
                checkStateForButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isStrongPassword=ServiceMethods.strongPassword(s.toString());
                if (isStrongPassword){
                    Log.i(TAG,"Проверка пароля прошла");
                    mUser.setPassword(s.toString());
                    if (mRepeatPassword.getText().toString().length()>0){
                        if(s.toString().equals(mRepeatPassword.getText().toString())){
                            isMatchedPassword=true;
                        }else{
                            isMatchedPassword=false;
                        }
                    }
                }else{
                    Log.i(TAG,"Проверка пароля не прошла");
                    mPassword.setError(getString(R.string.rule_for_password));
                }
                checkStateForButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRepeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUser.getPassword().equals(s.toString())){
                    Log.i(TAG,"Сравнение паролей прошло");
                    isMatchedPassword=true;
                }else {
                    Log.i(TAG,"Сравнение паролей не прошло");
                    isMatchedPassword=false;
                    mRepeatPassword.setError(getString(R.string.bad_repeat_password));

                }

                checkStateForButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkStateForButton() {
        if (isStrongPassword&&isMatchedPassword&&isCorrectPhone){
            Log.i(TAG,"Кнопка активирована");
            mRegistrationButton.setEnabled(true);
        }else{
            Log.i(TAG,"Кнопка деактивирована");
            mRegistrationButton.setEnabled(false);
        }

    }

}
