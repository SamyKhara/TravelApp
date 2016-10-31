package com.example.samy.travelapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AttractionsActivity extends AppCompatActivity {

    EditText budget;
    ListView selectionList;
    ListView attractionList;
    UserInput user;
    ArrayList<String> places;
    ArrayList<String> userPlaces;
    Button itinerary;
    int selectedColor = Color.parseColor("#696969");
    int unSelectedColor = Color.TRANSPARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_list);

        budget = (EditText)findViewById(R.id.budget);
        selectionList = (ListView)findViewById(R.id.listOfPlaces);
        attractionList = (ListView)findViewById(R.id.attractionListView);
        places = new ArrayList<String>();
        userPlaces = new ArrayList<String>();
        itinerary = (Button)findViewById(R.id.itinerarybtn);

        listOfPlaces();
       // setUserInput();

        selectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userPlaces.add(places.get(position));
                setSelectedPlaces();
            }
        });
        attractionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                userPlaces.remove(userPlaces.get(i));
                setSelectedPlaces();
                return false;
            }
        });
        itinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttractionsActivity.this,TravelItinerary.class);
                startActivity(intent);
            }
        });
    }

    public void listOfPlaces(){
        places.add("Red Fort");
        places.add("Qutub Minar");
        places.add("India Gate");
        places.add("Lotus Temple");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        selectionList.setAdapter(adapter);
    }

    public void setSelectedPlaces(){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userPlaces);
        attractionList.setAdapter(adapter);
    }
    public void setUserInput(){
        user.budget = Float.parseFloat(budget.toString());
        user.attractionsList = places;
    }
}
