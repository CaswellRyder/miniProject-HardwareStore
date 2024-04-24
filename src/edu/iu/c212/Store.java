import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.StaffScheduler;
import edu.iu.c212.utils.FileUtils;

public class Store implements IStore{
	
	public Store() {
		takeAction();
	}
	
	public List<Item> getItemsFromFile(){
		FileUtils temp = new FileUtils();
		List<Item> ans = null;
		try {
			ans = temp.readInventoryFromFile();
		}
		catch(IOException e) {
			System.exit(0);
		}
		return ans;
	}
	
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
	
	public void saveItemsFromFile() {
		FileUtils temp = new FileUtils();
		temp.writeInventoryToFile(getItemsFromFile());
	}
	
	public void saveStaffFromFile() {
		FileUtils temp = new FileUtils();
		temp.writeStaffToFile(getStaffFromFile());
	}
	
	public void takeAction() {
		FileUtils commands = new FileUtils();
		List<String> comms = null;
		try {
			comms = commands.readCommandsFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String current : comms) {
			String command = current.split(" ")[0];
			Commands commandList = new Commands();
			
			if(command.equals("ADD")) {
				commandList.addItem();
			}
			else if (command.equals("COST")) {
				commandList.costOfItem();
			}
			else if (command.equals("FIND")) {
				commandList.findItem();
			}
			else if (command.equals("FIRE")) {
				commandList.firePerson();
			}
			else if (command.equals("HIRE")) {
				commandList.hirePerson();
			}
			else if (command.equals("PROMOTE")) {
				commandList.promotePerson();
			}
			else if (command.equals("SAW")) {
				SawPrimePlanks planks = new SawPrimePlanks();
				planks.sawPrimePlanks();
			}
			else if (command.equals("SCHEDULE")) {
				StaffScheduler schedule = new StaffScheduler();
				schedule.scheduleStaff();
			}
			else if (command.equals("SELL")) {
				commandList.sellItem();
			}
			else if(command.equals("QUANITY")) {
				commandList.quantityOfItem();
			}
		}
		
	}
}
