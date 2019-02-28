package com.example.ynaccache.agrimed2.activity;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.widget.TextView;
import android.provider.Settings.Secure;


import com.example.ynaccache.agrimed2.R;

import static java.security.AccessController.getContext;

public class numserie extends AppCompatActivity {
    TextView txt1;
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numserie);
        txt1=(TextView)findViewById(R.id.txt1);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        // TODO Auto-generated method stub

        IMEI_Number_Holder = telephonyManager.getDeviceId();

        txt1.setText(IMEI_Number_Holder);
    }
}
