package com.example.samy.travelapp.algorithm.slow;

import com.example.samy.travelapp.database.Permutations;


public class GeneratePermutation {
    public static void getPermutation(String[] listOfAttraction){
        String[] permutedPart=new String[listOfAttraction.length-2];
        for (int i = 0; i < permutedPart.length; i++) {
            permutedPart[i]=listOfAttraction[i+2];
        }
        permutate(permutedPart, 0);
    }

    public static void permutate(String[] list, int pointer) {
        if (pointer == list.length) {
            //stop-condition: print or process number
            Permutations.all.add(list);
            return;
        }
        for (int i = pointer; i < list.length; i++) {
            String[] permutation = list.clone();
            permutation[pointer] = list[i];
            permutation[i] = list[pointer];
            permutate(permutation, pointer + 1);
        }
    }



   /* public static void main(String[] args) {
        String[] test={"nothing","1","1","2","3","4"};
        getPermutation(test);
        for (int i = 0; i < Permutations.all.size(); i++) {
            for (int j = 0; j < Permutations.all.get(i).length; j++) {
                System.out.print(Permutations.all.get(i)[j] + "  ");
            }
            System.out.println();
        }
        System.out.println(Permutations.all.size());
    }*/
}
