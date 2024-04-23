package edu.iu.c212.programs;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SawPrimePlanksTest {

    @Test
    public void testGetPlankLengths() {
        // Test case 1: Plank length 654
        List<Integer> expectedPlanksFor654 = Arrays.asList(2, 3, 109);
        List<Integer> actualPlanksFor654 = SawPrimePlanks.getPlankLengths(654);
        assertEquals(expectedPlanksFor654, actualPlanksFor654);

        // Test case 2: Plank length 120
        List<Integer> expectedPlanksFor120 = Arrays.asList(2, 2, 2, 3, 5);
        List<Integer> actualPlanksFor120 = SawPrimePlanks.getPlankLengths(120);
        assertEquals(expectedPlanksFor120, actualPlanksFor120);

        // Test case 3: Plank length 17
        List<Integer> expectedPlanksFor17 = Arrays.asList(17);
        List<Integer> actualPlanksFor17 = SawPrimePlanks.getPlankLengths(17);
        assertEquals(expectedPlanksFor17, actualPlanksFor17);

        // Test case 4: Plank length 1
        List<Integer> expectedPlanksFor1 = Arrays.asList();
        List<Integer> actualPlanksFor1 = SawPrimePlanks.getPlankLengths(1);
        assertEquals(expectedPlanksFor1, actualPlanksFor1);
    }

    @Test
    public void testIsPrime() {
        // Test case 1: Prime number
        assertTrue(SawPrimePlanks.isPrime(7));

        // Test case 2: Non-prime number
        assertTrue(!SawPrimePlanks.isPrime(10));
    }

    @Test
    public void testFindSmallestPrimeFactor() {
        // Test case 1: Number with prime factor
        assertEquals(3, SawPrimePlanks.findSmallestPrimeFactor(21));

        // Test case 2: Prime number
        assertEquals(17, SawPrimePlanks.findSmallestPrimeFactor(17));
    }
}