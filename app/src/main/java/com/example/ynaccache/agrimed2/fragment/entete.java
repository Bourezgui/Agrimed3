package com.example.ynaccache.agrimed2.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ynaccache.agrimed2.R;


public class entete extends Fragment {
    EditText etat,site,nomsite,client,nomclient,datecommande,dateliv,priorité,observation;
    View v;




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










        etat.setText(PhotosFragment.etat);
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

     }
        observation.setText(PhotosFragment.observation);








        return v;
    }


}
