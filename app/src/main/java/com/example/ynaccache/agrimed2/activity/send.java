package com.example.ynaccache.agrimed2.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class send extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final Button AuthBtn = (Button) findViewById(R.id.signin);
        AuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("pseudo","you"));
                nameValuePairs.add(new BasicNameValuePair("mdp","lll"));
                nameValuePairs.add(new BasicNameValuePair("mail","kkk"));
                // nameValuePairs.add(new BasicNameValuePair("tel",tel));

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://10.0.3.3/TripTun/inscription.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();



                }catch(ClientProtocolException e){
                    Log.e("ClientProtocol","Log_tag");
                    e.printStackTrace();
                }catch(IOException e) {
                    Log.e("Log_tag", "IOException");
                    e.printStackTrace();
                }
            }


                    //sendLoginForm();




        });

    }
    void sendLoginForm() {

        JSONObject joUser = new JSONObject();
        try {
            joUser.put("CLIENTLEVEL", 65);
            joUser.put("CLIENTNAME","youssef" );

            new AsynckLogin(joUser).execute();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public class AsynckLogin extends AsyncTask<Void, Integer, Void> {

        JSONObject joUser;

        JSONObject joResult;


        public AsynckLogin(JSONObject joUser) {
            // TODO Auto-generated constructor stub
            this.joUser = joUser;
        }


        protected Void doInBackground(Void... arg0) {

            joResult = User.loginUser(send.this, joUser);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.e("result", joResult.toString());
            Toast.makeText(getApplicationContext(), joResult.toString(), Toast.LENGTH_SHORT).show();






            // progressBar1.setVisibility(View.GONE);

        }
    }
}
