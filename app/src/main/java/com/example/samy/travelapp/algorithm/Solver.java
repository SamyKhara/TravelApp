package com.example.samy.travelapp.algorithm;


import com.example.samy.travelapp.algorithm.fast.FastSolver;
import com.example.samy.travelapp.algorithm.slow.SlowSolver;
import com.example.samy.travelapp.database.Methods;
import com.example.samy.travelapp.database.Permutations;
import com.example.samy.travelapp.database.SolutionSet;


public class Solver {

    /**
     * Last edited by Zhang Hao on 7/11/15.
     *
     * To get a solution,use the following code:
     * getSolution(attractionList,budget);
     *
     * The solution can be accessed using:
     * SolutionSet.route    the planned route in array form.
     *                      eg.SolutionSet.route={"place 1","place 2,..., "place 1}
     *                      note that it will always return to the start place
     * SolutionSet.method;  the type of transport in array form.
     *                      eg.SolutionSet.method={"nothing","taxi","public transport",...}
     *                      note that the first element is always meaningless
     * SolutionSet.time;    total time of daily travel
     * SolutionSet.cost;    total cost of daily travel
     *
     *
     * @param attractionList a String[] containing all the attractions, note
     *                       that first element is not used.
     *                       eg. attractionList={"nothing","place 1","place 2"...}
     * @param budget variable type double, the budget input.
     * @return update the new SolutionSet static class
     *

     */
    public static SolutionSet getSolution(String[] attractionList, double budget) throws CloneNotSupportedException {

        SolutionSet.clear();
        Permutations.clear();
        Methods.clear();

        if (attractionList.length<8){
            SlowSolver.solve(attractionList, budget);
            //System.out.println("Using Brute Force");
        }
        else{
            FastSolver.solve(attractionList, budget);
            //System.out.println("Using Fast Approximate");
        }
        return new SolutionSet();
    }

/*
    //Test code
    public static void main(String[] args) throws CloneNotSupportedException {
        String[] list={"nothing","Marina Bay Sands","Singapore Zoo","Haw Par Villa","Bukit Timah Nature Reserve","Changi Prison"};
        getSolution(list, 30);
        System.out.println("Start from "+SolutionSet.route[0]);
        for (int i = 1; i < SolutionSet.route.length; i++) {
            System.out.println(SolutionSet.method[i]+" to "+SolutionSet.route[i]);
        }
        System.out.println("cost:"+SolutionSet.cost+ "  Time:"+SolutionSet.time);
    }*/
}
