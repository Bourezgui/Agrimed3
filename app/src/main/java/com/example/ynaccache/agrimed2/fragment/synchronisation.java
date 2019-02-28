package com.example.ynaccache.agrimed2.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.activity.Login;
import com.example.ynaccache.agrimed2.model.article;
import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.model.site;
import com.example.ynaccache.agrimed2.other.AlertDialogManager;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.ArticleRepo;
import com.example.ynaccache.agrimed2.sqlite.ClientRepo;
import com.example.ynaccache.agrimed2.sqlite.SiteRepo;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class synchronisation extends Fragment {
    JSONObject json_data;
  int  _Client_Id =0;
    int _Site_Id=0;
    int _Article_Id=0;
    View v;
    Button sync;
    ProgressDialog progressDialog1;
    TextView txt1;
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
    AlertDialogManager alert = new AlertDialogManager();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_synchrnisation, null);
        SocialFragment.list.clear();


        sync = (Button) v.findViewById(R.id.synchroniser);


        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveNetworkConnection())
                {
                    if (Ping.testconappache()) {

                        new DownloadJSONARTICLE().execute();
                        new DownloadJSONCLIENT().execute();
                        new DownloadJSONSITE().execute();

                    }else
                    {
                        alert.showAlertDialog(getActivity(), "Erreur Synchronisation", "Vous devez connecter en mode connecté ", false);

                    }

                }else
                {
                    alert.showAlertDialog(getActivity(), "Erreur Synchronisation", "Vous devez connecter en mode connecté ", false);

                }



            }
        });

        return v;
    }
    public class DownloadJSONSITE extends AsyncTask<Void, Void, Void> {
        String result = null;
        InputStream is = null;
        JSONObject json_data=null;

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
            }
            catch (java.lang.NullPointerException exception) {
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






                 ArrayList<HashMap<String, Object>> listItema = listItem;




               SiteRepo  repo = new SiteRepo(getActivity());

               repo.deleteuser();
                for (int i = 0; i < listItema.size(); i++) {
                    site article = new site();
                    article.nomsite = listItema.get(i).get("nomsite").toString();
                    article.numsite = listItema.get(i).get("numsite").toString();


                    repo.insert(article);


                    //
                }

            //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //android.R.layout.simple_spinner_item, roll_no);
            //dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //adapter.add("This is Hint");
            //sp_article2.setAdapter(dataAdapter1);


        }

    }
    public  class DownloadJSONARTICLE extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog1;
        String rest;



        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Synchronisation des articles...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            InputStream is;
            String URL =Connexion.c;
            String SOAP_ACTION = "http://connexion/ListArt";
            String METHOD_NAME = "ListArt";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );
            request.addProperty("XFCY",Login.numsite);

            //request.addProperty("XUNI",1);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);
                if (envelope.getResponse().toString().isEmpty())
                {

                }else {
                    rest = envelope.getResponse().toString();
                    System.out.println("reponse : " + rest);
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
                JSONArray json = JA.getJSONArray("GRP2");
                for (int i = 0; i < json.length(); i++) {

                    json_data = json.getJSONObject(i);


                    mapPPPP = new HashMap<String, Object>();
                    if(json_data.getString("YDES").isEmpty()&&json_data.getString("YITM").isEmpty()){}
                    else  {    mapPPPP.put("nomaricle", json_data.getString("YDES"));
                        mapPPPP.put("numaricle", json_data.getString("YITM"));
                        mapPPPP.put("abreviation", json_data.getString("YABR"));

                        listItem.add(mapPPPP);

                    }
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));





                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }


            final ArrayList<Map<String, Object>> listItema = listItem;




            ArticleRepo repo = new ArticleRepo(getActivity());

            repo.deletearticle();
            for (int i = 0; i < listItema.size(); i++) {
                article article = new article();
                article.nomarticle=listItema.get(i).get("nomaricle").toString();
                article.numarticle=listItema.get(i).get("numaricle").toString();
                article.abreviation=listItema.get(i).get("abreviation").toString();

                article.nomsite=Login.numsite;

                article.article_ID =_Article_Id;
                _Article_Id= repo.insert(article);


                //loadSpinnerData();





            }

            //conversion de la réponse en chaine de caractère

            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            //final MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);
            //text1.setText(rest);
            try
            {

                progressDialog1.dismiss();
                //text.setText("");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            /*ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, roll_no);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/




        }

    }

    public class DownloadJSONCLIENT extends AsyncTask<Void, Void, Void> {


        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Synchronisation des clients...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            String rest="";


            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/ListBpc";
            String METHOD_NAME = "ListBpc";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );


            request.addProperty("YAUS", Login.codeutulisateur);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : "+rest);





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


            try{


                ClientRepo repo = new ClientRepo(getActivity());
                repo.deleteclient();
                for (int i = 0; i < listItem.size(); i++) {
                    cli cli = new cli();
                    cli.nomclien=listItem.get(i).get("nomclient").toString();
                    cli.numclient=listItem.get(i).get("numclient").toString();
                    cli.nomsite= Login.numsite;


                    cli.client_ID =_Client_Id;
                    _Client_Id= repo.insert(cli);


                    //loadSpinnerData();
                }




            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }






            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
           progressDialog1.dismiss();
            alert.showAlertDialog(getActivity(), "Succés", "Votre Synchronisation a eté effectuer avec succés ", true);





        }

    }
}

