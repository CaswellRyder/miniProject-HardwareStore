package edu.iu.c212.programs;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SawPrimePlanks {
    public static List<Integer> getPrimeFactors(int num) {
        List<Integer> factors = new ArrayList<>();

        while (num % 2 == 0) {
            factors.add(2);
            num /= 2;
        }

        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            while (num % i == 0) {
                factors.add(i);
                num /= i;
            }
        }

        if (num > 2) {
            factors.add(num);
        }

        return factors;
    }

    public void sawPrimePlanks() {
        String inventoryFile = "src/edu/iu/c212/resources/inventory.txt";
        String outputInventoryFile = "src/edu/iu/c212/resources/inventory.txt";

        try {
            File inputFile = new File(inventoryFile);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("'Plank-")) {
                    String[] parts = line.split("'")[1].split(",");
                    String plankName = parts[0];
                    int plankLength = Integer.parseInt(plankName.split("-")[1]);

                    List<Integer> primeFactors = getPrimeFactors(plankLength);
                    int price = (int) Math.pow(plankLength, 2);  // Update this line

                    for (int factor : primeFactors) {
                        writer.write("'Plank-" + factor + "'," + price + ",1,1\n");
                    }
                } else {
                    writer.write(line + "\n");
                }
            }

            reader.close();
            writer.close();

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            PrintWriter outputWriter = new PrintWriter(new FileWriter("src/edu/iu/c212/resources/output.txt", true));
            outputWriter.println();
            outputWriter.println("########################################");
            outputWriter.println("Sawed planks (" + formattedDateTime + ")");
            outputWriter.println("########################################");
            outputWriter.close();

            if (inputFile.delete()) {
                if (!tempFile.renameTo(new File(outputInventoryFile))) {
                    System.out.println("Error renaming the file.");
                }
            } else {
                System.out.println("Error deleting the original file.");
            }

            System.out.println("Planks saved.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}