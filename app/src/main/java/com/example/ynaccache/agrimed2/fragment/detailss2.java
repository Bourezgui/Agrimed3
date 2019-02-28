package com.example.ynaccache.agrimed2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.sqlite.commandeRepo;

import java.util.ArrayList;
import java.util.HashMap;

public class detailss2 extends Fragment {
   public static  ListView lst;
    View v;
    // TODO: Rename parameter arguments, choose names that match

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detailss, container, false);
        lst=(ListView)v.findViewById(R.id.list);
      //  loadSpinnerData2();
        return v;
    }

    public      void loadSpinnerData2()
    {
        commandeRepo repo = new commandeRepo(getActivity());
        ArrayList<HashMap<String, String>> siteList = repo.getStudentList2(PhotosFragment.id_commande);
        SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), siteList, R.layout.row_article,
                new String[]{"nomarticle","quantite","unite"}, new int[]{R.id.titre,R.id.quant,R.id.unite});


        lst.setAdapter(mSchedule);


    };
}
