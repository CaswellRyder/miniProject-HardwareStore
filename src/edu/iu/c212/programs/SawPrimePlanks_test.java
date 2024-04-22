package edu.iu.c212.programs;

import java.util.List;

public class SawPrimePlanks_test {
    public static void main(String[] args) {
        // Test case 1: Long plank of length 616
        int longPlankLength1 = 616;
        List<Integer> plankLengths1 = SawPrimePlanks.getPlankLengths(longPlankLength1);
        System.out.println("Test Case 1:");
        System.out.println("Long Plank Length: " + longPlankLength1);
        System.out.println("Resulting Plank Lengths: " + plankLengths1);
        System.out.println("Total Planks: " + SawPrimePlanks.sawPlank(longPlankLength1));
        System.out.println();

        // Test case 2: Long plank of length 195
        int longPlankLength2 = 195;
        List<Integer> plankLengths2 = SawPrimePlanks.getPlankLengths(longPlankLength2);
        System.out.println("Test Case 2:");
        System.out.println("Long Plank Length: " + longPlankLength2);
        System.out.println("Resulting Plank Lengths: " + plankLengths2);
        System.out.println("Total Planks: " + SawPrimePlanks.sawPlank(longPlankLength2));
        System.out.println();

        // Test case 3: Long plank of length 1001
        int longPlankLength3 = 1001;
        List<Integer> plankLengths3 = SawPrimePlanks.getPlankLengths(longPlankLength3);
        System.out.println("Test Case 3:");
        System.out.println("Long Plank Length: " + longPlankLength3);
        System.out.println("Resulting Plank Lengths: " + plankLengths3);
        System.out.println("Total Planks: " + SawPrimePlanks.sawPlank(longPlankLength3));
        System.out.println();
    }
}