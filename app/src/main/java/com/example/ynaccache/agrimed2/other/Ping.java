package com.example.ynaccache.agrimed2.other;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ynaccache.agrimed2.activity.Login;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by y.naccache on 21/12/2017.
 */

public  class Ping {

    public static Boolean testconwamp()
    {
        Boolean  test=false;
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                    Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
                }
            });
        }*/
        try
        {
            HttpURLConnection conn = (HttpURLConnection) (new URL("http://192.168.100.100:8181/"))
                    .openConnection();
            conn.setConnectTimeout(1000);
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();
              System.out.println(String.valueOf(status)+"connexion wamap");

            conn.disconnect();
            test=true;

        }catch (ConnectException e) {
            System.out.println("echec");

        }
        catch (Exception e) {
            System.out.println("echec1");


        }



        return  test   ;
    }

    public static Boolean testconappache()
    {
        Boolean  test=false;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try
        {
            HttpURLConnection conn = (HttpURLConnection) (new URL("http://192.168.100.100:8080/"))
                    .openConnection();
            conn.setConnectTimeout(1000);
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();
            System.out.println(String.valueOf(status)+"connexion appache");

            conn.disconnect();
            test=true;

        }catch (ConnectException e) {
            System.out.println("echec apache");
        }
        catch (Exception e) {
            System.out.println("echec de apache "+e.getMessage());


        }



        return  test   ;
    }




}
