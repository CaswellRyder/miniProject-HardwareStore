package edu.iu.c212.programs;

import java.util.*;

public class SawPrimePlanks {
    public static List<Integer> getPlankLengths(int longPlankLength) {
        List<Integer> plankLengths = new ArrayList<>();
        sawPlankHelper(longPlankLength, plankLengths);
        return plankLengths;
    }

    private static void sawPlankHelper(int plankLength, List<Integer> plankLengths) {
        if (isPrime(plankLength)) {
            plankLengths.add(plankLength);
            return;
        }

        int smallestPrimeFactor = findSmallestPrimeFactor(plankLength);
        int numSmallerPlanks = plankLength / smallestPrimeFactor;

        for (int i = 0; i < numSmallerPlanks; i++) {
            sawPlankHelper(smallestPrimeFactor, plankLengths);
        }
    }

    public static int sawPlank(int plankLength) {
        if (isPrime(plankLength)) {
            return 1;
        }

        int smallestPrimeFactor = findSmallestPrimeFactor(plankLength);
        int numSmallerPlanks = plankLength / smallestPrimeFactor;

        return numSmallerPlanks * sawPlank(smallestPrimeFactor);
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static int findSmallestPrimeFactor(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (isPrime(i) && number % i == 0) {
                return i;
            }
        }

        return number;
    }

}
