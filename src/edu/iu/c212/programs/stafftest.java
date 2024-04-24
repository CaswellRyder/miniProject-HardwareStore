package edu.iu.c212.programs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class stafftest {

    public static void main(String[] args) {
        HashMap<String, ArrayList<String>> workers = new HashMap();

        try {
            System.out.println("starting try block");
            int totalHours = 0;
            File inputFile = new File("src/edu/iu/c212/resources/shift_schedules_IN.txt");
            PrintWriter outputFile = new PrintWriter("src/edu/iu/c212/resources/store_schedule_OUT.txt");
            File shiftFile = new File("src/edu/iu/c212/resources/shift_schedules_IN.txt");
            Scanner shiftScanner = new Scanner(shiftFile);

            //getting total week hours, I do not know how neccessary this is for the logic of building schedule
            while(shiftScanner.hasNextLine()) {
                System.out.println("in while loop");
                String line = shiftScanner.nextLine();
                String hours = line.split(" ", 2)[1];
                int open = Integer.parseInt(hours.split(" ", 2)[0]);
                int close = Integer.parseInt(hours.split(" ", 2)[1]);
                totalHours = totalHours + (close - open);
            }
            shiftScanner.close();

			/*
			Creating array lists that will contain every employee
			that works on a given day
			*/
            ArrayList<String> monday = new ArrayList<String>();
            ArrayList<String> tuesday = new ArrayList<String>();
            ArrayList<String> wednesday = new ArrayList<String>();
            ArrayList<String> thursday = new ArrayList<String>();
            ArrayList<String> friday = new ArrayList<String>();
            ArrayList<String> saturday = new ArrayList<String>();
            ArrayList<String> sunday = new ArrayList<String>();

            //getting staff
            Scanner staffScanner = new Scanner(inputFile);
            while(staffScanner.hasNextLine()) {
                String employee = staffScanner.nextLine();
                String avail = employee.split(".", 2)[0].charAt(-1) + employee.split(".", 2)[1];
                String employeeName = employee.split(" ", 2)[0] + employee.split(" ", 2)[1].charAt(0);
                //adding employees to their corresponding lists
                if(avail.contains("M")) {monday.add(employeeName);}
                if(avail.contains("T")) {tuesday.add(employeeName);}
                if(avail.contains("W")) {wednesday.add(employeeName);}
                if(avail.contains("TR")) {thursday.add(employeeName);}
                if(avail.contains("F")) {friday.add(employeeName);}
                if(avail.contains("SAT")) {saturday.add(employeeName);}
                if(avail.contains("SUN")) {sunday.add(employeeName);}
            }
            //Adding the days of week and workers to hash map
            workers.put("M", monday);
            workers.put("T", tuesday);
            workers.put("W", wednesday);
            workers.put("TR", thursday);
            workers.put("F", friday);
            workers.put("SAT", saturday);
            workers.put("SUN", sunday);

            staffScanner.close();

            //getting times
            SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
            String date = ft.format(new Date());
            Calendar c = Calendar.getInstance();
            String time = Integer.toString(c.get(Calendar.HOUR)) + Integer.toString(c.get(Calendar.MINUTE));

            outputFile.println("Created on " + date + " at " + time);
            //This is where I left off, I have currently made hashmap (i am pretty sure everything
            //is working correctly with that) now we just need to figure out how to get this onto output file.
        }
        catch(FileNotFoundException e){
            System.out.print("Can not find file.");
        }

    }
}