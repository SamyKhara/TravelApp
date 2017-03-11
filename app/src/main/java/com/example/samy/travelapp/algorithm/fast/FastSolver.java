package com.example.samy.travelapp.algorithm.fast;


import com.example.samy.travelapp.algorithm.model.AttractionList;
import com.example.samy.travelapp.database.Data;
import com.example.samy.travelapp.database.SolutionSet;


public class FastSolver {

    public static SolutionSet solve(String[] namesOfAttraction, double budget){
        SolutionSet.route=new String[namesOfAttraction.length];
        SolutionSet.method=new String[namesOfAttraction.length];
        AttractionList attractionList=new AttractionList(namesOfAttraction.length);
        //mark start
        SolutionSet.route[0]=namesOfAttraction[1];
        SolutionSet.method[0]="nothing";
        //mark beginning
        int from=1;
        int smallestId=2;
        attractionList.visited[1]=true;
        int routeId=1;
        //generate route
        while (routeId!=namesOfAttraction.length-1){
            for (int i = 2; i < namesOfAttraction.length; i++) {
                if (attractionList.visited[i]==false & smallestId!= Integer.MIN_VALUE){
                    double newDistance=Data.getDistance(Data.getAttractionId(namesOfAttraction[from]), Data.getAttractionId(namesOfAttraction[i]));
                    double oldDistance=Data.getDistance(Data.getAttractionId(namesOfAttraction[from]), Data.getAttractionId(namesOfAttraction[smallestId]));
                    if (newDistance<oldDistance){
                        smallestId=i;
                    }
                }
                else if (attractionList.visited[i]==false & smallestId== Integer.MIN_VALUE){
                    smallestId=i;
                }
            }
            attractionList.visited[smallestId]=true;
            SolutionSet.route[routeId]=namesOfAttraction[smallestId];
            from=smallestId;
            smallestId= Integer.MIN_VALUE;
            routeId++;
        }
        SolutionSet.route[namesOfAttraction.length-1]=namesOfAttraction[1];

        //get total distance
        double remainingDistance=0;
        double remainingBudget=budget;
        for (int i = 0; i < SolutionSet.route.length-1; i++) {
            remainingDistance+= Data.getDistance(Data.getAttractionId(SolutionSet.route[i]),
                    Data.getAttractionId(SolutionSet.route[i+1]));
        }

        for (int i = 1; i < SolutionSet.route.length; i++) {
            remainingDistance-= Data.getDistance(Data.getAttractionId(SolutionSet.route[i-1]),
                    Data.getAttractionId(SolutionSet.route[i]));

            if (remainingBudget/remainingDistance>1 & Data.getTaxiCost(Data.getAttractionId(SolutionSet.route[i - 1]),
                    Data.getAttractionId(SolutionSet.route[i]))<remainingBudget){
                //take taxi
                SolutionSet.method[i]="taxi";
                SolutionSet.time+=Data.getTaxiTime(Data.getAttractionId(SolutionSet.route[i - 1]),
                        Data.getAttractionId(SolutionSet.route[i]));
                remainingBudget-=Data.getTaxiCost(Data.getAttractionId(SolutionSet.route[i - 1]),
                        Data.getAttractionId(SolutionSet.route[i]));
            }
            else if(Data.getPublicCost(Data.getAttractionId(SolutionSet.route[i - 1]),
                    Data.getAttractionId(SolutionSet.route[i]))<remainingBudget){
                //take public transport
                SolutionSet.method[i]="public transport";
                SolutionSet.time+=Data.getPublicTime(Data.getAttractionId(SolutionSet.route[i - 1]),
                        Data.getAttractionId(SolutionSet.route[i]));
                remainingBudget-=Data.getPublicCost(Data.getAttractionId(SolutionSet.route[i - 1]),
                        Data.getAttractionId(SolutionSet.route[i]));
            }
            else{
                //walk
                SolutionSet.method[i]="walk";
                SolutionSet.time+=Data.getWalkTime(Data.getAttractionId(SolutionSet.route[i - 1]),
                        Data.getAttractionId(SolutionSet.route[i]));
            }
        }
        SolutionSet.cost=budget-remainingBudget;
        SolutionSet.time/=60;
        return new SolutionSet();
    }
}
