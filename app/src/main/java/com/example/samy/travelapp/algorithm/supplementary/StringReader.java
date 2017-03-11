package com.example.samy.travelapp.algorithm.supplementary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class StringReader {
    public static void main(String[] args) {
        String file="/Users/zhanghao/Desktop/WalkTime.txt";
        try {
            BufferedReader sc=new BufferedReader(new java.io.FileReader(file));
            String line;
            System.out.print("{\"nothing\",");
            int count=0;
            while ((line = sc.readLine()) != null){
                System.out.print("\""+line+"\",");
                count++;
                if (count==10){
                    System.out.println();
                    count=1;
                }
            }
            System.out.println("}");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
