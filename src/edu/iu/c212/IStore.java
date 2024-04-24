package edu.iu.c212;

public interface IStore {
	List<Item> getItemsFromFile();
	
	List<Staff> getStaffFromFile();
	
	void saveItemsFromFile();
	
	void saveStaffFromFile();
	
	void takeAction();
}
