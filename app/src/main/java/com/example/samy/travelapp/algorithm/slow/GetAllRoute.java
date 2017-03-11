package com.example.samy.travelapp.algorithm.slow;

import com.example.samy.travelapp.algorithm.model.RouteList;
import com.example.samy.travelapp.algorithm.model.RouteListSet;
import com.example.samy.travelapp.database.Data;
import com.example.samy.travelapp.database.Methods;


public class GetAllRoute {
    public static void add(String[] route){
        for (int i = 0; i < Methods.allMethods.size(); i++) {
            RouteList routeList=new RouteList(route.length);
            String[] thisMethod=Methods.allMethods.get(i);
            routeList.singleRoute[0].setName(route[0]);
            routeList.singleRoute[0].setMethodToThis("nothing");
            for (int j = 1; j < route.length; j++) {
                routeList.singleRoute[j].setName(route[j]);
                routeList.singleRoute[j].setMethodToThis(thisMethod[j]);
            }

            for (int j = 1; j < route.length; j++) {
                if (thisMethod[j]=="walk"){
                    routeList.time+= Data.getWalkTime(Data.getAttractionId(route[j]), Data.getAttractionId(route[j-1]));
                }
                if (thisMethod[j]=="public transport"){
                    routeList.cost+= Data.getPublicCost(Data.getAttractionId(route[j]),Data.getAttractionId(route[j-1]));
                    routeList.time+= Data.getPublicTime(Data.getAttractionId(route[j]),Data.getAttractionId(route[j-1]));
                }
                if (thisMethod[j]=="taxi"){
                    routeList.cost+= Data.getTaxiCost(Data.getAttractionId(route[j]),Data.getAttractionId(route[j-1]));
                    routeList.time+= Data.getTaxiTime(Data.getAttractionId(route[j]),Data.getAttractionId(route[j-1]));
                }
            }

            RouteListSet.allRoutes.add(routeList);


        }
    }
}
