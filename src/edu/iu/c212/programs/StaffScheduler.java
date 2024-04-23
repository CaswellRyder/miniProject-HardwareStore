package edu.iu.c212.programs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StaffScheduler {

    public void scheduleStaff() {
        try {
            File inputFile = new File("staff_availability_IN");
            PrintWriter outputFile = new PrintWriter("store_schedule_OUT");
            File shiftFile = new File("shift_schedules_IN");
            Scanner shiftScanner = new Scanner(shiftFile);
            while(shiftScanner.hasNextLine()) {
                String line = shiftScanner.nextLine();
                String hours = line.split(" ")[1];

            }

        }
        catch(FileNotFoundException e){
            System.out.print("Can not find file.");
        }

    }
}