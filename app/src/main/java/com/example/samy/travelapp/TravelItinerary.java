package com.example.samy.travelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TravelItinerary extends AppCompatActivity {
    TextView TotalCost;
    TextView Time;
    ListView TravelList;
    CustomListAdapter adapter;
    Solution myRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary);
        //use hardcoded data
        myRoute = new Solution();
        myRoute.testData();

        ArrayList travelIt = new ArrayList();

        TravelList = (ListView)findViewById(R.id.travelList);
        TotalCost = (TextView)findViewById(R.id.totalCost);
        Time = (TextView)findViewById(R.id.totalTime);
        TotalCost.setText("Cost: Rs "+ ((float) myRoute.totalCost));
        Time.setText("Duration: "+ (myRoute.totalTime)+"mins");

        for (int i = 0; i < myRoute.route.size(); i++){
            DailyListItems results = new DailyListItems(myRoute.route.get(i),myRoute.mode.get(i),myRoute.price.get(i));
            travelIt.add(results);
        }
        final ListView routeList = (ListView)findViewById(R.id.travelList);
        adapter = new CustomListAdapter(this, travelIt);
        routeList.setAdapter(adapter);
    }
}
