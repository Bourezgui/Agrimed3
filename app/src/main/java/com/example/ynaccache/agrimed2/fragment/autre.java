package com.example.ynaccache.agrimed2.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.activity.Drawer;
import com.example.ynaccache.agrimed2.activity.Login;
import com.example.ynaccache.agrimed2.model.commande;
import com.example.ynaccache.agrimed2.model.details;
import com.example.ynaccache.agrimed2.other.AlertDialogManager;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.other.util;
import com.example.ynaccache.agrimed2.sqlite.UserRepo;
import com.example.ynaccache.agrimed2.sqlite.commandeRepo;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.sqlite.ArticleRepo;
import com.example.ynaccache.agrimed2.sqlite.ClientRepo;

import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

import static com.example.ynaccache.agrimed2.fragment.SocialFragment.adapter;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.article;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.date1;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.date2;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.list;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.list1;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.lst;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.req3;
import static com.example.ynaccache.agrimed2.fragment.SocialFragment.size;
import static com.example.ynaccache.agrimed2.fragment.client.annee;
import static com.example.ynaccache.agrimed2.fragment.client.dateliv;


public class autre extends Fragment {

    AlertDialogManager alert = new AlertDialogManager();

    //////////////////////VPN//////////////
    NetworkCapabilities caps1,caps2,caps3;
    ConnectivityManager cm;
    Network[] networks ;
    public String numcommande;




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





   public static MaterialSpinner spinner7;
    public EditText observ;
   long  _Commande_Id,article_ID;
    public static String priorite="";
    public static String observation;
    public static String etat="En attente";
    public static String etat1="Envoyé";

    public int year ,month;
    String monthString;
    public  int pos1;
    public String jour,annee,moi;
    public static   Button valider,valider1;
    int prio;
    View v;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_autre, container, false);
      valider=(Button) v.findViewById(R.id.valid) ;
        commandeRepo repo = new commandeRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList();
        // numcommande = siteList.get(0).get("id1").toString();
        if (siteList.size()==0)
        {
            numcommande = util.convertor( "1", 5);

        }else {
            numcommande = util.convertor(String.valueOf(Integer.parseInt(siteList.get(0).get("id1").toString()) + 1), 5);
        }
        spinner7=(MaterialSpinner) v.findViewById(R.id.autre) ;
        observ=(EditText) v.findViewById(R.id.observation) ;

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);




        String[] pr = new String[]{
                "Haute",
                "Normale",
                "Faible"

        };

         List<String> prr = new ArrayList<>(Arrays.asList(pr));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item,prr);
        spinner7.setAdapter(adapter);

        spinner7.setSelection(pos1);

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                pos1 = spinner7.getSelectedItemPosition();
                int iden = arg0.getId();

                if (iden == R.id.autre) {



                        //categorie = spinner1.getSelectedItem().toString();
                        pos1= spinner7.getSelectedItemPosition();


                    if(pos1 == 1)
                    {
                        prio=3;
                    }else if (pos1 == 2)
                    {
                        prio=2;

                    }else
                    {
                        prio=1;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


     valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateliv=client.ed2.getText().toString();
                priorite= spinner7.getSelectedItem().toString();



                //
                try {
                    SimpleDateFormat formatYmd = new SimpleDateFormat("dd/MM/yyyy");
                    date1 = formatYmd.parse(client.datecommande);
                    date2 = formatYmd.parse(dateliv);




                }catch (ParseException e) {
                    e.printStackTrace();
                }

                if (client.cli.getText().toString().equals("") || client.text.getText().toString().equals("") || client.datecommande.equals("") || client.ed2.getText().toString().equals("")|| priorite.equals("Choisir Priorité commande") ||observ.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Veuillez remplir tout les champs", Toast.LENGTH_LONG).show();
                }
                else if(date2.before(date1))
                {
                    Toast.makeText(getActivity(), "La date de livraison doit etre aprés la date de commande ", Toast.LENGTH_LONG).show();

                } else if (list.size()==0)
                {
                    Toast.makeText(getActivity(), "Liste des articles est vide", Toast.LENGTH_LONG).show();

                }

                else {
                    final AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
                    alerte.setTitle("Validation Commande"); // titre de l'alerte
                    // message de la'lerte
                    alerte.setMessage("Vouler vous valider cette commande ? ");
                    alerte.setPositiveButton("Valider", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            observation= observ.getText().toString();
                            Calendar c = Calendar.getInstance();
                            year = c.get(Calendar.YEAR);
                            month = c.get(Calendar.MONTH)+1;

                            switch (month) {
                                case 1:  monthString = "Jan";
                                    break;
                                case 2:  monthString = "Feb";
                                    break;
                                case 3:  monthString = "Mar";
                                    break;
                                case 4:  monthString = "Apr";
                                    break;
                                case 5:  monthString = "May";
                                    break;
                                case 6:  monthString = "Jun";
                                    break;
                                case 7:  monthString = "Jul";
                                    break;
                                case 8:  monthString = "Aout";
                                    break;
                                case 9:  monthString = "Sep";
                                    break;
                                case 10: monthString = "Oct";
                                    break;
                                case 11: monthString = "Nov";
                                    break;
                                case 12: monthString = "Dec";
                                    break;
                                default: monthString = "Invalid month";
                                    break;
                            }

                            String[] parts = dateliv.split("/");
                            jour = parts[0];
                            moi= parts[1];
                            annee=parts[2];
                                if (haveNetworkConnection()) {
                                       new UploadSoapDataAsynck().execute();

                                   // Toast.makeText(getActivity(), String.valueOf(list.size()), Toast.LENGTH_LONG).show();






                                }else
                                {

                                    //Toast.makeText(getActivity(), numcommande,Toast.LENGTH_SHORT).show();
                                    insertoffline();
                                }





                            //insertoffline();




                        }
                    });

                    alerte.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                        // fermer


                    });


                    alerte.show(); // afficher l'alerte de dialogue




                /*MySQLiteHelper db = new MySQLiteHelper(getActivity());
                db.addcommande(new commandelisteee(client.client,client.datecommande,dateliv));

                Toast.makeText(getActivity(), "record inserted", Toast.LENGTH_LONG).show();*/



                }
            }
        });



        return v;
    }
    class UploadSoapDataAsynck extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog1;
        String rest;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Merci de patienter...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
        }


        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {
            InputStream is;
            {


                String URL = Connexion.c;
            String SOAP_ACTION = "http://connexion/commande";
            String METHOD_NAME = "commande";
            String NAMESPACE = "http://connexion";
            String youssef = "20171113";
                String req9 ="<PARAM>\n" +
                        "\t<GRP ID=\"YOX0_1\" >\n" +
                        "\t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                        "\t</GRP>\n" +
                        "\t<GRP ID=\"YOX1_1\" >\n" +
                        "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\" >AGA</FLD>\n" +
                        "\t\t<FLD NAME=\"YBPCNUM\" TYPE=\"Char\" >110105</FLD>\n" +
                        "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >20171004</FLD>\n" +
                        "\t\t<FLD NAME=\"YDATLIV\" TYPE=\"Date\" >20171004</FLD>\n" +
                        "\t</GRP>\n" +
                        "\t<GRP ID=\"YOX1_2\" >\n" +
                        "\t\t <FLD  NAME=\"YPRIOR\" TYPE=\"Integer\" >2</FLD>\n" +
                      //  "\t\t <FLD  NAME=\"YOPERATEUR\" TYPE=\"Char\" >ABO</FLD>\n" +

                        "\t\t <FLD NAME=\"YOBSERV\" TYPE=\"Char\" >Hhh</FLD>\n" +
                       // "\t\t <FLD NAME=\"YAP\" TYPE=\"Char\" >S4</FLD>\n" +

                        "\t </GRP>\n" +
                        "\t<GRP ID=\"ADXTEC\" >\n" +
                        "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                        "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                        "\t</GRP>\n" +
                        "\t<TAB DIM=\"10\" ID=\"YOX2_1\" SIZE=\"1\" >\n" +
                        "\t\t<LIN NUM=\"1\" >\n" +
                        "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FLM01800</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >CAR</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >12</FLD>\n" +
                        "\t\t</LIN>\n" +
                        "\t</TAB>\n" +

                        "</PARAM>";
          String req = "<PARAM>\n" +
                    "\t<GRP ID=\"YOX0_1\" >\n" +
                    "\t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                  //"\t\t<FLD NAME=\"YNUMTEL\" TYPE=\"Char\" >"+"MOB"+annee+moi+Login.prefix+client.id_site+numcommande+"</FLD>\n" +

                  "\t</GRP>\n" +
                    "\t<GRP ID=\"YOX1_1\" >\n" +
                    "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\" >" + client.id_site + "</FLD>\n" +
                    // "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\" >" + "RAD" + "</FLD>\n" +

                    "\t\t<FLD NAME=\"YBPCNUM\" TYPE=\"Char\" >" + client.id_client + "</FLD>\n" +
                    "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >" + client.annee + client.moi + client.jour + "</FLD>\n" +
                    "\t\t<FLD NAME=\"YDATLIV\" TYPE=\"Date\" >" + annee + moi + jour + "</FLD>\n" +
                    "\t</GRP>\n" +

                    "\t<GRP ID=\"YOX1_2\" >\n" +
                    "\t\t<FLD  NAME=\"YPRIOR\" TYPE=\"Integer\" >" + String.valueOf(prio) + "</FLD>\n" +
                    //"\t\t<FLD NAME=\"CREUSR\" TYPE=\"Char\" >SRA</FLD>\n" +
                    "\t\t <FLD  NAME=\"YOPERATEUR\" TYPE=\"Char\" >"+Login.codeutulisateur+"</FLD>\n" +

                   //"\t\t<FLD NAME=\"ZYOPERATEUR\" TYPE=\"Char\"></FLD>\n" +

                    "\t\t<FLD NAME=\"YOBSERV\" TYPE=\"Char\" >"+observation+"</FLD>\n" +
                  //  "\t\t<FLD NAME=\"YAP\" TYPE=\"Char\" >"+Login.prefix+"</FLD>\n" +
                  "\t\t<FLD NAME=\"YAP\" TYPE=\"Char\" >"+Login.prefix+"</FLD>\n" +


                  // "\t\t<FLD NAME=\"YOBSERV\" TYPE=\"Char\" >" + "Appareils: " + Login.prefix + "   " +
                   // "Commercial:" + "  " + Login.nom + " " + Login.prenom + "  "
                   // + "Observation:" + "   " + " " + observation + "</FLD>\n" +


                    "\t</GRP>\n" +

                    "\t<GRP ID=\"ADXTEC\" >\n" +
                    "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                    "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                    "\t</GRP>\n";



            String req2 = "\t<TAB DIM=\"1000\" ID=\"YOX2_1\" SIZE=\"" + list.size() + "\" >\n";
            ////// dynami
            for (int i = 0; i < list.size(); i++) {
                req3 = req3 + "\t\t<LIN NUM=\"" + (i + 1) + "\" >\n" +
                        "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >" + SocialFragment.list.get(i).getId() + "</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >" + SocialFragment.list.get(i).getUnite() + "</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >" + SocialFragment.list.get(i).getQuant().toString() + "</FLD>\n" +
                        "\t\t</LIN>\n";
                ////////////////////////dyn


                //////non dynamique/////


            }


            String req4 = "\t</TAB>\n" + "</PARAM>";

            String req5 = req + req2 + req3 + req4;
            System.out.print(req5);
            req3 = "";
           String req7="<PARAM>\n" +
                   " \t<GRP ID=\"YOX0_1\" >\n" +
                   " \t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                   " \t</GRP>\n" +
                   "\t<GRP ID=\"YOX1_1\" >\n" +
                   "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\" >RAD</FLD>\n" +
                   "\t\t<FLD NAME=\"YBPCNUM\" TYPE=\"Char\" >411019683</FLD>\n" +
                   "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >20180119</FLD>\n" +
                   "\t\t<FLD NAME=\"YDATLIV\" TYPE=\"Date\" >20180122</FLD>\n" +
                   "\t</GRP>\n" +
                   "\t<GRP ID=\"YOX1_2\" >\n" +
                   "\t\t<FLD  NAME=\"YPRIOR\" TYPE=\"Integer\" >2</FLD>\n" +
                   "\t\t <FLD  NAME=\"YOPERATEUR\" TYPE=\"Char\" >ISSAM</FLD>\n" +
                   "\t\t<FLD NAME=\"YOBSERV\" TYPE=\"Char\" >Camion avec ayon</FLD>\n" +
                   "\t\t<FLD NAME=\"YAP\" TYPE=\"Char\" >M09</FLD>\n" +
                   "\t</GRP>\n" +
                   "\t<GRP ID=\"ADXTEC\" >\n" +
                   "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                   "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                   "\t</GRP>\n" +
                   "\t<TAB DIM=\"1000\" ID=\"YOX2_1\" SIZE=\"11\" >\n" +
                   "\t\t<LIN NUM=\"1\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FMP03000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >1</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"2\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FCP01000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >CAR</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >45</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"3\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FMP01800</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >3</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"4\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FMP01000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >2</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"5\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FMP05000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >2</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"6\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FVG01001</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >3</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"7\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FVG05000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >2</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"8\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FVG10000</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >1</FLD>\n" +
                   "\t\t</LIN>\n" +
                   "\t\t<LIN NUM=\"9\" >\n" +
                   "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FVG00900</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >2</FLD>\n" +
                   " \t\t</LIN>\n" +
                   " \t\t<LIN NUM=\"10\" >\n" +
                   " \t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FVG01800</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >2</FLD>\n" +
                   " \t\t</LIN>\n" +
                   " \t\t<LIN NUM=\"11\" >\n" +
                   " \t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >FLM01800</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\" >PAL</FLD>\n" +
                   " \t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >1</FLD>\n" +
                   " \t\t</LIN>\n" +
                   " \t</TAB>\n" +
                   " </PARAM>";

            /*String req8 = "<PARAM>\n" +
                    "\t<GRP ID=\"YOX0_1\" >\n" +
                    "\t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                    "\t</GRP>\n" +
                    "\t<GRP ID=\"YOX1_1\" >\n" +
                    "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\" >AO022</FLD>\n" +
                    "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >20171004</FLD>\n" +
                    "\t</GRP>\n" +
                    "\t<GRP ID=\"ADXTEC\" >\n" +
                    "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                    "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                    "\t</GRP>\n" +
                    "\t<TAB DIM=\"10\" ID=\"YOX2_1\" SIZE=\"2\" >\n" +
                    "\t\t<LIN NUM=\"1\" >\n" +
                    "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >ASS001</FLD>\n" +
                    "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >1</FLD>\n" +
                    "\t\t</LIN>\n" +
                    "\t\t<LIN NUM=\"2\" >\n" +
                    "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\" >ASS001</FLD>\n" +
                    "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\" >100</FLD>\n" +
                    "\t\t</LIN>\n" +
                    "\t</TAB>\n" +

                    "</PARAM>";*/
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11
            );
            request.addProperty("req", req5);

            //request.addProperty("XUNI",1);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            //AndroidHttpTransport a = new AndroidHttpTransport (URL);
            HttpTransportSE a1 = new HttpTransportSE(URL);
            a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            a1.debug = true;
            try {
                a1.call(SOAP_ACTION, envelope);

                rest = envelope.getResponse().toString();
                System.out.println("reponse : " + rest);

                if (rest.equals("1")) {
                    //todo/////


                    Log.i("succés", "succés");
                } else if (rest.equals("0")) {

                    Log.i("erreur", "erreur");


                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("erreur", e + "");

                e.printStackTrace();
            } catch (XmlPullParserException e) {


                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /////////////////////////////////////////////////////////////////////////
           /* List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("req", json));
            nameValuePairs.add(new BasicNameValuePair("datecommande", client.datecommande));
            nameValuePairs.add(new BasicNameValuePair("dateliv", dateliv));


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://10.0.3.3/reception%20json/commande2.php");


                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                is = entity.getContent();
                //myWebView.loadUrl("http://192.168.1.9/youssef3/youssef/index.php");

//                Toast.makeText(getApplicationContext(), "Votre insertion a été validée", Toast.LENGTH_SHORT).show();
                //String url = "http://192.168.1.9/youssef3/youssef/index.php";

                //Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(url));
                //startActivity(i);

                //startActivity(new Intent(getApplicationContext(), Voirconstat.class));


            } catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
            }*/

        }
            return "Success";
            // (null);
        }


        @Override
        protected void onPostExecute(String sResponse) {
                progressDialog1.dismiss();
                if (rest.equals("1")) {
                    insertoffline1();
                    final AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
                    alerte.setTitle("Succées Commande");
                    alerte.setIcon(R.drawable.success);
                    // titre de l'alerte
                    // message de la'lerte
                    alerte.setMessage("Votre Commande a été bien enregistrée");
                    alerte.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            startActivity(new Intent(getActivity(),
                                    Drawer.class));

                        }
                    });

                    alerte.show();

                } else {
                    insertoffline();

                }
                //Toast.makeText(getActivity(), rest.toString(), Toast.LENGTH_SHORT).show();
                list.clear();
            }

        }



            //pos1=0;











    public void insertoffline() {
        try {


        commandeRepo repo = new commandeRepo(getActivity());


        commande commande = new commande();
        commande.numclient = client.id_client;
        commande.nomclient = client.nom_client;
        commande.numsite = client.id_site;
        commande.nomsite = "AGAREB";
        commande.datecommande = client.datecommande;
        commande.dateliv = client.dateliv;
        commande.priorite = String.valueOf(prio);
        commande.observation = observation;
        commande.anneee = String.valueOf(year);
        commande.mois = monthString;
        commande.etat = etat;
        commande.commande_ID = _Commande_Id;

        _Commande_Id = repo.insert(commande);


        /////////////////////////////////////////// insertion article////////////


        for (int i = 0; i < list.size(); i++) {
            commandeRepo repo1 = new commandeRepo(getActivity());

            details details = new details();

            details.numarticle = list.get(i).getId().toString();
            details.nomarticle = list.get(i).getTitle();
            details.quantite = list.get(i).getQuant();
            details.unite = list.get(i).getUnite();
            details.numcommande = String.valueOf(_Commande_Id);
            article_ID = repo1.insert1(details);







        }
        final AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
        alerte.setTitle("Succées Commande");
        alerte.setIcon(R.drawable.success);
        // titre de l'alerte
        // message de la'lerte
        alerte.setMessage("Votre Commande a été bien enregistrée");
        alerte.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {



                list.clear();

                startActivity(new Intent(getActivity(),
                        Drawer.class));

            }
        });

        alerte.show();


        } catch (java.lang.NullPointerException exception) {
                   /* loadSpinnerArticle();
                   clicklistexception();*/
        } catch (Exception e) {
            Log.i("tagjsonexp", "" + e.toString());
        }

        //loadSpinnerData();

    }
   /* public void insertoffline1() {
        commandeRepo repo = new commandeRepo(getActivity());


        commande commande = new commande();
        commande.numclient = client.id_client;
        commande.nomclient = client.nom_client;
        commande.numsite = client.numsiteoff1;
        commande.nomsite = "AGAREB";
        commande.datecommande = client.datecommande;
        commande.dateliv = client.dateliv;
        commande.priorite = String.valueOf(prio);
        commande.observation = observation;
        commande.anneee = String.valueOf(year);
        commande.mois = monthString;
        commande.etat = etat1;
        commande.commande_ID = _Commande_Id;

        _Commande_Id = repo.insert(commande);


        /////////////////////////////////////////// insertion article////////////


        for (int i = 0; i < SocialFragment.size; i++) {
            commandeRepo repo1 = new commandeRepo(getActivity());

            details details = new details();

            details.numarticle = SocialFragment.list.get(i).getId().toString();
            details.nomarticle = SocialFragment.list.get(i).getTitle();
            details.quantite = SocialFragment.list.get(i).getQuant();
            details.unite = SocialFragment.list.get(i).getUnite();
            details.numcommande = String.valueOf(_Commande_Id);
            article_ID = repo1.insert1(details);







        }
        list.clear();





        //loadSpinnerData();

    }*/
   public void insertoffline1() {
       try
       {
       commandeRepo repo = new commandeRepo(getActivity());


       commande commande = new commande();
       commande.numclient = client.id_client;
       commande.nomclient = client.nom_client;
       commande.numsite = client.id_site;
       commande.nomsite = "AGAREB";
       commande.datecommande = client.datecommande;
       commande.dateliv = client.dateliv;
       commande.priorite = String.valueOf(prio);
       commande.observation = observation;
       commande.anneee = String.valueOf(year);
       commande.mois = monthString;
       commande.etat = etat1;
       commande.commande_ID = _Commande_Id;

       _Commande_Id = repo.insert(commande);


       /////////////////////////////////////////// insertion article////////////


       for (int i = 0; i < list.size(); i++) {
           commandeRepo repo1 = new commandeRepo(getActivity());

           details details = new details();

           details.numarticle = list.get(i).getId().toString();
           details.nomarticle = list.get(i).getTitle();
           details.quantite = list.get(i).getQuant();
           details.unite = list.get(i).getUnite();
           details.numcommande = String.valueOf(_Commande_Id);
           article_ID = repo1.insert1(details);







       }
       final AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
       alerte.setTitle("Succées Commande");
       alerte.setIcon(R.drawable.success);
       // titre de l'alerte
       // message de la'lerte
       alerte.setMessage("Votre Commande a été bien enregistrée");
       alerte.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

           @Override
           public void onClick(DialogInterface dialog, int which) {



               list.clear();

               startActivity(new Intent(getActivity(),
                       Drawer.class));

           }
       });

       alerte.show();


       } catch (java.lang.NullPointerException exception) {
                   /* loadSpinnerArticle();
                   clicklistexception();*/
       } catch (Exception e) {
           Log.i("tagjsonexp", "" + e.toString());
       }

       //loadSpinnerData();

   }


}



