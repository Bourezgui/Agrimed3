package com.example.ynaccache.agrimed2.activity;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class User {

    private int id;
    private String authority;
    private String token;


    public static JSONObject loginUser(Context context, JSONObject obj) {
        InputStream in = null;
        String result = "";

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);

        String link = "http://192.168.2.39:8124/sdata/x3/erp/SEED/ZCLIENT?representation=ZCLIENT.$edit";
        result = POST(link.replace(" ", "%20"), obj);

        JSONObject on_results = null;
        try {
            on_results = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return on_results;

    }


    public static String POST(String url, JSONObject jsonObject) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            Log.e("url", url);
            Log.e("jsonObject", jsonObject.toString());

            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin
            // Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json, "UTF-8");

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the
            // content
            String basicAuth = "Basic YWRtaW46YWRtaW4=";
           httpPost.addHeader("Authorization", basicAuth);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
    
    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User(int id, String authority, String token) {
        this.id = id;
        this.authority = authority;
        this.token = token;
    }
}
