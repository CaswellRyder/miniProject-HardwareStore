package edu.iu.c212.models;

public class Staff {
    //Instance Variables
    private String fullName;
    private int age;
    private String role;
    private String availability;

    //Constructor
    public Staff(String name, int age, String role, String av) {
        this.fullName = name;
        this.age = age;
        this.role = role;
        this.availability = av;
    }

    //Getters
    public String getName() {return fullName;}
    public int getAge() {return age;}
    public String getRole() {return role;}
    public String getAvailability() {return availability;}

}
