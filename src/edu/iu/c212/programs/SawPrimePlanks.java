package edu.iu.c212.programs;

import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SawPrimePlanks {
    public static void main(String[] args) {
        List<String> inventory;
        try {
            // Read the initial inventory from a file
            inventory = FileUtils.readInventoryFromFile();
        } catch (IOException e) {
            System.out.println("Error occurred while reading inventory from file.");
            e.printStackTrace();
            return; // Exit the program if an error occurs
        }

        // Process inventory to generate new planks
        processInventory(inventory);

        // Save updated inventory back to the file
        saveUpdatedInventory(inventory);
    }

    /**
     * Processes inventory list to generate new planks with prime lengths.
     * The new planks are added to the same inventory list.
     *
     * @param inventory The list of inventory items to process.
     */
    private static void processInventory(List<String> inventory) {
        List<String> newPlanks = new ArrayList<>();
        List<String> otherItems = new ArrayList<>();

        // Iterate through each item in the inventory
        for (String item : inventory) {
            // Check if the item represents a plank
            if (item.startsWith("'Plank-") && item.contains(",")) {
                String[] itemParts = item.split(",");
                String lengthString = itemParts[0].substring(6).replaceAll("[^0-9]", "");
                int length = Integer.parseInt(lengthString);

                // Find the prime length of the plank
                int primeLength = findPrimeLength(length);

                // Calculate the quantity of new planks based on the prime length
                int quantity = calculateQuantity(length, primeLength);

                // Calculate the price of the new planks based on the prime length
                int price = primeLength * primeLength;

                // Generate the new plank item string
                String newPlankName = "'Plank-" + primeLength + "'";
                String newPlankItem = newPlankName + "," + price + "," + quantity + "," + "1";
                newPlanks.add(newPlankItem);
            } else {
                // Add non-plank items to the otherItems list
                otherItems.add(item);
            }
        }

        // Clear the original inventory list
        inventory.clear();

        // Add non-plank items back to the inventory list
        inventory.addAll(otherItems);

        // Add the new planks to the inventory list
        inventory.addAll(newPlanks);
    }

    /**
     * Finds the prime length of a given length by recursively dividing the length
     * by prime factors until it becomes prime.
     *
     * @param length The length to find the prime length for.
     * @return The prime length.
     */
    private static int findPrimeLength(int length) {
        for (int i = 2; i <= Math.sqrt(length); i++) {
            if (length % i == 0) {
                return findPrimeLength(length / i);
            }
        }
        return length;
    }

    /**
     * Calculates the quantity of new planks based on the original length and prime length.
     *
     * @param length      The original length of the plank.
     * @param primeLength The prime length of the plank.
     * @return The quantity of new planks.
     */
    private static int calculateQuantity(int length, int primeLength) {
        int quantity = 1;
        while (length > 1) {
            if (length % primeLength == 0) {
                quantity *= length / primeLength;
                length /= primeLength;
            } else {
                break;
            }
        }
        return quantity;
    }

    /**
     * Saves the updated inventory list to a file.
     *
     * @param inventory The updated inventory list to save.
     */
    private static void saveUpdatedInventory(List<String> inventory) {
        try {
            FileUtils.writeInventoryToFile(inventory);
            System.out.println("Updated inventory saved to file.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving updated inventory to file.");
            e.printStackTrace();
        }
    }
}