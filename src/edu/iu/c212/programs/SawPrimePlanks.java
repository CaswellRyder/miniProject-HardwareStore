package edu.iu.c212.programs;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SawPrimePlanks {
    public void SawPlanks() {
        try {
            File inventoryFile = new File("src/edu/iu/c212/resources/inventory.txt");
            Scanner scanner = new Scanner(inventoryFile);
            List<String> lines = new ArrayList<>();
            List<String> sawedPlanks = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("'Plank-'")) {
                    String[] parts = line.split(",");
                    String plankNameWithLength = parts[0].trim().replace("'", "");
                    String[] nameParts = plankNameWithLength.split("-");
                    String plankName = nameParts[0];
                    int plankLength = Integer.parseInt(nameParts[1]);

                    List<Integer> primeFactors = getPlankLengths(plankLength);
                    for (int factor : primeFactors) {
                        int price = factor * factor;
                        String sawedPlank = "'" + plankName + "-" + factor + "'," + price + "," + parts[2] + "," + parts[3];
                        sawedPlanks.add(sawedPlank);
                    }
                } else {
                    lines.add(line);
                }
            }

            scanner.close();

            PrintWriter inventoryWriter = new PrintWriter(new FileWriter(inventoryFile));
            for (String line : lines) {
                inventoryWriter.println(line);
            }
            for (String sawedPlank : sawedPlanks) {
                inventoryWriter.println(sawedPlank);
            }
            inventoryWriter.close();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            PrintWriter outputWriter = new PrintWriter(new FileWriter("src/edu/iu/c212/resources/output.txt", true));
            outputWriter.println();
            outputWriter.println("==================================");
            outputWriter.println("Sawed planks (" + formattedDateTime + "):");
            for (String sawedPlank : sawedPlanks) {
                outputWriter.println(sawedPlank);
            }
            outputWriter.close();

            System.out.println("Planks sawed and updated in inventory and output files.");
        } catch (IOException e) {
            System.out.println("Error: Cannot find file.");
        }
    }

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
        int remainingLength = plankLength / smallestPrimeFactor;

        plankLengths.add(smallestPrimeFactor);
        sawPlankHelper(remainingLength, plankLengths);
    }

    public static int sawPlank(int plankLength) {
        if (isPrime(plankLength)) {
            return 1;
        }

        int smallestPrimeFactor = findSmallestPrimeFactor(plankLength);
        int numSmallerPlanks = plankLength / smallestPrimeFactor;

        return numSmallerPlanks * sawPlank(smallestPrimeFactor);
    }

    public static boolean isPrime(int number) {
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

    public static int findSmallestPrimeFactor(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (isPrime(i) && number % i == 0) {
                return i;
            }
        }

        return number;
    }

}
