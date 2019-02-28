package com.example.ynaccache.agrimed2.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.fragment.NetworkStateChecker;
import com.example.ynaccache.agrimed2.fragment.SocialFragment;
import com.example.ynaccache.agrimed2.fragment.client;
import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.model.user;
import com.example.ynaccache.agrimed2.other.AlertDialogManager;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.ClientRepo;
import com.example.ynaccache.agrimed2.sqlite.UserRepo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {
    /////////////// VPN//////////////
    NetworkCapabilities caps1,caps2,caps3;
    ///////////



    /////////////////////
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //n est pas utilisé
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    ProgressDialog barProgressDialog;
    public EditText ed1, ed2;
    String login, pass;
    public ProgressDialog progressDialog1;
    AlertDialogManager alert = new AlertDialogManager();
    public String code;
    String IMEI_Number_Holder;
    TelephonyManager telephonyManager;
    public static String idd, log, mdp, etat, numsite, codeutulisateur, imei, nom, prenom, appareil, prefix;
    public static String logoff, mdpoff, etatoff, numsiteoff, codeutulisateuroff, imeioff, nomoff, prenomoff, appareiloff, prefixoff;
    int _user_Id = 0;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    @TargetApi(Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        ed1 = (EditText) findViewById(R.id.email);
        ed2 = (EditText) findViewById(R.id.password);
        SocialFragment.list.clear();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

                // TODO Auto-generated method stub
                IMEI_Number_Holder = telephonyManager.getDeviceId();

            }
            /*if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

                // TODO Auto-generated method stub
                IMEI_Number_Holder = telephonyManager.getDeviceId();
            }*/
        } else {

            telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

            // TODO Auto-generated method stub

            IMEI_Number_Holder = telephonyManager.getDeviceId();
            // do something for phones running an SDK before lollipop
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button connexion = (Button) findViewById(R.id.conect);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = ed1.getText().toString();
                pass = ed2.getText().toString();

                //Toast.makeText(getApplicationContext(), IMEI_Number_Holder, Toast.LENGTH_LONG).show();

               if ((login.isEmpty() || pass.isEmpty())) {
                    ed1.setError("Merci de remplir  ce champ");
                    ed2.setError("Merci de remplir  ce champ");
                    //Toast.makeText(getApplicationContext(), "SVP, Remplir tous les champs", Toast.LENGTH_LONG).show();


                } else {
                    SocialFragment.list.clear();

                   if (haveNetworkConnection())
                    {

                           new AsynckLogin().execute();


                    } else
                   {
                       //System.out.print("echec1");
                       UserRepo repo = new UserRepo(getApplicationContext());
                       ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
                       if (siteList.size()==0)
                       {
                           alert.showAlertDialog(Login.this, "Erreur Connexion", "Vous devez Connecter en mode connecté ", false);

                       }else
                       {
                           AsynckLoginOffline();

                       }
                   }



                    }

            }
        });

    }


    public class AsynckLogin extends AsyncTask<Void, Void, Void> {
        ArrayList<Map<String, String>> listItem = new ArrayList<Map<String, String>>();


        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(Login.this);
            progressDialog1.setMessage("Vérification du compte...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            Map<String, String> mapPPPP = null;
            String result = null;
            InputStream is = null;
            StringBuilder sb = new StringBuilder();
            if(Ping.testconwamp()) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("login", login));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Connexion.c1);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());
                }
                try { // try w catch c pour l'exception
                    //ici converstion de la resultat en String
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    is.close();
                    result = sb.toString();
                    System.out.print(result);
                }catch (java.lang.NullPointerException exception) {
                //loadSpinnerDataclient();
                 }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());
                }

                try {

                    try {
                        // parser les données en JSON
                        JSONArray jArray = new JSONArray(result);
                        int b = jArray.length();

                        JSONObject json_data = jArray.getJSONObject(0);
                        idd= json_data.getString("id_utulisateur").toString();
                        // ce qui est entre deux " ,c le nom de champs dans la base de données qu'on veut le recuprer
                        nom = json_data.getString("nom").toString();
                        prenom = json_data.getString("prenom").toString();


                        log = json_data.getString("login").toString();
                        mdp = json_data.getString("mdp").toString();
                        etat = json_data.getString("etat").toString();
                        numsite = json_data.getString("code_site").toString();
                        codeutulisateur = json_data.getString("code_utulisateur").toString();
                        imei = json_data.getString("imei").toString();
                        appareil = json_data.getString("nom_appereil").toString();
                        prefix = json_data.getString("prefix").toString();
                        JSONArray jArray1 = new JSONArray(result);




                    } catch (JSONException e) {
                        Log.e("log_tag", "Error parsing data " + e.toString());
                        // Toast.makeText(Login.this, "Vérifier votre login et password ", Toast.LENGTH_LONG).show();
                    }
                } catch (org.apache.http.ParseException e) {
                    Log.i("tagjsonpars", "" + e.toString());
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog1.dismiss();


            // TODO Auto-generated method stub
            IMEI_Number_Holder="866943031793730";

            if (Ping.testconwamp()&& Ping.testconappache() | true) {
                if (login.equals(log) && pass.equals(mdp) ) {
                    if (etat.equals("1")) {
                        if (imei.equals(IMEI_Number_Holder)) {

                            UserRepo repo = new UserRepo(getApplicationContext());
                            repo.deleteuser();

                            user user = new user();
                            user.code = codeutulisateur;
                            user.nomuser = nom;
                            user.prenomuser = prenom;
                            user.login = log;
                            user.pass = pass;
                            user.etat = etat;
                            user.numsite = numsite;
                            user.nomappareil = appareil;
                            user.prefix = prefix;
                            user.imei = imei;
                            _user_Id = repo.insert(user);
                            Intent i = new Intent(getApplicationContext(), Drawer.class);
                            startActivity(i);
                        } else {
                            alert.showAlertDialog(Login.this, "Erreur Connexion(01)", "", false);
                        }

                    } else {
                        alert.showAlertDialog(Login.this, "Erreur Connexion(02)", "", false);


                    }
                    //si les login et password sont juste on va vider les deux champs et lui ouvrire l'interface de menu principale


                } else {

                    alert.showAlertDialog(Login.this, "Erreur Connexion", "Login Ou Mot de passe est incorrect", false);
                }
            }else
            {
                alert.showAlertDialog(Login.this, "Erreur ", "Connexion impossible  connectez en" +
                        " mode hors connexion"+(Ping.testconwamp()), false);

            }




           /* if (listItem.get(1).get("res").toString().equals("1"))
            {
                alert.showAlertDialog(Login.this, "Compte inactif", "Votre compte est inactif", false);

            }else
            {
                startActivity(new Intent(Login.this,
                        Drawer.class));
            }*/


        }


        // progressDialog1.dismiss();


        // progressBar1.setVisibility(View.GONE);


    }

    public void AsynckLoginOffline() {

       /* @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(Login.this);
            progressDialog1.setMessage("Vérification du compte...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }*/


        // progressDialog1.dismiss();
        UserRepo repo = new UserRepo(getApplicationContext());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        codeutulisateuroff = siteList.get(0).get("code").toString();
        nomoff = siteList.get(0).get("nomuser").toString();
        prenomoff = siteList.get(0).get("prenomuser").toString();
        logoff = siteList.get(0).get("login").toString();
        mdpoff = siteList.get(0).get("pass").toString();
        etatoff = siteList.get(0).get("etat").toString();
        numsiteoff = siteList.get(0).get("numsite").toString();
        appareiloff = siteList.get(0).get("nomappareil").toString();
        imeioff = siteList.get(0).get("imei").toString();
        prefixoff = siteList.get(0).get("prefix").toString();
        if (login.equals(logoff) && pass.equals(mdpoff)) {
            if (etatoff.equals("1")) {
                if (imeioff.equals(IMEI_Number_Holder)) {

                    Intent i = new Intent(getApplicationContext(), Drawer.class);
                    startActivity(i);
                } else {
                    alert.showAlertDialog(Login.this, "Erreur Connexion(01)", "", false);
                }

            } else {
                alert.showAlertDialog(Login.this, "Erreur Connexion(02)", "", false);


            }
            //si les login et password sont juste on va vider les deux champs et lui ouvrire l'interface de menu principale


        } else {

            alert.showAlertDialog(Login.this, "Erreur Connexion", "Login Ou Mot de passe est incorrect", false);
        }


    }
}


