package com.example.samy.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class JournalActivity extends Fragment implements View.OnClickListener{

    View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_journal, container, false);

        updateList();

        return root;
    }

    @Override
    public void onClick(View v) {
        //Opens the journal editor activity, which when opened is a blank journal because the "load" variable has not been changed
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("load",-1);
        editor.commit();
        startActivity(new Intent(getActivity(), JournalEditorActivity.class));
        //getActivity().finish();
    }

    public void updateList(){
        //declare shared prefs
        final SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("load",-1);
        editor.putBoolean("update",false);
        editor.commit();

        //Update the nameArrayList based on what is stored in the shared prefs
        ArrayList<String> nameArrayList = new ArrayList<>();
        nameArrayList.clear();
        boolean a=true;
        for(int i=0;a;i++){
            if (sharedpreferences.getString("name"+ Integer.toString(i),"")!=""){
                nameArrayList.add(i,sharedpreferences.getString("name"+ Integer.toString(i),""));
            }
            else{
                a = false;
            }
            //Variables in the Shared prefs are stored as name0, name1, name2
        }

        //Create the Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),R.layout.list_view_layout,nameArrayList);
        ListView listView = (ListView) root.findViewById(R.id.listView);
        listView.clearChoices();
        listView.setAdapter(adapter);

        //On Click listener for individual items in the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //save the position that was clicked so that it can be loaded by the editor
                editor.putInt("load", position);
                editor.commit();

                //start the journal editor
                startActivity(new Intent(getActivity(), JournalEditorActivity.class));

            }
        });

        //set the onClickListener for the new journal button
        ImageButton imageButton = (ImageButton)root.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (sharedpreferences.getBoolean("update",false)==true) {
                            adaptedUpdateList();
                            editor.putBoolean("update",false);
                            editor.commit();
                        }
                    }
                });

            }
        }, 0, 1000);
    }
    public void adaptedUpdateList(){
        //declare shared prefs
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        //Update the nameArrayList based on what is stored in the shared prefs
        ArrayList<String> nameArrayList = new ArrayList<>();
        nameArrayList.clear();
        boolean a=true;
        for(int i=0;a;i++){
            if (sharedpreferences.getString("name"+ Integer.toString(i),"")!=""){
                nameArrayList.add(i,sharedpreferences.getString("name"+ Integer.toString(i),""));
            }
            else{
                a = false;
            }
            //Variables in the Shared prefs are stored as name0, name1, name2
        }

        //Create the Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),R.layout.list_view_layout,nameArrayList);
        ListView listView = (ListView) root.findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }





}


