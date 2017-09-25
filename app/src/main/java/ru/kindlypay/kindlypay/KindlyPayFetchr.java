package ru.kindlypay.kindlypay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

/**
 * Created by User on 22.09.2017.
 */

public class KindlyPayFetchr {

    private String getDataFromServer (String mUrl, Map<String,Object> params) throws IOException{
        URL url = new URL(mUrl);
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        conn.disconnect();
        return sb.toString();
    }

    public String getUserId(User mUser,String mUrl) throws IOException {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("login", mUser.getPhoneNumber());
        params.put("login_password", mUser.getPassword());
        return getDataFromServer(mUrl,params);
    }


}
