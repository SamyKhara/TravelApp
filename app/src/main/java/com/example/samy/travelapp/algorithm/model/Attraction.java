package com.example.samy.travelapp.algorithm.model;



public class Attraction {
    private String name;
    private int id;
    private String methodToThis;

    public Attraction(String name, int id, boolean visited, String methodToThis) {
        this.name = name;
        this.id = id;
        this.methodToThis=methodToThis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMethodToThis() {
        return methodToThis;
    }

    public void setMethodToThis(String methodToThis) {
        this.methodToThis = methodToThis;
    }
}
