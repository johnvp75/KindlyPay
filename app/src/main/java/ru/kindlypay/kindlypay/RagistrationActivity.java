package ru.kindlypay.kindlypay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by User on 25.09.2017.
 */

public class RagistrationActivity extends AppCompatActivity {

    private User mUser;
    private TextView mPasswordNotMatch,PasswordNotStrong;
    private EditText mPhone,mPassword,mRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    
    }

}
