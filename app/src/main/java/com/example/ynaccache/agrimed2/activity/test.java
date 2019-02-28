package com.example.ynaccache.agrimed2.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.sqlite.UserRepo;
import com.example.ynaccache.agrimed2.sqlite.commandeRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {
    AutoCompleteTextView cli;
    JSONObject json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
       /* Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });*/
        cli = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
       // new DownloadJSONCLIENT().execute();
        /*UserRepo repo = new UserRepo(getApplicationContext());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        cli.setText(siteList.get(0).get("imei").toString());*/
        new DownloadJSONSITE().execute();


    }
    public class DownloadJSONCLIENT extends AsyncTask<Void, Void, Void> {


        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;

            String rest1="";

            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/ListBpc";
            String METHOD_NAME = "ListBpc";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );


            request.addProperty("YFCY","RAD");
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest1 = envelope.getResponse().toString();
                System.out.println("reponse : "+rest1);





            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("erreur", e+"");

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


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("nomclient", json_data.getString("YBPCNAM"));
                    mapPPPP.put("numclient", json_data.getString("YBPC"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            } catch (org.apache.http.ParseException e) {
                Log.i("tagjsonpars", "" + e.toString());
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
                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(getApplicationContext(), listItema, R.layout.item,
                        new String[]{"nomclient"}, new int[]{R.id.t1});

                cli.setThreshold(1);
                cli.setAdapter(mSchedule);





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
    public class DownloadJSONSITE extends AsyncTask<Void, Void, Void> {


        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {
            String rest="";

            Map<String, Object> mapPPPP = null;



            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/ListFcy";
            String METHOD_NAME = "ListFcy";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope1= new SoapSerializationEnvelope(SoapEnvelope.VER11
            );


            //request.addProperty("XUNI",1);
            envelope1.dotNet=true;
            envelope1.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope1);
                if(envelope1.getResponse().toString().isEmpty())
                {

                }else
                {
                    rest = envelope1.getResponse().toString();
                    System.out.println("reponse : "+rest);
                }







            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("erreur", e+"");

                e.printStackTrace();
            } catch (XmlPullParserException e) {


                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                JSONObject JA = new JSONObject(rest);
                JSONArray json = JA.getJSONArray("GRP1");
                for (int i = 0; i < json.length(); i++) {

                    json_data = json.getJSONObject(i);


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("nomsite", json_data.getString("YFCYNAM"));
                    mapPPPP.put("numsite", json_data.getString("YFCY"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            } catch (org.apache.http.ParseException e) {
                Log.i("tagjsonpars", "" + e.toString());
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
                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(getApplicationContext(), listItema, R.layout.item,
                        new String[]{"nomsite"}, new int[]{R.id.t1});

                cli.setThreshold(1);
                cli.setAdapter(mSchedule);


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
}
