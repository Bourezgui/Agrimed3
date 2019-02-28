package com.example.ynaccache.agrimed2.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ynaccache.agrimed2.R;
import com.example.ynaccache.agrimed2.fragment.RowItem;
import com.example.ynaccache.agrimed2.fragment.SocialFragment;
import com.example.ynaccache.agrimed2.fragment.autre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class test2 extends AppCompatActivity {
     public static  String numclient;
    public static  String id_commande;
    public ListView list;
    public Dialog alerte;
    public EditText search;
    Button bt1;
    String bac ;

    AutoCompleteTextView  cli;
    AutoCompleteTextView  cli1,cli2;
    Button b1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
          cli = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        cli1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        cli2 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView4);
        b1 = (Button) findViewById(R.id.bt1);


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),cli.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });



        alerte=new Dialog(test2.this);
        alerte.setContentView(R.layout.input_box1);
        search=(EditText) alerte.findViewById(R.id.tt1);

        list=(ListView) alerte.findViewById(R.id.list1);
        cli.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showInputBox();

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                final HashMap<String, Object> map = (HashMap<String, Object>) list.getItemAtPosition(position);
                id_commande=(String) map.get("titre") ;
                numclient=(String) map.get("description") ;
                cli.setText(id_commande);
                cli1.setText(numclient);
                bac= cli1.getText().toString();
                cli2.setText(bac);
                alerte.dismiss();











                // Toast.makeText(getActivity(), nomsite,Toast.LENGTH_SHORT).show();

            }
        });







    }
    public void showInputBox(){
        /// public void showInputBox(){

      // decalartyion d'alerte dialogue



        alerte.setTitle("Validation Commande"); // titre de l'alerte
        // message de la'lerte
       // ListView list = ((AlertDialog) dialog).getListView();




        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        map = new HashMap<String, String>();

        map.put("titre", "youssef");
        map.put("description", "fff ");
        listItem.add(map);


       final SimpleAdapter mSchedule = new SimpleAdapter (this.getApplicationContext(), listItem, R.layout.item_spinner,
                new String[] { "titre", "description"}, new int[] {R.id.sujet, R.id.img});
       list.setAdapter(mSchedule);

        search.addTextChangedListener(new TextWatcher() {

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
        alerte.show();
    }

}
