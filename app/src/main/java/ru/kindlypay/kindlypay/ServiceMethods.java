package ru.kindlypay.kindlypay;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 26.09.2017.
 */

public class ServiceMethods {

    private static final String TAG ="ServiceMethods";

    public static String correctPhone(String inputPhone){
        Log.i(TAG,"Полученный номер телефона"+inputPhone);
        inputPhone=inputPhone.replaceAll("-|\\(|\\)","");
        Log.i(TAG,"Преобразованный номер телефона"+inputPhone);
        Pattern p = Pattern.compile("^\\+7\\d{10}$");
        Matcher m = p.matcher(inputPhone);
        if (m.matches()) {
            Log.i(TAG,"Номер телефона корректный");
            return inputPhone;
        }
        else {
            Log.i(TAG,"Номер телефона некорректный");
            return null;
        }
    }

    public static boolean strongPassword(String password){
        Log.i(TAG,"Получен пароль:"+password);
        Pattern p = Pattern.compile("^(?=.*\\d)((?=.*[a-z])|(?=.*[а-я]))((?=.*[A-Z])|(?=.*[А-Я])).{6,}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
