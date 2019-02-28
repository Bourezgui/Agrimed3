package com.example.ynaccache.agrimed2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.activity.Photofragment2;
import com.example.ynaccache.agrimed2.activity.Photofragment3;
import com.example.ynaccache.agrimed2.activity.order;
import com.example.ynaccache.agrimed2.activity.order1;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.sqlite.ArticleRepo;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PhotosFragment extends Fragment {
    String rest="";
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

    View v;
    ListView lst;
    JSONObject   json_data;
    EditText dtcom;
   public String dcommande;
    Button valider;
    Date date3;
    EditText inputSearch,inputSearch1;
    LinearLayout lin1,lin2;
    Button b1,b2;
public static String id_commande1, id_commande,numclient,nomclient,numsite,nomsite,datecommande,dateliv,priorite,
        observation,etat ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.activity_commande, container, false);
        SocialFragment.list.clear();

        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        lin1=(LinearLayout) v.findViewById(R.id.conn);
        lin2=(LinearLayout) v.findViewById(R.id.off);
        b1=(Button) v.findViewById(R.id.valid);
        b2=(Button) v.findViewById(R.id.attente);





        lst=(ListView) v.findViewById(R.id.listecommande1);
        // new Generationlistecommande().execute();
        if(haveNetworkConnection())
        {
            //new Generationlistecommande().execute();
            lin1.setVisibility(View.VISIBLE);
            lin2.setVisibility(View.GONE);
            b1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(getActivity(),Photofragment2.class);
                    startActivity(i);
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(getActivity(),Photofragment3.class);
                    startActivity(i);
                }
            });

        }else {
            lin2.setVisibility(View.VISIBLE);
            lin1.setVisibility(View.GONE);
            loadSpinnerData2();


                      lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    final HashMap<String, Object> map = (HashMap<String, Object>) lst.getItemAtPosition(position);
                    String item = lst.getItemAtPosition(position).toString();
                    id_commande = (String) map.get("id");
                    id_commande1 = (String) map.get("id1");
                    numclient = (String) map.get("numclient");
                    nomclient = (String) map.get("nomclient");
                    numsite = (String) map.get("numsite");
                    nomsite = (String) map.get("nomsite");
                    datecommande = (String) map.get("datecommande");
                    dateliv = (String) map.get("dateliv");
                    priorite = (String) map.get("priorite");
                    observation = (String) map.get("observation");
                    etat = (String) map.get("etat");


                    Intent i = new Intent(getActivity(), order.class);
                    startActivity(i);

                     //Toast.makeText(getActivity(), id_commande1,Toast.LENGTH_SHORT).show();

                }
            });

        }

        return v;
    }
    public class Generationlistecommande extends AsyncTask<Void, Void, Void> {


        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;


            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/ListOrd";
            String METHOD_NAME = "ListOrd";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );

            int b =2;
            Date bpcnum=new Date("2017/11/13");

            request.addProperty("YETAT","2");
            request.addProperty("YDAT","20180109");
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : "+rest);





            } catch (java.lang.NullPointerException exception) {
                    /*loadSpinnerArticle();
                    clicklistexception();*/

            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("erreur", e+"");

                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           /* try {
                JSONObject JA = new JSONObject(rest);
                JSONArray json = JA.getJSONArray("GRP1");
                for (int i = 0; i < json.length(); i++) {

                    json_data = json.getJSONObject(i);


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("des", json_data.getString("XORD"));
                    mapPPPP.put("itm", json_data.getString("YBPCNUM"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }*/





            return null;
        }


        @Override
        protected void onPostExecute(Void args) {

          /*  try
            {

                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item_commande,
                        new String[]{"des","itm"}, new int[]{R.id.titre1,R.id.quant1});



              lst.setAdapter(mSchedule);
                //text.setText("");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            // Locate the spinner in activity_main.xml
            //final MaterialSpinner spinner1 = (MaterialSpinner) findViewById(R.id.spinner1);


            /*ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, roll_no);
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/




            //ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //android.R.layout.simple_spinner_item, roll_no);
            //dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //adapter.add("This is Hint");
            //sp_article2.setAdapter(dataAdapter1);*/


        }

    }
    public      void loadSpinnerData2(){

        commandeRepo repo = new commandeRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
       final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item_commande,
                new String[]{"id1","mois","anneee","numsite","id","nomclient","etat"}, new int[]{R.id.id1,R.id.titre2,R.id.titre3,R.id.titre4,R.id.titre5,R.id.quant1,R.id.quant2});


        lst.setAdapter(mSchedule);
        inputSearch.addTextChangedListener(new TextWatcher() {

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




    };

}






