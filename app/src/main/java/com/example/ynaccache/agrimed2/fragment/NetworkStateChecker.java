package com.example.ynaccache.agrimed2.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.activity.*;
import com.example.ynaccache.agrimed2.activity.synchronisation;
import com.example.ynaccache.agrimed2.model.cli;
import com.example.ynaccache.agrimed2.model.commande;
import com.example.ynaccache.agrimed2.other.Connexion;
import com.example.ynaccache.agrimed2.other.Ping;
import com.example.ynaccache.agrimed2.sqlite.ClientRepo;
import com.example.ynaccache.agrimed2.sqlite.UserRepo;
import com.example.ynaccache.agrimed2.sqlite.commandeRepo;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.ynaccache.agrimed2.fragment.SocialFragment.req3;

/**
 * Created by Belal on 1/27/2017.
 */

public class NetworkStateChecker extends BroadcastReceiver {
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    //context and database helper object
    private Context context;

    ArrayList<HashMap<String, String>> siteList;
    commandeRepo repo;
    String jour,mois,annee;
    String jour1,mois1,annee1;

    String req5;



    @Override
    public void onReceive(Context context, Intent intent) {



        this.context = context;
        NetworkCapabilities caps1,caps2,caps3;

    /*  Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });*/

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        repo = new commandeRepo(context);
        siteList = repo.getStudentList1("En attente");

        if (haveNetworkConnection())
        {
            if(siteList.size()!=0) {
                if (Ping.testconappache()) {
                    new UploadSoapDataAsynck().execute();

                }
            }
        }






    }


    public class UploadSoapDataAsynck extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog1;
        String rest;





        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {
            InputStream is;


                String URL = Connexion.c;
                String SOAP_ACTION = "http://connexion/commande";
                String METHOD_NAME = "commande";
                String NAMESPACE = "http://connexion";

                for (int i = 0; i < siteList.size(); i++) {
                    String[] parts = siteList.get(i).get("datecommande").toString().split("/");
                    jour = parts[0];
                    mois = parts[1];
                    annee = parts[2];
                    String[] parts1 = siteList.get(i).get("dateliv").toString().split("/");
                    jour1 = parts1[0];
                    mois1 = parts1[1];
                    annee1 = parts1[2];
                    UserRepo user = new UserRepo(context);
                    ArrayList<HashMap<String, String>> lstuser = user.getStudentList();

                    String req = "<PARAM>\n" +
                            "\t<GRP ID=\"YOX0_1\" >\n" +
                            "\t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                            "\t</GRP>\n" +
                            "\t<GRP ID=\"YOX1_1\" >\n" +
                            "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\">" + siteList.get(i).get("numsite") + "</FLD>\n" +
                            "\t\t<FLD NAME=\"YBPCNUM\" TYPE=\"Char\" >" + siteList.get(i).get("numclient") + "</FLD>\n" +
                            "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >" + annee + mois + jour + "</FLD>\n" +
                            "\t\t<FLD NAME=\"YDATLIV\" TYPE=\"Date\" >" + annee1 + mois1 + jour1 + "</FLD>\n" +
                            "\t</GRP>\n" +

                            "\t<GRP ID=\"YOX1_2\" >\n" +
                            "\t\t<FLD MENULAB=\"\" MENULOCAL=\"6100\" NAME=\"YPRIOR\" TYPE=\"Integer\" >" + siteList.get(i).get("priorite") + "</FLD>\n" +
                           // "\t\t<FLD NAME=\"CREUSR\" TYPE=\"Char\" >SRA</FLD>\n" +
                            "\t\t <FLD NAME=\"YOPERATEUR\" TYPE=\"Char\" >"+lstuser.get(0).get("code")+"</FLD>\n" +
                            "\t\t<FLD NAME=\"YOBSERV\" TYPE=\"Char\" >"+  siteList.get(i).get("observation")+"</FLD>\n" +

                            "\t\t<FLD NAME=\"YAP\" TYPE=\"Char\" >"+lstuser.get(0).get("prefix")+"</FLD>\n" +


                            "\t</GRP>\n" +

                            "\t<GRP ID=\"ADXTEC\" >\n" +
                            "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                            "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                            "\t</GRP>\n";

                    commandeRepo repo1 = new commandeRepo(context);
                    ArrayList<HashMap<String, String>> siteList1 = repo1.getStudentList2(siteList.get(i).get("id"));

                    String req2 = "\t<TAB DIM=\"10\" ID=\"YOX2_1\" SIZE=\"" + siteList1.size() + "\" >\n";
                    ////// dynami
                    for (int j = 0; j < siteList1.size(); j++) {
                        req3 = req3 + "\t\t<LIN NUM=\"" + (j + 1) + "\" >\n" +
                                "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\">" + siteList1.get(j).get("numarticle") + "</FLD>\n" +
                                "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\">" + siteList1.get(j).get("unite") + "</FLD>\n" +
                                "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\">" + siteList1.get(j).get("quantite") + "</FLD>\n" +
                                "\t\t</LIN>\n";
                        ////////////////////////dyn


                        //////non dynamique/////


                    }


                    String req4 = "\t</TAB>\n" + "</PARAM>\n";

                    req5 = req + req2 + req3 + req4;

                    req3 = "";

                    System.out.print(req5);






          /*String[] parts = siteList.get(0).get("datecommande").toString().split("/");
            jour = parts[0];
            mois= parts[1];
            annee=parts[2];

            String[] parts1 = siteList.get(0).get("dateliv").toString().split("/");
            jour1 = parts1[0];
            mois1= parts1[1];
            annee1=parts1[2];

            String req = "<PARAM>\n" +
                    "\t<GRP ID=\"YOX0_1\" >\n" +
                    "\t\t<FLD NAME=\"YORDNUM\" TYPE=\"Char\" ></FLD>\n" +
                    "\t</GRP>\n" +
                    "\t<GRP ID=\"YOX1_1\" >\n" +
                    "\t\t<FLD NAME=\"YFCY\" TYPE=\"Char\">" + siteList.get(0).get("numsite") + "</FLD>\n" +
                    "\t\t<FLD NAME=\"YBPCNUM\" TYPE=\"Char\" >" +siteList.get(0).get("numclient") +"</FLD>\n" +
                    "\t\t<FLD NAME=\"YORDDAT\" TYPE=\"Date\" >" + annee + mois + jour + "</FLD>\n" +
                    "\t\t<FLD NAME=\"YDATLIV\" TYPE=\"Date\" >" + annee1 + mois1 + jour1+ "</FLD>\n" +
                    "\t</GRP>\n" +

                    "\t<GRP ID=\"YOX1_2\" >\n" +
                    "\t\t<FLD MENULAB=\"\" MENULOCAL=\"6100\" NAME=\"YPRIOR\" TYPE=\"Integer\" >"+siteList.get(0).get("priorite")+"</FLD>\n" +
                    "\t\t<FLD NAME=\"CREUSR\" TYPE=\"Char\" >SRA</FLD>\n" +
                    "\t</GRP>\n" +

                    "\t<GRP ID=\"ADXTEC\" >\n" +
                    "\t\t<FLD NAME=\"WW_MODSTAMP\" TYPE=\"Char\" ></FLD>\n" +
                    "\t\t<FLD NAME=\"WW_MODUSER\" TYPE=\"Char\" ></FLD>\n" +
                    "\t</GRP>\n";
            commandeRepo repo1 = new commandeRepo(getApplicationContext());
            ArrayList<HashMap<String, String>> siteList1 = repo1.getStudentList2(siteList.get(0).get("id"));

            String req2 = "\t<TAB DIM=\"10\" ID=\"YOX2_1\" SIZE=\"" + siteList1.size() + "\" >\n";
            ////// dynami
            for (int j = 0; j <siteList1.size(); j++) {
                req3 = req3 + "\t\t<LIN NUM=\"" + (j + 1) + "\" >\n" +
                        "\t\t\t<FLD NAME=\"YITMREF\" TYPE=\"Char\">" + siteList1.get(j).get("numarticle") + "</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YUOM\" TYPE=\"Char\">" + siteList1.get(j).get("quantite") + "</FLD>\n" +
                        "\t\t\t<FLD NAME=\"YQTY\" TYPE=\"Decimal\">" + siteList1.get(j).get("unite") + "</FLD>\n" +
                        "\t\t</LIN>\n";
                ////////////////////////dyn


                //////non dynamique/////


            }


            String req4 = "\t</TAB>\n" + "</PARAM>\n";

            String req5 = req + req2 + req3 + req4;
            System.out.print(req5);
            req="";
            req3 = "";*/
                    //System.out.print(req7);
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
                }



            return "Success";
            // (null);
        }


        @Override
        protected void onPostExecute(String sResponse) {
            {

                if (rest.equals("1")) {

                    for (int i = 0; i < siteList.size(); i++) {
                        commande commande = new commande();
                        commande.etat = "Envoyé";
                        commande.commande_ID = Integer.parseInt(siteList.get(i).get("id").toString());


                        repo.update(commande);


                        //loadSpinnerData();
                    }


                }
            }

        }




    }




    /*
    * method taking two arguments
    * name that is to be saved and id of the name from SQLite
    * if the name is successfully sent
    * we will update the status as synced in SQLite
    * */



}
