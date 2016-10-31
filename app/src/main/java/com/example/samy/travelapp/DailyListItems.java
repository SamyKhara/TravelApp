package com.example.samy.travelapp;

public class DailyListItems {
    private String Place;
    private String Method;
    private Float Cost;

    public DailyListItems(String plc, String mode, Float cost){
        Place = plc;
        Method = mode;
        Cost = cost;
    }

    public void setPlace(String place){
        Place = place;
    }
    public void setMethod(String method){
        Method = method;
    }
    public void setCost(Float cost) { Cost = cost; }

    public String getPlace(){
        return Place;
    }
    public String getMethod(){
        return Method;
    }
    public Float getCost() { return Cost; }

}