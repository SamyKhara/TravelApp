package com.example.samy.travelapp.algorithm.model;


public class RouteList implements Cloneable {
    public Attraction[] singleRoute;
    public double cost;
    public double time;


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public RouteList(int length){
        singleRoute=new Attraction[length];
        for (int i = 0; i < this.singleRoute.length; i++) {
            this.singleRoute[i]=new Attraction(null,0,false,null);
        }
    }

    public RouteList(){}


}
