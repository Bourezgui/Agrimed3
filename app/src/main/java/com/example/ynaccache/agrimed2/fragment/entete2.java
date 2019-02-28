package com.example.ynaccache.agrimed2.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.activity.Photofragment2;
import com.example.ynaccache.agrimed2.activity.Photofragment3;
import com.example.ynaccache.agrimed2.other.Connexion;

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


public class entete2 extends Fragment {
    EditText etat,site,nomsite,client,nomclient,datecommande,dateliv,priorité,observation;
    View v;
    public static String fcy,bpc,datecom,datelivv,prio,obs;
    public static  String jour,mois,anneee,jour1,mois1,annee1;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_entete, container, false);
        etat=(EditText) v.findViewById(R.id.ed1);
        site=(EditText) v.findViewById(R.id.ed2);
        nomsite=(EditText) v.findViewById(R.id.ed3);
        nomsite.setVisibility(View.GONE);
        client=(EditText) v.findViewById(R.id.ed4);
        nomclient=(EditText) v.findViewById(R.id.ed5);
        datecommande=(EditText) v.findViewById(R.id.ed6);
        dateliv=(EditText) v.findViewById(R.id.ed8);
        priorité=(EditText) v.findViewById(R.id.ed9);
        observation=(EditText) v.findViewById(R.id.ed10);

        etat.setEnabled(false);
        site.setEnabled(false);
        nomsite.setEnabled(false);
        client.setEnabled(false);
        nomclient.setEnabled(false);
        datecommande.setEnabled(false);
        dateliv.setEnabled(false);
        priorité.setEnabled(false);
        observation.setEnabled(false);










       /* etat.setText(PhotosFragment.etat);
        site.setText(PhotosFragment.numsite);
        nomsite.setText(PhotosFragment.nomsite);
        nomclient.setText(PhotosFragment.nomclient);
        client.setText(PhotosFragment.numclient);
        datecommande.setText(PhotosFragment.datecommande);
        dateliv.setText(PhotosFragment.dateliv);

     if(PhotosFragment.priorite.equals("1"))
     {
      priorité.setText("Faible");

     }else if (PhotosFragment.priorite.equals("2"))
     {
         priorité.setText("Normale");

     }else
     {
         priorité.setText("Haute");

     }*/
        //observation.setText(PhotosFragment.observation);





       new Generationlentetecommande().execute();



        return v;
    }

    public class Generationlentetecommande extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog1;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
       /* @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(Photofragment2.this);
            progressDialog1.setMessage("Merci de patienter...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }*/
        @Override
        protected Void doInBackground(Void... params) {
            String rest="";

            Map<String, Object> mapPPPP = null;


            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/YCMD";
            String METHOD_NAME = "YCMD";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );

            int b =2;
            Date bpcnum=new Date("2017/11/13");

            request.addProperty("YNUM", Photofragment3.numclient);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : "+rest);





            } catch (NullPointerException exception) {
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
            try {
                JSONObject JA = new JSONObject(rest);
                JSONObject json = JA.getJSONObject("GRP1");
                JSONArray json2 = JA.getJSONArray("GRP2");
                JSONArray json3 = JA.getJSONArray("GRP3");

                JSONObject json_data = json2.getJSONObject(0);
                obs=json_data.getString("YOBS").toString();

                for (int i = 0; i < json2.length(); i++) {
                    mapPPPP = new HashMap<String, Object>();

                    JSONObject json_data1 = json2.getJSONObject(i);
                    JSONObject json_data2 = json3.getJSONObject(i);

                    mapPPPP.put("nomarticle", json_data1.getString("YITMREF"));
                    mapPPPP.put("quantite", json_data1.getString("YQTY"));
                    mapPPPP.put("unite", json_data2.getString("YUOM"));

                    listItem.add(mapPPPP);




                }



                    fcy= json.getString("YFCY").toString();

                bpc= json.getString("YBPCNUM").toString();
                datecom = json.getString("YORDDAT").toString();
                prio= json.getString("YPRIOR").toString();
                jour= datecom.substring(6, 8);
                mois= datecom.substring(4, 6);
                anneee= datecom.substring(0, 4);


                datelivv = json.getString("YDATLIV").toString();

                jour1= datelivv.substring(6, 8);
                mois1= datelivv.substring(4, 6);
                annee1= datelivv.substring(0, 4);







            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }





            return null;
        }


        @Override
        protected void onPostExecute(Void args) {

            nomclient.setText(Photofragment3.nomclient);

            etat.setText("En attente");
            site.setText(fcy);
            client.setText(bpc);
            datecommande.setText(jour+"/"+mois+"/"+anneee);
            dateliv.setText(jour1+"/"+mois1+"/"+annee1);
         if(prio.equals("1"))
            {
                priorité.setText("Faible");

            }else if (prio.equals("2"))
            {
                priorité.setText("Normale");

            }else
            {
                priorité.setText("Haute");

            }

            observation.setText(obs);

            final ArrayList<Map<String, Object>> listItema = listItem;
            SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.row_article,
                    new String[]{"nomarticle","quantite","unite"}, new int[]{R.id.titre,R.id.quant,R.id.unite});
            detailss2.lst.setAdapter(mSchedule);

           /* try
            {
                progressDialog1.dismiss();
                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(Photofragment2.this, listItema, R.layout.item_commande1,
                        new String[]{"des","itm"}, new int[]{R.id.quant11,R.id.quant22});
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

}
