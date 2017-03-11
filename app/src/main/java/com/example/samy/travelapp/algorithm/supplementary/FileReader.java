package com.example.samy.travelapp.algorithm.supplementary;


import com.example.samy.travelapp.database.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileReader {
    static double[][] read(String file, int size){
        double[][] ans=new double[size+1][size+1];
        try {
            BufferedReader sc=new BufferedReader(new java.io.FileReader(file));
            String line;
            int index=0;
            int index2=2;
            while ((line = sc.readLine()) != null){
                if(line.equals("NA")){
                    if (index2==41 | index==0 ){
                        index++;
                        index2=index;
                        ans[index][index]=0;
                    }
                    ans[index][index2]= Data.getDistance(index, index2)/20*3600;
                    System.out.println(index+" "+index2+" "+ans[index][index2]);
                    index2++;
                }
                else{
                    String[] info = line.split("\\s+");
                    if (index2==41 | index==0 ){
                        index++;
                        index2=index;
                        ans[index][index]=0;
                    }

                    ans[index][index2]= Double.parseDouble(info[1]);
//                    System.out.println(index+" "+index2+" "+ans[index][index2]);
                    index2++;

                /*for (int i = 1; i < info.length; i++) {
                    int index=Integer.parseInt(info[0]);
                    ans[index][i]=Double.parseDouble(info[i]);
                }*/
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static void main(String[] args) {
        String file="/Users/zhanghao/Desktop/WalkTime.txt";
        int size=40;
        double[][] ans=read(file,size);
        for (int i = 0; i < ans.length; i++) {
            ans[i][0]=i;
            ans[0][i]=i;
        }
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                ans[j][i]=ans[i][j];
            }
        }
        System.out.print("{");
        for (int i = 0; i < ans.length; i++) {
            System.out.print("{");
            for (int j = 0; j < ans[0].length; j++) {
                if(j!=ans[0].length-1){
                    System.out.print(ans[i][j]+", ");
                }
                else{
                    System.out.print(ans[i][j]);
                }
            }
            if(i!=ans.length-1){
                System.out.println("},");
            }
            else{
                System.out.print("}}");
            }
        }

    }
}


