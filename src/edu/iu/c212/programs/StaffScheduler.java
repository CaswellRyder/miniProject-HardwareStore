package edu.iu.c212.programs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class StaffScheduler {
	
	public void scheduleStaff() {
		HashMap<String, ArrayList<String>> workers = new HashMap<>();
		String shiftIN = "C:/Users/benja/OneDrive/Documents/C212 Documents/Projects/MiniProjectMain/src/edu/iu/c212/resources/shift_schedules_IN.txt";
		String staffAvailIn = "C:/Users/benja/OneDrive/Documents/C212 Documents/Projects/MiniProjectMain/src/edu/iu/c212/resources/staff_availability_IN.txt";
		String storeSchedOut = "C:/Users/benja/OneDrive/Documents/C212 Documents/Projects/MiniProjectMain/src/edu/iu/c212/resources/store_schedule_OUT.txt";
		
		try {
			int totalHours = 0;
			File inputFile = new File(staffAvailIn);
			PrintWriter outputFile = new PrintWriter(storeSchedOut);
			File shiftFile = new File(shiftIN);
			Scanner shiftScanner = new Scanner(shiftFile);
			
			//getting total week hours, I do not know how necessary this is for the logic of building schedule
			while(shiftScanner.hasNextLine()) {
				String line = shiftScanner.nextLine();
				String hours = line.split(" ", 2)[1];
				int open = Integer.parseInt(hours.split(" ", 2)[0]);
				int close = Integer.parseInt(hours.split(" ", 2)[1]);
				totalHours = totalHours + (close - open);
			}
			//rounding up to to nearest hour
			totalHours = (((totalHours + 99) / 100) * 100);
			
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
				String [] avail = employee.split(" ");
				String employeeName = "(" + employee.split(" ", 2)[0] + " " + employee.split(" ", 2)[1].charAt(0) + ")";
				//adding employees to their corresponding lists
				if(avail[4].contains("M")) {monday.add(employeeName);}
				if(avail[4].contains("T")) {tuesday.add(employeeName);}
				if(avail[4].contains("W")) {wednesday.add(employeeName);}
				if(avail[4].contains("TR")) {thursday.add(employeeName);}
				if(avail[4].contains("F")) {friday.add(employeeName);}
				if(avail[4].contains("SAT")) {saturday.add(employeeName);}
				if(avail[4].contains("SUN")) {sunday.add(employeeName);}
				
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
			
			//Calculating average worker per day
			int totalEmployees = workers.values().stream().mapToInt(List::size).sum();
			int averageEmployeesPerDay = totalEmployees / 7;
			
			//Available employees per day, sorted by how many people can work that day
			List<String> sortedDays = new ArrayList<>(workers.keySet());
			sortedDays.sort(Comparator.comparingInt(day -> workers.get(day).size()));
			
			//assigning days to work
			HashMap<String, List<String>> assignedWorkers = new HashMap<>();
			for(String day : sortedDays) {
				List<String> workersForDay = workers.get(day);
				assignedWorkers.put(day, new ArrayList<>());
				for(String employee : workersForDay) {
					assignedWorkers.get(day).add(employee);
					if(assignedWorkers.get(day).size() >= averageEmployeesPerDay) {
						break;
					}
				}
			}
			//getting times
			SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
			String date = ft.format(new Date());
			Calendar c = Calendar.getInstance();
			String time = Integer.toString(c.get(Calendar.HOUR)) + Integer.toString(c.get(Calendar.MINUTE));
			
			//making a list of every day of week
			String[] daysOfWeek = {"M", "T", "W", "TR", "F", "SAT", "SUN"};
			
			//writing to output file
			outputFile.println("Created on " + date + " at " + time);
			for(String day : daysOfWeek) {
				outputFile.println(day + " " + assignedWorkers.get(day));
			}
			outputFile.close();
				
		}
		catch(FileNotFoundException e){
			System.out.print("Can not find file.");
		}
	}
	public static void main(String [] args) {
		StaffScheduler test = new StaffScheduler();
		test.scheduleStaff();
	}
}
