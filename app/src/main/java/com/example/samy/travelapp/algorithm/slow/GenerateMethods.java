package com.example.samy.travelapp.algorithm.slow;

import com.example.samy.travelapp.database.Methods;


public class GenerateMethods {
    public static void getMethods(int len){
        for (int i = 0; i < (int) Math.pow(3, len); i++) {
            int number= Integer.parseInt(Integer.toString(i,3));
            String[] method=new String[len];
            for (int j = 0; j < len; j++) {
                int type;
                type=number%10;
                number/=10;
                if (type==0){
                    method[j]="walk";
                }
                else if (type==1){
                    method[j]="public transport";
                }
                else if (type==2){
                    method[j]="taxi";
                }
            }
            Methods.allMethods.add(method);
        }
    }

    public static void main(String[] args) {
        getMethods(9);
        for (int i = 0; i < Methods.allMethods.size(); i++) {
            for (int j = 0; j < Methods.allMethods.get(i).length; j++) {
                System.out.print(Methods.allMethods.get(i)[j]+" ");
            }
            System.out.println();
        }
    }
}
