package edu.iu.c212.utils;

import edu.iu.c212.models.Staff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



class OsUtils {
    private static String OS = null;
    private static String finalDirectory;

    public static String getOsName()
    {
        if(OS == null) { OS = System.getProperty("os.name"); }
        return OS;
    }

    public static boolean isWindows()
    {
        return getOsName().startsWith("Windows");
    }

    public static String getUser() {
        return System.getProperty("user.name");
    }

    public static String displayDirectoryContents(File dir) {
        boolean fileFound = false;
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (fileFound) {
                    break;
                }

                if (file.isDirectory() && (!file.getName().equals("out"))) {
                    if(file.getName().equals("resources")) {
                        File[] resourceFiles = file.listFiles();
                        for (File f : resourceFiles) {
                            if (fileFound) {
                                break;
                            }
                            if (f.getName().equals("input.txt")) {
                                finalDirectory = (file.getAbsolutePath() + "/").replace("\\", "/");
                                fileFound = true;
                                break;
                            }
                        }

                    }
                    if (!fileFound) {
                        displayDirectoryContents(file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalDirectory;

    }
}

public class FileUtilsTest {
    private static File inputFile = new File("input.txt");
    private static File outputFile = new File("output.txt");
    private static File inventoryFile = new File("inventory.txt");
    private static File staffAvailabilityFile = new File("staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("store_schedule_OUT.txt");

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


private static File inputFile;
private static File outputFile;
private static File inventoryFile;
private static File staffAvailabilityFile;
private static File shiftSchedulesFile;
private static File storeScheduleFile;

//Not used?
private static File staffFile;

//Test Files
private static File outputTestFile;
private static File inventoryTestFile;
private static File staffAvailabilityTestFile;
private static File storeScheduleTestFile;

public static void setFilePaths() {

    String userDirectory = System.getProperty("user.dir").replace("\\", "/");
    File file = new File(userDirectory);
    String resourcePath = OsUtils.displayDirectoryContents(file);

    inputFile = new File(resourcePath + "input.txt");
    outputFile = new File(resourcePath + "output.txt");
    outputTestFile = new File(resourcePath + "outputTest.txt");
    inventoryFile = new File(resourcePath + "inventory.txt");
    inventoryTestFile = new File(resourcePath + "inventoryTest.txt");
    staffFile = new File(resourcePath + "staff.txt");
    staffAvailabilityTestFile = new File(resourcePath + "staff_availability_IN_Test.txt");
    staffAvailabilityFile = new File(resourcePath + "staff_availability_IN.txt");
    shiftSchedulesFile = new File(resourcePath + "shift_schedules_IN.txt");
    storeScheduleFile = new File(resourcePath + "store_schedule_OUT.txt");
    storeScheduleTestFile = new File(resourcePath + "store_schedule_OUT_Test.txt");
}