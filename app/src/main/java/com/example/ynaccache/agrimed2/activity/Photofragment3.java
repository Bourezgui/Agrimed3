package com.example.ynaccache.agrimed2.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.fragment.SocialFragment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by y.naccache on 10/01/2018.
 */

public class Photofragment3 extends AppCompatActivity {
    EditText inputSearch;
    ListView lst;
    JSONObject   json_data;
    EditText ed1;
    ImageButton b1;
    public static String datee,jour,moi,annee,nomclient,numclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SocialFragment.list.clear();
        ed1=(EditText) findViewById(R.id.datelivraison1);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        lst=(ListView)findViewById(R.id.listecommande1);
        b1=(ImageButton) findViewById(R.id.rchcom);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel() {


                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ed1.setText(sdf.format(myCalendar.getTime()));
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

        ed1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Photofragment3.this, R.style.datepicker, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    if(ed1.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "Veuillez remplir ce champ", Toast.LENGTH_LONG).show();
                    }else
                    {
                        datee = ed1.getText().toString();
                        String[] parts = datee.split("/");
                        jour = parts[0];
                        moi= parts[1];
                        annee=parts[2];
                        new Generationlistecommande().execute();

                    }
                // TODO Auto-generated method stub

            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final HashMap<String, Object> map = (HashMap<String, Object>) lst.getItemAtPosition(position);
                String item = lst.getItemAtPosition(position).toString();
                numclient = (String) map.get("des");

                nomclient = (String) map.get("itm");



                Intent i = new Intent(Photofragment3.this, order2.class);
                startActivity(i);

                // Toast.makeText(getActivity(), nomsite,Toast.LENGTH_SHORT).show();

            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class Generationlistecommande extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog1;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(Photofragment3.this);
            progressDialog1.setMessage("Merci de patienter...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            String rest="";

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

            request.addProperty("YETAT","1");

            request.addProperty("YDAT",annee + moi + jour);
            request.addProperty("YOPERATEUR",Login.codeutulisateur);

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
                JSONArray json = JA.getJSONArray("GRP1");

               for (int i = 0; i < json.length(); i++) {

                    json_data = json.getJSONObject(i);
                   /// json_data2 = json2.getJSONObject(i);


                   mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("des", json_data.getString("XORD"));
                    mapPPPP.put("itm", json_data.getString("YBPCNUM"));
                    //mapPPPP.put("unité", json_data2.getString("YBPCNUM"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }





            return null;
        }


        @Override
        protected void onPostExecute(Void args) {

           try
            {
                progressDialog1.dismiss();
                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(Photofragment3.this, listItema, R.layout.item_commande1,
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
