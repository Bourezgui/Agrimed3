package com.example.ynaccache.agrimed2.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.fragment.PhotosFragment;
import com.example.ynaccache.agrimed2.fragment.SocialFragment;
import com.example.ynaccache.agrimed2.fragment.client;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.commandeRepo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.ynaccache.agrimed2.fragment.SocialFragment.req3;
import static com.example.ynaccache.agrimed2.fragment.client.dateliv;

public class synchronisation extends AppCompatActivity {
    ArrayList<HashMap<String, String>> siteList;
    String jour, mois, annee;
    String jour1, mois1, annee1;
    boolean haveConnectedvpn = false;
    String req5;

public static Boolean testcon()
{
  Boolean  test=false;
    if (android.os.Build.VERSION.SDK_INT > 9) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    try
    {
        HttpURLConnection conn = (HttpURLConnection) (new URL("http://192.168.100.100:8181/"))
                .openConnection();
        conn.setConnectTimeout(1000);
        conn.setUseCaches(false);
        conn.connect();
        int status = conn.getResponseCode();
      //  System.out.println(String.valueOf(status));

        conn.disconnect();
        test=true;

    }catch (ConnectException e) {
        //System.out.println("echec");
    }
    catch (Exception e) {
        //System.out.println("jjj");


    }



 return  test   ;
}



    private final Context mContext = this;
    private int mStatusCode = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread timer = new Thread(){
            @Override
            public void run(){
                boolean test =false;

                try{

                    sleep(3000);// durree  est égale a 5 seconde
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    try
                    {
                        HttpURLConnection conn = (HttpURLConnection) (new URL("http://192.168.100.100:8080/"))
                                .openConnection();
                        conn.setConnectTimeout(3000);
                        conn.setUseCaches(false);
                        conn.connect();
                        int status = conn.getResponseCode();
                          System.out.println(String.valueOf(status));

                        conn.disconnect();

                    }catch (ConnectException e) {
                        System.out.println("echec");
                    }
                    catch (Exception e) {
                        System.out.println("jjj");


                    }




                }
            }
        };
        timer.start();//


    }
}



