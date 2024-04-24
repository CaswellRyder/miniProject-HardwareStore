package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileUtils {
    private static File inputFile = new File("src/edu/iu/c212/resources/input.txt");
    private static File outputFile = new File("src/edu/iu/c212/resources/output.txt");
    private static File inventoryFile = new File("src/edu/iu/c212/resources/inventory.txt");
    private static File staffAvailabilityFile = new File("src/edu/iu/c212/resources/resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("src/edu/iu/c212/resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("src/edu/iu/c212/resources/store_schedule_OUT.txt");

    /**
     * Reads the inventory from the "inventory.txt" file and returns a list of strings.
     *
     * @return A list of strings representing the inventory items.
     * @throws IOException If an error occurs while reading the file.
     */
    public static List<String> readInventoryFromFile() throws IOException {
        List<String> inventory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inventoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comment lines starting with "//"
                if (!line.trim().startsWith("//")) {
                    inventory.add(line);
                }
            }
        }
        return inventory;
    }

    /**
     * Writes the updated inventory list to the "inventory.txt" file.
     *
     * @param inventory The updated inventory list to be written to the file.
     * @throws IOException If an error occurs while writing to the file.
     */
    public static void writeInventoryToFile(List<String> inventory) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inventoryFile))) {
            for (String item : inventory) {
                writer.write(item);
                writer.write(System.lineSeparator());
            }
        }
    }

    public List<Staff> readStaffFromFile() throws IOException {
        List<Staff> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(staffAvailabilityFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0] + " " + parts[1];
                int age = Integer.parseInt(parts[2]);
                String role = parts[3];
                String availability = String.join(".", parts).substring(4 + name.length() + role.length());
                employees.add(new Staff(name, age, role, availability));
            }
        }
        return employees;
    }

    public void writeStaffToFile(List<Staff> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffAvailabilityFile))) {
            for (Staff staff : employees) {
                writer.write(staff.getName() + " " + staff.getAge() + " " + staff.getRole() + " " + staff.getAvailability());
                writer.newLine();
            }
        }
    }

    public static List<String> readCommandsFromFile() throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commands.add(line);
            }
        }
        return commands;
    }

    public static void writeStoreScheduleToFile(List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storeScheduleFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void writeLineToOutputFile(String line) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true)); // Append mode
        writer.write(line);
        writer.newLine();
        writer.close();
    }
}
