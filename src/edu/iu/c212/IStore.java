package edu.iu.c212;
import edu.iu.c212.models.Staff;

import java.io.IOException;
import java.util.List;


public interface IStore {
	List<String> getItemsFromFile();

	List<Staff> getStaffFromFile();

	void saveItemsToFile();

	void saveStaffToFile();

	void takeAction() throws IOException;
}