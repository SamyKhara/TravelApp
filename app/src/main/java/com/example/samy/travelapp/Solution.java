package com.example.samy.travelapp;

import java.util.ArrayList;

public class Solution {
    ArrayList<String> route;
    ArrayList<String> mode;
    ArrayList<Float> price;

    float totalCost;
    float totalTime;

    public Solution(){
        route = new ArrayList<String>();
        mode = new ArrayList<String>();
        price = new ArrayList<Float>();
    }

    public void testData(){
        totalCost = Float.valueOf(410);
        totalTime = 116;

        route.add("India Gate");
        mode.add("Cab");
        price.add(Float.valueOf(140));

        route.add("Qutub Minar");
        mode.add("Cab");
        price.add(Float.valueOf(120));

        route.add("Lotus Temple");
        mode.add("Cab");
        price.add(Float.valueOf(100));

        route.add("Red Fort");
        mode.add("Bus/Metro");
        price.add(Float.valueOf(50));

    }
}
