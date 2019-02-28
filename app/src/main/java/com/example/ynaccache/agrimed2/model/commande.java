package com.example.ynaccache.agrimed2.model;

/**
 * Created by y.naccache on 28/11/2017.
 */

public class commande {

    public static final String TABLE = "commande";
    public static final String KEY_ID = "id";
    public static final String KEY_numclient = "numclient";
    public static final String KEY_nomclient = "nomclient";
    public static final String KEY_numsite = "numsite";
    public static final String KEY_nomsite = "nomsite";
    public static final String KEY_datecommande = "datecommande";
    public static final String KEY_dateliv = "dateliv";
    public static final String KEY_priorite = "priorite";
    public static final String KEY_observation = "observation";
    public static final String KEY_etat= "etat";
    public static final String KEY_annnee= "anneee";
    public static final String KEY_mois= "mois";
    public static final String KEY_prefix= "prefix";






    public long commande_ID;
    public String numclient;
    public String nomclient;
    public String numsite;
    public String nomsite;
    public String datecommande;
    public String dateliv;
    public String priorite;
    public String observation;
    public String etat;
    public String anneee;
    public String mois;
    public String prefix;


}
