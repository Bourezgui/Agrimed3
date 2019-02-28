package com.example.ynaccache.agrimed2.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.activity.views.DotsProgressBar;
import com.example.ynaccache.agrimed2.fragment.NetworkStateChecker;

public class SplashScreen extends AppCompatActivity {
    private DotsProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        initView();
    }
    private void initView() {
        progressBar = (DotsProgressBar) findViewById(R.id.dotsProgressBar);
        progressBar.setDotsCount(5);

        DownloadTask task = new DownloadTask();
        task.execute();
    }
    class DownloadTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            progressBar.start();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                progressBar.stop();
                progressBar.setVisibility(View.INVISIBLE);

                finish();
                startActivity(new Intent(SplashScreen.this,
                       Login.class));
            }
        }


    }
}
