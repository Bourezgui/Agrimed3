package com.example.ynaccache.agrimed2.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.activity.Login;
import com.example.ynaccache.agrimed2.other.AlertDialogManager;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.JSONfunctions;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.ArticleRepo;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.support.constraint.R.id.parent;


public class SocialFragment extends Fragment {
    int position1;

    //////////////////VPN////////////////////
    NetworkCapabilities caps1,caps2,caps3;
    ConnectivityManager cm;
    Network[] networks ;
    public static String unn ,car,pal;






    public Dialog dialog;
    int stocc,quann;
   public static String  stockk;


    public static Date date1,date2;
    public String json;
    public String unitee="";
    public int pos;





    public String abreviation,id_article,nom,id_articlebox,nombox;
    TextInputLayout inputquantun,inputquantcar,inputquantpal;
     public EditText editText1;
    public AutoCompleteTextView editText2,editText3;
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
    SearchableSpinner sp_article,sp_article2,sp_unite ;
    public static String req3="";
    public static CustomListViewadapter adapter,adapter1;
    AlertDialogManager alert = new AlertDialogManager();


    Button btajout;
    EditText qt,qtcar,qtpal;
    LinearLayout lin1;
    String quantite ,dateliv;
    ArrayList<String> mylist=new ArrayList<String>();
     public static  ArrayList<RowItem> list = new ArrayList<RowItem>();
    public static ArrayList<RowItem> list1 = new ArrayList<RowItem>();
   public static ListView lst;
    InputStream is=null;
    String result=null;
    public static int size=0;
    JSONObject json_data;
    String output=null;
    public String[] roll_no;
    public String[] it_des;
    ArrayAdapter adapter_unite;
    Button valider;
    ProgressDialog progressDialog1;
    String  article1;
   public static AutoCompleteTextView cache,article,unite,articlebox;
    String unite_article;
    //////////////////alerte/////////
    public static ListView list2;
    public Dialog alerte2;
    public static EditText search1;
    ///////////Spinner//////
    MaterialSpinner uni;
    //////alerte modif//////
    Button rech;




    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.article_layout,null);

        dialog=new Dialog(getActivity());

        inputquantun=(TextInputLayout) v.findViewById(R.id.input2);
        inputquantcar=(TextInputLayout) v.findViewById(R.id.inputt1);
        inputquantpal=(TextInputLayout) v.findViewById(R.id.inputt2);


        article =(AutoCompleteTextView) v.findViewById(R.id.autoCompleteTextView3);
        uni=(MaterialSpinner) v.findViewById(R.id.un) ;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);



        lin1= (LinearLayout) v.findViewById(R.id.linear11);

        size=list.size();

        alerte2=new Dialog(getActivity());
        alerte2.setContentView(R.layout.input_box2);
        search1=(EditText) alerte2.findViewById(R.id.tt1);
        list2=(ListView) alerte2.findViewById(R.id.list1);
        uni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int pos;
                pos = uni.getSelectedItemPosition();
                int iden = arg0.getId();

                if (iden == R.id.un) {
                    if (pos == 1) {

                        unn = uni.getSelectedItem().toString();
                        qt.setEnabled(true);
                        qtcar.setEnabled(false);
                        qtpal.setEnabled(false);


                    }
                    if (pos == 2) {

                        car = uni.getSelectedItem().toString();
                        qt.setEnabled(false);
                        qtcar.setEnabled(true);
                        qtpal.setEnabled(false);



                    }
                    if (pos == 3) {

                        pal = uni.getSelectedItem().toString();
                        qt.setEnabled(false);
                        qtcar.setEnabled(false);
                        qtpal.setEnabled(true);



                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });









        if (haveNetworkConnection()) {


        }else{
            loadSpinnerArticle();
        }


        btajout=(Button) v.findViewById(R.id.ajouter) ;
        qt=(EditText)v.findViewById(R.id.quantite);
        qtcar=(EditText)v.findViewById(R.id.quantitecar);
        qtpal=(EditText)v.findViewById(R.id.quantitepal);



        qt.setEnabled(false);
        qtcar.setEnabled(false);
        qtpal.setEnabled(false);





        lst=(ListView) v.findViewById(R.id.list);




        article.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showalertearticle();

            }
        });
        String[] unnn = new String[]{
                "UN", "CAR","PAL"
        };

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item, unnn);
        uni.setAdapter(adapter1);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {



              if (haveNetworkConnection()) {

                        final HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
                        nom= (String) map.get("nomaricle") ;
                        id_article= (String) map.get("numaricle") ;
                        abreviation= (String) map.get("abreviation") ;

                  article.setText(abreviation);
                  final ViewTooltip.TooltipView viewTooltip = ViewTooltip
                          .on(article)
                          .position(ViewTooltip.Position.BOTTOM)
                          .text(nom)
                          .show();
                  unn="";
                  pal="";
                  car="";
                  qt.setEnabled(false);
                  qtcar.setEnabled(false);
                  qtpal.setEnabled(false);
                  qt.setText("");
                  qtcar.setText("");
                  qtpal.setText("");
                  uni.setSelection(0);


                  //new DownloadJSONUNITE().execute();



                        alerte2.dismiss();



                }else {
                    final HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);

                    nom = (String) map.get("nomaricle");
                    id_article = (String) map.get("numarticle");
                  abreviation= (String) map.get("abreviation") ;

                  article.setText(abreviation);
                  final ViewTooltip.TooltipView viewTooltip = ViewTooltip
                          .on(article)
                          .position(ViewTooltip.Position.BOTTOM)
                          .text(nom)
                          .show();
                  unn="";
                  pal="";
                  car="";
                  qt.setEnabled(false);
                  qtcar.setEnabled(false);
                  qtpal.setEnabled(false);
                  qt.setText("");
                  qtcar.setText("");
                  qtpal.setText("");
                  uni.setSelection(0);


                  alerte2.dismiss();

                }

            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getActivity(), Login.numsite, Toast.LENGTH_LONG).show();

                if (article.getText().toString().equals("")) {
                    article.setError("Veuiller remplir ce champ");


                }




                else if (unitee.equals("Choisir une unité")) {
                    uni.setError("Veuiller remplir ce champ");

                }
                else if (qt.getText().toString().equals("")&& (qtcar.getText().toString().equals(""))
                        && (qtpal.getText().toString().equals(""))) {
                    qt.setError("Veuiller remplir ce champ");
                    qtcar.setError("Veuiller remplir ce champ");
                    qtpal.setError("Veuiller remplir ce champ");





                }
                else {

                            //new DownloadJSONSTOC().execute();




                  {
                            if (qt.getText().toString().equals(""))
                            {

                            }else {
                                RowItem item = new RowItem(id_article, nom, qt.getText().toString(), unn);
                                list.add(item);
                                adapter = new CustomListViewadapter(getActivity(),
                                        R.layout.row_article, list);
                                adapter.notifyDataSetChanged();


                                lst.setAdapter(adapter);
                                unn="";
                            }






                                if (qtcar.getText().toString().equals("")) {

                                }else
                                {
                                    RowItem item1 = new RowItem(id_article, nom, qtcar.getText().toString(), car);
                                    list.add(item1);
                                    adapter = new CustomListViewadapter(getActivity(),
                                            R.layout.row_article, list);
                                    adapter.notifyDataSetChanged();


                                    lst.setAdapter(adapter);
                                    car="";
                                }




                                if(qtpal.getText().toString().equals(""))
                                {

                                }else
                                {
                                    RowItem item2 = new RowItem(id_article, nom, qtpal.getText().toString(), pal);

                                    list.add(item2);
                                    adapter = new CustomListViewadapter(getActivity(),
                                            R.layout.row_article, list);
                                    adapter.notifyDataSetChanged();


                                    lst.setAdapter(adapter);
                                    pal="";
                                }













                        size=size+1;


                        article.requestFocus();
                        String listee = "";
                        RowItem[] array = new RowItem[list.size()];
                        Gson gson = new Gson();
                        json = new Gson().toJson(list);


                        lin1.requestFocus();
                        qt.setText("");
                        qt.setEnabled(false);
                        qtcar.setText("");
                      qtcar.setEnabled(false);
                       qtpal.setText("");
                      qtpal.setEnabled(false);

                        article.setText("");
                        uni.setSelection(0);
                        search1.setText("");
                      View view = getActivity().getCurrentFocus();
                      if (view != null) {
                          InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                          imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                      }


                    }





                    //Toast.makeText(getActivity(), client.id_client, Toast.LENGTH_SHORT).show();



                }
                //new DownloadJSON2().execute();
            }
        };
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box




                 position1=position;
                    //categorie = spinner1.getSelectedItem().toString();

                String articlemodif;
                String articlemodifnom;
                String articlemodifunite;
                String articlemodifid;
                articlemodif = list.get(position).getQuant();
                articlemodifnom= list.get(position).getTitle();
                articlemodifunite= list.get(position).getUnite();
                articlemodifid= list.get(position).getId();
                showInputBox(articlemodifid,articlemodif,articlemodifnom,articlemodifunite,position);
                //showInputBox();



            }
        });
        btajout.setOnClickListener(listener);
        return v;

    }

    public void showInputBox(String idarticle,String oldItem,String oldIarticle,String oldIunite, final int index){
   /// public void showInputBox(){
        LinearLayout linearLayout = new LinearLayout(getContext()); // declaration d'un linear layout  text avec java
        linearLayout.setLayoutParams(new LayoutParams( // specifier la position et parametrre de linear layout
                LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        linearLayout.setPadding(30, 0, 30, 0);

        dialog.setTitle("Article");
        dialog.setContentView(R.layout.input_box);


        TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
      articlebox=(AutoCompleteTextView) dialog.findViewById(R.id.autoCompleteTextViewbox1) ;
        rech=(Button) dialog.findViewById(R.id.button1);
        Button rech2=(Button) dialog.findViewById(R.id.button2);

        if(haveNetworkConnection())
        {
            new  DownloadJSONARTICLEBOX().execute();
        }else
        {
                loadSpinnerArticlebox();
        }

        rech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub




                if(articlebox.getText().toString().isEmpty())
                {
                    articlebox.showDropDown();

                    articlebox.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(articlebox, InputMethodManager.SHOW_IMPLICIT);




                }else{


                    articlebox.setText("");



                }



            }
        });

        rech2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub




                if(editText2.getText().toString().isEmpty())
                {
                    editText2.showDropDown();

                    editText2.requestFocus();




                }else{


                    editText2.setText("");



                }



            }
        });

        articlebox.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(haveNetworkConnection())
                {
                    final HashMap<String, Object> map = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                    nombox= (String) map.get("des") ;
                    id_articlebox= (String) map.get("itm") ;
                    articlebox.setText(nombox);
                }else
                {
                    final HashMap<String, Object> map = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                    nombox= (String) map.get("nomaricle") ;
                    id_articlebox= (String) map.get("numarticle") ;
                    articlebox.setText(nombox);
                }


            }
        });


        articlebox.setText(oldIarticle);


        txtMessage.setText("Modifier /Supprimer Article");
       // new DownloadJSON3().execute();


        //txtMessage.setTextColor(Color.parseColor("#ff2222"));
      editText1=(EditText)dialog.findViewById(R.id.txtinput);
        editText2=(AutoCompleteTextView)dialog.findViewById(R.id.txtinput1);

        /////////////////////////////////////////




        String[] pr = new String[]{
                "UN",
                "CAR",
                "PAL"


        };
        List<String> prr = new ArrayList<>(Arrays.asList(pr));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item,prr);
        //////////////

        editText1.setText(oldItem);
        editText2.setText(oldIunite);
        editText2.setThreshold(1);
        editText2.setAdapter( adapter);

        Button bt=(Button)dialog.findViewById(R.id.btmodif);
        Button bt1=(Button)dialog.findViewById(R.id.btsupp);
        Button bt2=(Button)dialog.findViewById(R.id.btanull);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new Asyncmodif().execute();

               // editText3.setText(String.valueOf(position));

               //Toast.makeText(getActivity(),String.valueOf(position1) , Toast.LENGTH_LONG).show();


               /*if (Integer.parseInt(editText1.getText().toString())<=Integer.parseInt(Stk))
                {
                    RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

                    list.set(index,item);
                    //list.remove(index);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getActivity(),"Stock Insuffisant" , Toast.LENGTH_LONG).show();



                    RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

                    list.set(index,item);
                    //list.remove(index);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    dialog.dismiss();

                }





               /* RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

                list.set(index,item);
                //list.remove(index);
                adapter.notifyDataSetChanged();
                dialog.dismiss();*/
            }
        });
       bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Asyncsupp().execute();
                /*RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

                //list.set(index,item);
                list.remove(index);
                adapter.notifyDataSetChanged();


                dialog.dismiss();*/
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
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
                            mapPPPP.put("des", json_data.getString("YDES"));
                            mapPPPP.put("itm", json_data.getString("YITM"));
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
                            new String[]{"des"}, new int[]{R.id.art1});


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
                loadSpinnerArticle();

            }





        }

    }


    private void loadSpinnerData() {
        // database handler

        DatabaseHandler1 db = new DatabaseHandler1(getActivity());

        // Spinner Drop down elements
        List<String> articlee = db.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, articlee);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp_article.setAdapter(dataAdapter);
    }

    public class DownloadJSONUNITE extends AsyncTask<Void, Void, Void> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            InputStream is;
            if (Ping.testconappache()) {
                String URL = Connexion.c;
                String SOAP_ACTION = "http://connexion/Unite";
                String METHOD_NAME = "Unite";
                String NAMESPACE = "http://connexion";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                //request.addProperty("XFCY","AI2");

                request.addProperty("XITM", id_article);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE a1 = new HttpTransportSE(URL);
                a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                a1.debug = true;
                try {
                    a1.call(SOAP_ACTION, envelope);

                    rest = envelope.getResponse().toString();
                    System.out.println("reponse : " + rest);


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
                    JSONObject json = JA.getJSONObject("GRP1");
                    for (int i = 0; i < json.length(); i++) {


                        mapPPPP = new HashMap<String, Object>();

                        mapPPPP.put("numarticle", json.getString("XITM"));
                        mapPPPP.put("unite", json.getString("RES"));
                        //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                        listItem.add(mapPPPP);


                    }

                } catch (JSONException e) {
                    Log.i("tagjsonexp", "" + e.toString());
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            if (Ping.testconappache()) {
                try {
                    String[] unn = new String[]{
                            listItem.get(0).get("unite").toString()
                    };


                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            getActivity(), android.R.layout.simple_spinner_dropdown_item, unn);
                    uni.setAdapter(adapter);
                    uni.setSelection(1);


                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }else
            {
                String[] unnn = new String[]{
                        "UN", "TO"
                };

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_dropdown_item, unnn);
                uni.setAdapter(adapter);
            }









        }

    }
    public class DownloadJSONSTOC extends AsyncTask<Void, Void, Void> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Vérification du Stock...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/Dispo";
            String METHOD_NAME = "Dispo";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            //request.addProperty("XFCY","AI2");

            request.addProperty("ARTICLE",id_article);
            request.addProperty("SITE",Login.numsite);

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
                JSONObject json = JA.getJSONObject("GRP1");
                for (int i = 0; i < json.length(); i++) {




                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("stock", json.getString("STOCK"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }




            //conversion de la réponse en chaine de caractère

            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            progressDialog1.dismiss();
            String stock= listItem.get(1).get("stock").toString();
            Float stoc = Float.parseFloat(stock);

            Float quan= Float.parseFloat(qt.getText().toString());
            int retval = Float.compare(quan, stoc);
            if ((retval<=0))
            {
                //unite_article= unite.getText().toString();
                RowItem item = new RowItem(id_article, nom, qt.getText().toString(), unitee);
                list.add(item);

                adapter = new CustomListViewadapter(getActivity(),
                        R.layout.row_article, list);

                lst.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                size=size+1;

                article.requestFocus();

                String listee = "";
                RowItem[] array = new RowItem[list.size()];
                Gson gson = new Gson();
                json = new Gson().toJson(list);


                lin1.requestFocus();

                qt.setText("");
                article.setText("");
                uni.setSelection(0);
                search1.setText("");
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(article, InputMethodManager.SHOW_IMPLICIT);




            }else {
                AlertDialog.Builder alerte3 = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
                alerte3.setTitle("Stock Insuffisant"); // titre de l'alerte
                // message de la'lerte
                alerte3.setMessage("Voulez vous ajoutez cet article ? ");
                alerte3.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //unite_article= unite.getText().toString();
                        RowItem item = new RowItem(id_article, nom, qt.getText().toString(), unitee);
                        list.add(item);

                        adapter = new CustomListViewadapter(getActivity(),
                                R.layout.row_article, list);

                        lst.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        size=size+1;


                        article.requestFocus();
                        String listee = "";
                        RowItem[] array = new RowItem[list.size()];
                        Gson gson = new Gson();
                        json = new Gson().toJson(list);


                        lin1.requestFocus();
                        qt.setText("");
                        article.setText("");
                        uni.setSelection(0);
                        search1.setText("");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(article, InputMethodManager.SHOW_IMPLICIT);



                    }
                });


                alerte3.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        lin1.requestFocus();
                        qt.setText("");
                        article.setText("");
                        uni.setSelection(0);
                        search1.setText("");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(article, InputMethodManager.SHOW_IMPLICIT);
                    }
                    // fermer


                });
                alerte3.show(); // afficher l'alerte de dialogue


            }












        }

    }


    public  class DownloadJSONARTICLEBOX extends AsyncTask<Void, Void, Void> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

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

                rest = envelope.getResponse().toString();
                System.out.println("reponse : "+rest);





            }
            catch (java.lang.NullPointerException exception) {
                   /* loadSpinnerArticle();
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
                JSONArray json = JA.getJSONArray("GRP2");
                for (int i = 0; i < json.length(); i++) {

                    json_data = json.getJSONObject(i);

                    if(json_data.getString("YDES").isEmpty()&&json_data.getString("YITM").isEmpty()){

                    }else {
                        mapPPPP = new HashMap<String, Object>();

                        mapPPPP.put("des", json_data.getString("YDES"));
                        mapPPPP.put("itm", json_data.getString("YITM"));
                        mapPPPP.put("abreviation", json_data.getString("YABR"));

                        //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                        listItem.add(mapPPPP);
                    }


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
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

                final ArrayList<Map<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema, R.layout.item_article,
                        new String[]{"abreviation"}, new int[]{R.id.art1});


                SocialFragment.articlebox.setThreshold(1);
                SocialFragment.articlebox.setAdapter(mSchedule);


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

    public class DownloadJSONUNITEBOX extends AsyncTask<Void, Void, Void> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, Object> mapPPPP = null;
            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/Unite";
            String METHOD_NAME = "Unite";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            //request.addProperty("XFCY","AI2");

            request.addProperty("XITM", id_articlebox);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : " + rest);


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
                JSONObject json = JA.getJSONObject("GRP1");
                for (int i = 0; i < json.length(); i++) {


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("numarticle", json.getString("XITM"));
                    mapPPPP.put("unite", json.getString("RES"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }


            //conversion de la réponse en chaine de caractère

            return null;
        }


        @Override
        protected void onPostExecute(Void args) {
            try {
                String[] unn1 = new String[]{
                        listItem.get(0).get("unite").toString()
                };


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(),android.R.layout.simple_spinner_dropdown_item,unn1);
                editText2.setText(listItem.get(1).get("unite").toString());
                editText2.setAdapter(adapter);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            //text.setText("");


        }





    }
    class DownloadJSONSTOCALERTE extends AsyncTask<Void, Void, String> {
        String rest;

        ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Vérification du Stock...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }


        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {
            Map<String, Object> mapPPPP = null;
            InputStream is;
            String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/Dispo";
            String METHOD_NAME = "Dispo";
            String NAMESPACE = "http://connexion";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            //request.addProperty("XFCY","AI2");

            request.addProperty("ARTICLE", id_articlebox);
            request.addProperty("SITE", Login.numsite);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : " + rest);


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
                JSONObject json = JA.getJSONObject("GRP1");
                for (int i = 0; i < json.length(); i++) {


                    mapPPPP = new HashMap<String, Object>();

                    mapPPPP.put("stock", json.getString("STOCK"));
                    //mapPPPP.put("ville", json_data.getString("DES1AXX"));

                    listItem.add(mapPPPP);


                }

            } catch (JSONException e) {
                Log.i("tagjsonexp", "" + e.toString());
            }



            return "Success";
            // (null);
        }



        @Override
        protected void onPostExecute(String sResponse) {
            progressDialog1.dismiss();
           String stockk= listItem.get(1).get("stock").toString();
            Float stocc = Float.parseFloat(stockk);
            int retval = Float.compare(Float.parseFloat(editText1.getText().toString()), stocc);
            if(retval<=0)
            {
                RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
                list.set(position1,item);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
               // list.remove(index);

            }
            else {
                dialog.dismiss();

                AlertDialog.Builder alerte3 = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
                alerte3.setTitle("Stock Insuffisant"); // titre de l'alerte
                // message de la'lerte
                alerte3.setMessage("Voulez vous modifiez cette ligne ? ");
                alerte3.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //unite_article= unite.getText().toString();
                        RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());
                        list.set(position1,item);
                        adapter.notifyDataSetChanged();


                    }
                });


                alerte3.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                    // fermer


                });
                alerte3.show(); // afficher l'alerte de dialogue

            }


                //list.remove(index);

            //quann= Integer.parseInt(editText1.getText().toString());


        }

    }

    class Asyncsupp extends AsyncTask<Void, Void, String> {


        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {



            return "Success";
            // (null);
        }



        @Override
        protected void onPostExecute(String sResponse) {
            RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

            //list.set(index,item);
            list.remove(position1);
            adapter.notifyDataSetChanged();


            dialog.dismiss();
        }
    }
    class Asyncmodif extends AsyncTask<Void, Void, String> {


        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {



            return "Success";
            // (null);
        }



        @Override
        protected void onPostExecute(String sResponse) {
            RowItem item = new RowItem(id_articlebox,articlebox.getText().toString(),editText1.getText().toString(),editText2.getText().toString());

            list.set(position1,item);
            //list.remove(index);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }



    public void showalertearticle(){

        alerte2.setTitle("Choisir un Article"); // titre de l'alerte


        alerte2.show();
    }



    public      void loadSpinnerArticle(){

        ArticleRepo repo = new ArticleRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
       final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item_article,
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


    }
    public      void loadSpinnerArticlebox(){

        ArticleRepo repo = new ArticleRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item_article,
                new String[]{"abreviation"}, new int[]{R.id.art1});
        articlebox.setThreshold(1);
       articlebox.setAdapter(mSchedule);


    }


    public      void loadSpinnerArticledialog(){

        ArticleRepo repo = new ArticleRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.item_article,
                new String[]{"nomaricle"}, new int[]{R.id.art1});

        articlebox.setAdapter(mSchedule);


    }



}
