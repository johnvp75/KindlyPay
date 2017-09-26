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
    private TextView mPasswordNotMatch,mPasswordNotStrong,mIncorrectPhone;
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
        mIncorrectPhone=(TextView)findViewById(R.id.registration_incorrect_phone);
        mPassword=(EditText)findViewById(R.id.registration_password);
        mPasswordNotStrong=(TextView)findViewById(R.id.registration_password_rule);
        mRepeatPassword=(EditText)findViewById(R.id.registration_repeat_password);
        mPasswordNotMatch=(TextView)findViewById(R.id.registration_password_not_match);

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
                    mIncorrectPhone.setVisibility(TextView.VISIBLE);
                }
                else {
                    Log.i(TAG,"Проверка телефона прошла");
                    mIncorrectPhone.setVisibility(TextView.INVISIBLE);
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
                    mPasswordNotStrong.setVisibility(TextView.INVISIBLE);
                    if (mRepeatPassword.getText().toString().length()>0){
                        if(s.toString().equals(mRepeatPassword.getText().toString())){
                            isMatchedPassword=true;
                            mPasswordNotMatch.setVisibility(TextView.INVISIBLE);
                        }else{
                            isMatchedPassword=false;
                        }
                    }
                }else{
                    Log.i(TAG,"Проверка пароля не прошла");
                    mPasswordNotStrong.setVisibility(TextView.VISIBLE);
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
                    mPasswordNotMatch.setVisibility(TextView.INVISIBLE);
                }else {
                    Log.i(TAG,"Сравнение паролей не прошло");
                    isMatchedPassword=false;
                    mPasswordNotMatch.setVisibility(TextView.VISIBLE);

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
