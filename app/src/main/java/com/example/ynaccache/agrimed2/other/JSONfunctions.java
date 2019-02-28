package com.example.ynaccache.agrimed2.other;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by y.naccache on 27/10/2017.
 */

public class JSONfunctions {

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String output=null;
        String result = "";
        JSONObject jArray = null;

        // Download JSON data from URL
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            //////
            String username = "admin";
            String password = "admin";
            String userPassword = username + ":" + password;
            // Encoder encoder = Base64.getEncoder();
            // byte[] src = userPassword.getBytes();


            //String encoding = encoder.encodeToString(src);
            String basicAuth = "Basic "+"V0VCU1JWOldFQlNSVg==";
            conn.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4=");
            //////
           /* if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
        }*/

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            StringBuilder sb  = new StringBuilder();


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                // System.out.println(output);

                sb.append(output + "\n");


            }
            result = sb.toString();
            Log.i("result",result);

            conn.disconnect();


        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }
}
