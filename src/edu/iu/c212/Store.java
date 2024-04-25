package edu.iu.c212;

import java.io.*;
import java.util.*;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;
import edu.iu.c212.IStore;
import edu.iu.c212.programs.*;

public class Store implements IStore {

	private Map<String, Item> inventory = new HashMap<>();
	private Map<String, Staff> staffMembers = new HashMap<>();
	private List<String> shiftSchedules = new ArrayList<>();

	public Store() {
		loadInventory("src/edu/iu/c212/resources/inventory.txt");
		loadStaff("src/edu/iu/c212/resources/staff_availability_IN.txt");
		loadShiftSchedules("src/edu/iu/c212/resources/shift_schedules_IN.txt");
		try {
			takeAction();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Item> getItemsFromFile(){
		FileUtils temp = new FileUtils();
		List<Item> ans = null;
		List<String> tempArr = null;
		try {
			tempArr = temp.readInventoryFromFile();
		}
		catch(IOException e) {
			System.exit(0);
		}
		for (String info : tempArr){
			String [] itemInfo = info.split(",");
			ans.add(new Item(itemInfo[0], Double.parseDouble(itemInfo[1]), Integer.parseInt(itemInfo[2]), Integer.parseInt(itemInfo[3])));
		}
		return ans;
	}

	@Override
	public List<Staff> getStaffFromFile(){
		FileUtils temp = new FileUtils();
		List<Staff> ans = null;
		try {
			ans = temp.readStaffFromFile();
		}
		catch(IOException e) {
			System.exit(0);
		}
		return ans;
	}
	@Override
	public void saveItemsFromFile() {
		FileUtils temp = new FileUtils();
		List<String> val = null;
		try {
			val = temp.readInventoryFromFile();
			temp.writeInventoryToFile(val);
		}
		catch(IOException e) {
			System.exit(0);
		}
	}

	public void saveStaffFromFile() {
		FileUtils temp = new FileUtils();
		try{
			temp.writeStaffToFile(temp.readStaffFromFile());
		}
		catch(IOException e){
			System.exit(0);
		}
	}

	/**
	 * Loads the inventory from the specified file.
	 */
	private void loadInventory(String filename) {
		FileUtils temp = new FileUtils();
		List<String> itemLines = null;
		try{
			itemLines = temp.readInventoryFromFile();
		}
		catch(IOException e){

		}
		if (itemLines != null) {
			for (String line : itemLines) {
				String[] tokens = line.split(",");
				String itemName = tokens[0].replaceAll("'", "");
				double itemCost = Double.parseDouble(tokens[1]);
				int itemQuantity = Integer.parseInt(tokens[2]);
				int itemAisle = Integer.parseInt(tokens[3]);
				inventory.put(itemName, new Item(itemName, itemCost, itemQuantity, itemAisle));
			}
		} else {
			System.err.println("Error loading inventory: Inventory file is empty or cannot be read");
		}
	}

	/**
	 * Loads the staff members from the specified file.
	 */
	private void loadStaff(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				String staffName = tokens[0] + " " + tokens[1];
				int age = Integer.parseInt(tokens[2]);
				String role = tokens[3];
				String availability = tokens[4];
				staffMembers.put(staffName, new Staff(staffName, age, role, availability));
			}
		} catch (IOException e) {
			System.err.println("Error loading staff: " + e.getMessage());
		}
	}

	/**
	 * Loads the shift schedules from the specified file.
	 */
	private void loadShiftSchedules(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				shiftSchedules.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error loading shift schedules: " + e.getMessage());
		}
	}

	/**
	 * Adds a new item to the inventory.
	 */
	private void addItem(String[] tokens, StringBuilder writer) throws IOException {
		// Find the index of the last quote
		int lastQuoteIndex = tokens[0].lastIndexOf("'");

		if (lastQuoteIndex != -1 && lastQuoteIndex > 0) {
			// Extract the item name (excluding the quotes)
			String itemName = tokens[0].substring(1, lastQuoteIndex);

			// Split the remaining tokens (price, quantity, aisle) by whitespace
			String[] remainingTokens = tokens[0].substring(lastQuoteIndex + 1).trim().split("\\s+");

			double itemCost = Double.parseDouble(remainingTokens[0]);
			int itemQuantity = Integer.parseInt(remainingTokens[1]);
			int itemAisle = Integer.parseInt(remainingTokens[2]);

			inventory.put(itemName, new Item(itemName, itemCost, itemQuantity, itemAisle));
			writer.append(itemName).append(" was added to inventory");
			writer.append(System.lineSeparator());
		} else {
			// Handle the case when the input format is invalid
			writer.append("Invalid input format for adding an item");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Gets the cost of an item and writes it to the output file.
	 */
	private void getCost(String[] tokens, StringBuilder writer) throws IOException {
		String itemName = tokens[1].replaceAll("'", "");
		Item item = inventory.get(itemName);
		if (item != null) {
			writer.append(itemName).append(": $").append(String.format("%.2f", item.getPrice()));
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(itemName).append(" cannot be found");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Finds an item and writes its quantity and aisle to the output file.
	 */
	private void findItem(String[] tokens, StringBuilder writer) throws IOException {
		String itemName = tokens[1].replaceAll("'", "");
		Item item = inventory.get(itemName);
		if (item != null) {
			writer.append(item.getQuantity()).append(" ").append(itemName).append(" are available in ").append(item.getAisle());
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(itemName).append(" cannot be found");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Fires a staff member.
	 */
	private void fireStaff(String[] tokens, StringBuilder writer) throws IOException {
		String staffName = tokens[1].replaceAll("'", "");
		Staff staff = staffMembers.remove(staffName);
		if (staff != null) {
			writer.append(staffName).append(" was fired");
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(staffName).append(" cannot be found");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Hires a new staff member.
	 */

	private void hireStaff(String[] tokens, StringBuilder writer) throws IOException {
		String staffNameFirst = tokens[1].replaceAll("'", "");
		String staffNameLast = tokens[2].replaceAll("'", "");
		String staffName = staffNameFirst + " " + staffNameLast;
		int age = Integer.parseInt(tokens[3]);
		String role = getRoleString(tokens[4]);
		String availability = tokens[4];
		staffMembers.put(staffName, new Staff(staffName, age, role, availability));
		writer.append(staffName).append(" has been hired as a ").append(role);
		writer.append(System.lineSeparator());
	}

	/**
	 * Promotes or demotes a staff member.
	 */
	private void promoteStaff(String[] tokens, StringBuilder writer) throws IOException {
		String staffName = tokens[1].replaceAll("'", "");
		String newRole = getRoleString(tokens[2]);
		Staff staff = staffMembers.get(staffName);
		if (staff != null) {
			staffMembers.put(staffName, new Staff(staffName, staff.getAge(), newRole, staff.getAvailability()));
			writer.append(staffName).append(" was promoted to ").append(newRole);
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(staffName).append(" cannot be found");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Sells an item and updates the inventory.
	 */
	private void sellItem(String[] tokens, StringBuilder writer) throws IOException {
		String itemName = tokens[1].replaceAll("'", "");
		int quantity = Integer.parseInt(tokens[2]);
		Item item = inventory.get(itemName);
		if (item != null && item.getQuantity() >= quantity) {
			int newQuantity = item.getQuantity() - quantity;
			inventory.put(itemName, new Item(itemName, item.getPrice(), newQuantity, item.getAisle()));
			writer.append(quantity).append(" ").append(itemName).append(" was sold");
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(itemName).append(" could not be sold");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Gets the quantity of an item and writes it to the output file.
	 */
	private void getQuantity(String[] tokens, StringBuilder writer) throws IOException {
		String itemName = tokens[1].replaceAll("'", "");
		Item item = inventory.get(itemName);
		if (item != null) {
			writer.append(String.valueOf(item.getQuantity()));
			writer.append(System.lineSeparator());
		} else {
			writer.append("ERROR: ").append(itemName).append(" cannot be found");
			writer.append(System.lineSeparator());
		}
	}

	/**
	 * Returns the full role string based on the role code.
	 */
	private String getRoleString(String roleCode) {
		switch (roleCode) {
			case "M":
				return "Manager";
			case "C":
				return "Cashier";
			case "G":
				return "Gardening Expert";
			default:
				return "Unknown Role";
		}
	}

	private void sawPlanks(StringBuilder writer) {
		SawPrimePlanks sawPrimePlanks = new SawPrimePlanks();
		sawPrimePlanks.sawPrimePlanks();
	}

	private void createSchedule(StringBuilder writer) {
		StaffScheduler staffScheduler = new StaffScheduler();
		staffScheduler.scheduleStaff();
	}
	@Override
	public void takeAction() throws IOException {
		FileUtils commands = new FileUtils();
		List<String> comms;
		try {
			comms = commands.readCommandsFromFile();
		} catch (IOException e) {
			System.err.println("Error reading commands file: " + e.getMessage());
			return;
		}

		StringBuilder output = new StringBuilder();

		for (String current : comms) {
			String[] tokens = current.split(" ");
			String command = tokens[0];

			switch (command) {
				case "ADD":
					addItem(tokens, output);
					break;
				case "COST":
					getCost(tokens, output);
					break;
				case "EXIT":
					output.append("Thank you for visiting High's Hardware and Gardening!");
					output.append(System.lineSeparator());
					System.out.println("Press enter to continue...");
					try {
						System.in.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "FIND":
					findItem(tokens, output);
					break;
				case "FIRE":
					fireStaff(tokens, output);
					break;
				case "HIRE":
					hireStaff(tokens, output);
					break;
				case "PROMOTE":
					promoteStaff(tokens, output);
					break;
				case "SAW":
					sawPlanks(output);
					break;
				case "SCHEDULE":
					createSchedule(output);
					break;
				case "SELL":
					sellItem(tokens, output);
					break;
				case "QUANTITY":
					getQuantity(tokens, output);
					break;
				default:
					output.append("Invalid command: ").append(command);
					output.append(System.lineSeparator());
			}
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/edu/iu/c212/resources/output.txt"))) {
			writer.write(output.toString());
		} catch (IOException e) {
			System.err.println("Error writing output file: " + e.getMessage());
		}
	}
	public static void main(String[] args) throws IOException {
		Store storehigh = new Store();
		storehigh.takeAction();
	}
}