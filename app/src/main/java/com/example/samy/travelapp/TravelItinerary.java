package com.example.samy.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samy.travelapp.algorithm.Solver;
import com.example.samy.travelapp.database.SolutionSet;

import java.util.ArrayList;

public class TravelItinerary extends Fragment implements View.OnClickListener{

    View root;

    TextView Cost;
    TextView Time;
    ListView Daily;
    Button MapView;
    CustomListAdapter adapter;
    Solver myRoute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.itinerary, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Daily = (ListView)root.findViewById(R.id.dailyList);
        Cost = (TextView)root.findViewById(R.id.costText);
        Time = (TextView)root.findViewById(R.id.timeText);
        MapView = (Button)root.findViewById(R.id.viewonMapbtn);
        Cost.setText("Cost: S$"+ 0);
        Time.setText("Duration: " + 0 + "mins");
        MapView.setOnClickListener(this);

    }

    @Override
    public void onStart(){
        if (MainActivity.attractList.isEmpty()) {
            Cost.setText("Cost: S$"+ 0);
            Time.setText("Duration: " + 0 + "mins");
        }
        else {
            try {
                System.out.println(MainActivity.attractList);
                String[] solveList = MainActivity.attractList.toArray(new String[MainActivity.attractList.size()]);
                double budget = MainActivity.Budget;
                myRoute = new Solver();
                myRoute.getSolution(solveList, budget);
                System.out.println(SolutionSet.route);
                //setAttractionList(MainActivity.attractList.toArray(new String[MainActivity.attractList.size()]), Double.parseDouble(AttractionList.Budget.getText().toString()));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            ArrayList dailyIt = new ArrayList();
            ListItems results;
            int size = SolutionSet.route.length;
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    results = new ListItems(SolutionSet.route[i], null);
                } else
                    results = new ListItems(SolutionSet.route[i], SolutionSet.method[i + 1]);


                dailyIt.add(results);
            }
            adapter = new CustomListAdapter(root.getContext(), dailyIt);
            Cost.setText("Cost: " + ((int) SolutionSet.cost));
            Time.setText("Duration: " + ((int) SolutionSet.time) + "mins");

            Daily.setAdapter(adapter);


        }
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        System.out.println("ON CLICK!");
        switch(v.getId()){
            case R.id.viewonMapbtn:
                MapActivity nextFrag = new MapActivity();
                this.getFragmentManager().beginTransaction()
                        .replace(this.getId(), nextFrag, null)
                        .addToBackStack(null)
                        .commit();
                break;
        }

    }



}






