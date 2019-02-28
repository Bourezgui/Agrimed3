package com.example.ynaccache.agrimed2.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.activity.Drawer;
import com.example.ynaccache.agrimed2.activity.Login;
import com.example.ynaccache.agrimed2.activity.test2;
import com.example.ynaccache.agrimed2.model.article;
import com.example.ynaccache.agrimed2.model.cli;


import com.example.ynaccache.agrimed2.model.site;


import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.model.user;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.CustomEditText;
import com.example.ynaccache.agrimed2.other.DrawableClickListener;
import com.example.ynaccache.agrimed2.other.JSONfunctions;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.ArticleRepo;
import com.example.ynaccache.agrimed2.sqlite.ClientRepo;
import com.example.ynaccache.agrimed2.sqlite.SiteRepo;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.ynaccache.agrimed2.activity.Login.log;


public class client extends Fragment {



    NetworkCapabilities caps1,caps2,caps3;

    private SettingsFragment.OnFragmentInteractionListener mListener;



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    private int _Site_Id=0;
    private int _Client_Id=0;
    private int _Article_Id;
    public Spinner sp_client;
    public static String site;
    public static  String jour,moi,annee;
    public static Date date1, date2;
    String compareValue = "choisir un client";
    public int spinnerPosition;
    JSONObject jsonobject;
    public static EditText ed1, ed2;
    public static String id_client, id_site,nom,nom_site;
    public static String nom_client="";
    public static String client, datecommande, dateliv;
    public static AutoCompleteTextView text, cli;
    ProgressDialog progressDialog1;

    public static String numsiteoff1;


    InputStream is = null;
    String result = null;
    JSONObject json_data;
    String output = null;
    Button b1,b2;
    public String[] roll_no;
    int pos = 0;
    View v;


    //////////////////alerte/////////
    public static  ListView list,list2;
    public Dialog alerte,alerte2;
    public EditText search,search2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.client_layout, null);
/*Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });*/

        UserRepo repo1 = new UserRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList1 = repo1.getStudentList();
        numsiteoff1 = siteList1.get(0).get("numsite").toString();

        ed1 = (EditText) v.findViewById(R.id.datecommande);
        ed1.setEnabled(false);
        b1 = (Button) v.findViewById(R.id.search1);

        text = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView1);
        cli = (AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView2);

        alerte=new Dialog(getActivity());
        alerte.setContentView(R.layout.input_box1);
        alerte2=new Dialog(getActivity());
        alerte2.setContentView(R.layout.input_box3);
        search=(EditText) alerte.findViewById(R.id.tt1);
        search2=(EditText) alerte2.findViewById(R.id.tt1);

        list=(ListView) alerte.findViewById(R.id.list1);
        list2=(ListView) alerte2.findViewById(R.id.list1);
        if (haveNetworkConnection()) {

            new DownloadJSONSITE().execute();
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    final HashMap<String, Object> map = (HashMap<String, Object>) list2.getItemAtPosition(position);
                    nom_site = (String) map.get("nomsite");
                    id_site = (String) map.get("numsite");
                    text.setText(id_site);
                    alerte2.dismiss();











                    // Toast.makeText(getActivity(), nomsite,Toast.LENGTH_SHORT).show();

                }
            });
        }
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);




       /* if (networks.length==1 ||networks.length==2) {
        }else
        {
            caps3 = cm.getNetworkCapabilities(networks[2]);

        }*/


        if (haveNetworkConnection()) {

                new DownloadJSONCLIENT().execute();
            new DownloadJSONARTICLE().execute();
            text.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    showalertesite();

                }
            });
                //text.setEnabled(false);





            }

            //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).sho}else{
        else
        {
            text.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    showalertesite();

                }
            });
            loadSpinnersite();
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    final HashMap<String, Object> map = (HashMap<String, Object>) list2.getItemAtPosition(position);
                    nom_site = (String) map.get("nomsite");
                    id_site = (String) map.get("numsite");
                    text.setText(id_site);
                    alerte2.dismiss();

                }
            });
            //text.setText(numsiteoff1);
            //text.setEnabled(false);
            loadSpinnerDataclient();

        }

       /* if (haveNetworkConnection()) {
            //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();
            // new DownloadJSON2().execute();
        // new DownloadJSONSITE().execute();
            //new DownloadJSONARTICLE().execute();

          while(d.execute()==null){
                d.execute();
            }
        }else{
            loadSpinnerData();


        }*/





            //text.setEnabled(false);



      /*b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub




                if(text.getText().toString().isEmpty())
                {
                    text.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);

                    text.showDropDown();

                }else{

                    text.setText("");



                }



            }
        });*/







        cli.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showalerteclient();

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                final HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
                nom_client = (String) map.get("nomclient");
                id_client = (String) map.get("numclient");
                cli.setText(nom_client);
                alerte.dismiss();











                // Toast.makeText(getActivity(), nomsite,Toast.LENGTH_SHORT).show();

            }
        });



        /*cli.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


                if (haveNetworkConnection()) {

                    final HashMap<String, Object> map = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                   nom_client = (String) map.get("nomclient");
                    id_client = (String) map.get("numclient");
                    // Toast.makeText(getActivity(), id_client, Toast.LENGTH_SHORT).show();




                    cli.setText(nom_client);

                }else {
                    final HashMap<String, Object> map = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                     nom_client = (String) map.get("nomclient");
                    id_client = (String) map.get("numclient");
                    Toast.makeText(getActivity(), id_client, Toast.LENGTH_SHORT).show();



                    cli.setText(nom_client);


                }
                //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();



            }
        });*/










        //
        //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();

        if (haveNetworkConnection()) {
            //Toast.makeText(getApplicationContext(), "Connexion réussi", Toast.LENGTH_SHORT).show();
        } else {
            //loadSpinnerData();


        }


        ed2 = (EditText) v.findViewById(R.id.datelivraison);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ed1.setText(sdf.format(new Date()));
        String sDate1 = "31/12/1998";


        datecommande = ed1.getText().toString();
        String[] parts = datecommande.split("/");
         jour = parts[0];
        moi= parts[1];
        annee=parts[2];
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(datecommande);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(datecommande);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //ed1.setEnabled(false);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ed2.setText(sdf.format(myCalendar.getTime()));
            }

            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth,
                                  int mDay) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, mYear);
                myCalendar.set(Calendar.MONTH, mMonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                updateLabel();
            }
        };

        ed2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), R.style.datepicker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        dateliv = ed2.getText().toString();


        if (date1.before(date2)) {
            System.out.print("possile");

        }


        //sp_client.setTitle("choisir un client ");
        //sp_client.setPositiveButton("OK");

        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.villes, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sp_client.setAdapter(adapter);
        // Button btn = (Button) v.findViewById(R.id_client.btn);
        // spinnerPosition = adapter.getPosition(compareValue);

        //loadSpinnerData();

        /*text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (site.equals(""))
                {

                }
                else{
                    site= arg0.getItemAtPosition(arg2).toString();

                }



            }
        });*/

       /* sp_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int pos;
                pos = sp_client.getSelectedItemPosition();
                int iden = arg0.getId();

                if (iden == R.id_client.spinner) {



                    client = sp_client.getSelectedItem().toString();




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });*/

        return v;
    }

    public class DownloadJSON extends AsyncTask<Void, Void, Void> {
        final List<String> list1 = new ArrayList<String>();
        final List<String> list2 = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... params) {


            try {

                //  URL url = new URL("http://196.203.89.113:8139/sdata/x3/erp/SEED/BPCUSTOMER?representation=BPCUSTOMER.$query&count=1000");
                //URL url = new URL("http://10.0.3.3/consultant/client.txt");
                URL url = new URL("http://yousseffares.alwaysdata.net/client.txt");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                //////
                String username = "admin";
                String password = "admin";
                String userPassword = username + ":" + password;
                // Encoder encoder = Base64.getEncoder();
                // byte[] src = userPassword.getBytes();


                //String encoding = encoder.encodeToString(src);
                String basicAuth = "Basic " + "V0VCU1JWOldFQlNSVg==";
                conn.setRequestProperty("Authorization", basicAuth);
                //////
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                StringBuilder sb = new StringBuilder();


                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    // System.out.println(output);

                    sb.append(output + "\n");


                }
                result = sb.toString();
                Log.i("result", result);

                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            try {
                JSONObject JA = new JSONObject(result);
                JSONArray json = JA.getJSONArray("$resources");
                roll_no = new String[json.length()];


                for (int i = 0; i < json.length(); i++) {
                    json_data = json.getJSONObject(i);
                    roll_no[i] = json_data.getString("BPCNAM");

                }

                for (int i = 0; i < roll_no.length; i++) {
                    list1.add(roll_no[i]);

                }


            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
                //login.this.finish();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml


            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, roll_no);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //android.R.layout.simple_spinner_item, roll_no);
            //dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //adapter.add("This is Hint");
            sp_client.setAdapter(dataAdapter1);
            DatabaseHandler db = new DatabaseHandler(
                    getActivity());

            db.deleteLabel();
            for (String s : list1) {
                db.insertLabel(s);
            }
            // loadSpinnerData();


        }
    }

    public class DownloadJSON2 extends AsyncTask<Void, Void, Void> {
        final List<String> list1 = new ArrayList<String>();
        final List<String> list2 = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... params) {
            String line;
            JSONObject jsonobject;


            jsonobject = JSONfunctions
                    .getJSONfromURL("http://yousseffares.alwaysdata.net/client.txt");
            /*jsonobject = JSONfunctions
                    .getJSONfromURL("http://196.203.89.113:8139/sdata/x3/erp/SEED/BPCUSTOMER?representation=BPCUSTOMER.$query&count=1000");*/
            try {
                JSONArray json = jsonobject.getJSONArray("$resources");
                roll_no = new String[json.length()];


                for (int i = 0; i < json.length(); i++) {
                    json_data = json.getJSONObject(i);
                    roll_no[i] = json_data.getString("BPCNUM");

                }

                for (int i = 0; i < roll_no.length; i++) {
                    list1.add(roll_no[i]);


                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            //final MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id_client.spinner1);
            try {

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_dropdown_item, roll_no);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cli.setThreshold(1);
                cli.setAdapter(dataAdapter1);


                //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
                //android.R.layout.simple_spinner_item, roll_no);
                //dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //adapter.add("This is Hint");


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


        }
    }



    public class DownloadJSONSITE extends AsyncTask<Void, Void, Void> {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


        ArrayList<String> donnees = new ArrayList<String>();
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapPPPP=null;
        @Override
        protected Void doInBackground(Void... params) {

            try{
                //commandes httpClient
                nameValuePairs.add(new BasicNameValuePair("code",Login.idd));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.100.100:8181/agrimed4/webservice/site.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
            catch(Exception e){
                Log.i("taghttppost",""+e.toString());
            }


            //conversion de la réponse en chaine de caractère
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));

                StringBuilder sb  = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                is.close();

                result = sb.toString();
                Log.i("result",result);
            }catch (java.lang.NullPointerException exception) {
                //loadSpinnerDataclient();
            }
            catch(Exception e)
            {
                Log.i("tagconvertstr",""+e.toString());
            }
            //recuperation des donnees json
            try{
                JSONArray jArray = new JSONArray(result);

                for(int i=0;i<jArray.length();i++)
                {

                    json_data = jArray.getJSONObject(i);


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("numsite",json_data.getString("code_site"));
                    mapPPPP.put("nomsite",json_data.getString("nom_site"));

                    listItem.add(mapPPPP);


                }

            }
            catch(JSONException e){
                Log.i("tagjsonexp",""+e.toString());
            } catch (Exception e) {
                Log.i("tagjsonpars",""+e.toString());
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            //final MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);


            /*ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, roll_no);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

            try{
                ArrayList<HashMap<String, Object>> listItema = listItem;
              final   SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item,
                        new String[]{"nomsite"}, new int[]{R.id.t1});

                list2.setAdapter(mSchedule);


               /* SiteRepo repo = new SiteRepo(getActivity());

                 repo.deleteSitel();
                for (int i = 0; i < listItema.size(); i++) {
                    site site = new site();
                    site.nomsite=listItema.get(i).get("nomsite").toString();
                    site.numsite=listItema.get(i).get("numsite").toString();
                    site.site_ID =_Site_Id;
                    _Site_Id= repo.insert(site);


                    //loadSpinnerData();





                }*/
                search2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        mSchedule.getFilter().filter(cs);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                //
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //android.R.layout.simple_spinner_item, roll_no);
            //dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //adapter.add("This is Hint");
            //sp_article2.setAdapter(dataAdapter1);


        }

    }
    public  class DownloadJSONCLIENT extends AsyncTask<Void, Void, Void> {


        ArrayList<Map<String, String>> listItem = new ArrayList<Map<String, String>>();

        String param;
        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> mapPPPP = null;
            Map<String, String> mapPPPP1 = null;

            String rest1="";


            InputStream is;
            if (Ping.testconappache()) {

                String URL = Connexion.c;
                String SOAP_ACTION = "http://connexion/ListBpc";
                String METHOD_NAME = "ListBpc";
                String NAMESPACE = "http://connexion";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
                );

///YAUS
                request.addProperty("YAUS", Login.codeutulisateur);
               // param = request.addProperty("YFCY", Login.numsite).toString();
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE a1 = new HttpTransportSE(URL);
                a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                a1.debug = true;
                try {
                    a1.call(SOAP_ACTION, envelope);

                    rest1 = envelope.getResponse().toString();
                    System.out.println("reponse : " + rest1);


                } catch (java.lang.NullPointerException exception) {
                    //loadSpinnerDataclient();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d("erreur", e + "");

                    e.printStackTrace();
                } catch (XmlPullParserException e) {


                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    JSONObject JA = new JSONObject(rest1);
                    JSONArray json = JA.getJSONArray("GRP2");
                    for (int i = 0; i < json.length(); i++) {

                        json_data = json.getJSONObject(i);


                        mapPPPP = new HashMap<String, String>();

                        if (json_data.getString("YBPCNAM").isEmpty()) {
                            System.out.println("hey there this is empty youssef");
                        } else if (json_data.getString("YBPCNAM").equals("MONOPRIX")) {
                            System.out.println("kkkk");

                        } else {
                            mapPPPP.put("nomclient", json_data.getString("YBPCNAM"));
                            mapPPPP.put("numclient", json_data.getString("YBPC"));


                            listItem.add(mapPPPP);


                        }
                        mapPPPP1 = new HashMap<String, String>();


                    }

                } catch (JSONException e) {
                    Log.i("tagjsonexp", "" + e.toString());
                } catch (org.apache.http.ParseException e) {
                    Log.i("tagjsonpars", "" + e.toString());
                }


            }
            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            if (Ping.testconappache()) {

                try {
                    final ArrayList<Map<String, String>> listItema = listItem;
                    final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item,
                            new String[]{"nomclient"}, new int[]{R.id.t1});

                    //cli.setThreshold(1);
                    // cli.setAdapter(mSchedule);
                    list.setAdapter(mSchedule);

                    search.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            mSchedule.getFilter().filter(cs);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }






        }

    }
    public  class DownloadJSONARTICLE extends AsyncTask<Void, Void, Void> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            InputStream is;
            if(Ping.testconappache()) {
                String URL = Connexion.c;
                String SOAP_ACTION = "http://connexion/ListArt";
                String METHOD_NAME = "ListArt";
                String NAMESPACE = "http://connexion";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
                );
                request.addProperty("XFCY", Login.numsite);

                //request.addProperty("XUNI",1);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE a1 = new HttpTransportSE(URL);
                a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                a1.debug = true;
                try {
                    a1.call(SOAP_ACTION, envelope);
                    if (envelope.getResponse().toString().isEmpty()) {

                    } else {
                        rest = envelope.getResponse().toString();
                        System.out.println("reponse : " + rest);
                    }


                } catch (java.lang.NullPointerException exception) {
                    /*loadSpinnerArticle();
                    clicklistexception();*/

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d("erreur", e + "");

                    e.printStackTrace();
                } catch (XmlPullParserException e) {


                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    JSONObject JA = new JSONObject(rest);
                    JSONArray json = JA.getJSONArray("GRP2");
                    for (int i = 0; i < json.length(); i++) {

                        json_data = json.getJSONObject(i);


                        mapPPPP = new HashMap<String, Object>();
                        if (json_data.getString("YDES").isEmpty() && json_data.getString("YITM").isEmpty()) {
                        } else {
                            mapPPPP.put("nomaricle", json_data.getString("YDES"));
                            mapPPPP.put("numaricle", json_data.getString("YITM"));
                            mapPPPP.put("abreviation", json_data.getString("YABR"));
                            listItem.add(mapPPPP);

                        }
                        //mapPPPP.put("ville", json_data.getString("DES1AXX"));


                    }


                } catch (java.lang.NullPointerException exception) {
                   /* loadSpinnerArticle();
                   clicklistexception();*/
                } catch (JSONException e) {
                    Log.i("tagjsonexp", "" + e.toString());
                }


            }
            //conversion de la réponse en chaine de caractère

            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            if(Ping.testconappache()) {
                try {

                    final ArrayList<Map<String, Object>> listItema = listItem;

                    final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item_article,
                            new String[]{"abreviation"}, new int[]{R.id.art1});


                    SocialFragment.list2.setAdapter(mSchedule);
                    SocialFragment.search1.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            mSchedule.getFilter().filter(cs);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            }else
            {

            }





        }

    }
    public class DownloadJSONARTICLE1 extends AsyncTask<Void, Void, Void> {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapPPPP=null;


        @Override
        protected Void doInBackground(Void... params) {
            String result = null;
            InputStream is = null;
            StringBuilder sb = new StringBuilder();

            if(Ping.testconwamp()) {

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Connexion.c2);
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
                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());
                }

                try {

                    try {
                        // parser les données en JSON
                        JSONArray jArray = new JSONArray(result);

                        for(int i=0;i<jArray.length();i++)
                        {

                            json_data = jArray.getJSONObject(i);


                            mapPPPP = new HashMap<String, Object>();

                            mapPPPP.put("numaricle",json_data.getString("code_article"));
                            mapPPPP.put("nomaricle",json_data.getString("abr"));

                            listItem.add(mapPPPP);


                        }


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


            // TODO Auto-generated method stub

            if (Ping.testconwamp()&& Ping.testconappache()) {
                final ArrayList<HashMap<String, Object>> listItema = listItem;

                final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item_article,
                        new String[]{"nomaricle"}, new int[]{R.id.art1});


                SocialFragment.list2.setAdapter(mSchedule);
                SocialFragment.search1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        mSchedule.getFilter().filter(cs);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                    }
                });

            }else
            {

            }






        }


        // progressDialog1.dismiss();


        // progressBar1.setVisibility(View.GONE);


    }


    private void loadSpinnerData() {
        // database handler
        SiteRepo repo = new SiteRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList =  repo.getStudentList();
        SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item,
                new String[] {"nomsite"}, new int[] {R.id.t1});

        text.setThreshold(1);
        text.setAdapter(mSchedule);



        // attaching data adapter to spinner

    }

    private void loadSpinnerDataclient() {
        UserRepo repo1 = new UserRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList1 = repo1.getStudentList();
         numsiteoff1 = siteList1.get(0).get("numsite").toString();

        ClientRepo repo = new ClientRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList =  repo.getStudentList();
       final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item,
                new String[] {"nomclient"}, new int[] {R.id.t1});


        //cli.setThreshold(1);
        // cli.setAdapter(mSchedule);
        list.setAdapter(mSchedule);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mSchedule.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });



        // attaching data adapter to spinner

    }

    public void showalerteclient(){
        /// public void showInputBox(){

        // decalartyion d'alerte dialogue



        alerte.setTitle("Choisir un Client"); // titre de l'alerte
        // message de la'lerte
        // ListView list = ((AlertDialog) dialog).getListView();

        alerte.show();
    }
    public void showalertesite(){
        /// public void showInputBox(){

        // decalartyion d'alerte dialogue



        alerte2.setTitle("Choisir un Site"); // titre de l'alerte
        // message de la'lerte
        // ListView list = ((AlertDialog) dialog).getListView();

        alerte2.show();
    }


    public      void loadSpinnersite(){

        SiteRepo repo = new SiteRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item_article,
                new String[]{"nomsite"}, new int[]{R.id.art1});

       list2.setAdapter(mSchedule);
        search2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mSchedule.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });



    }





}
































