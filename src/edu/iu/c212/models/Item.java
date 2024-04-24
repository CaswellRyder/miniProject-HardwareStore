package edu.iu.c212.models;

public class Item {
    //Instance Variables
    private String name;
    private double price;
    private int quantity;
    private int aisleNum;
    private int plankLength;
    private boolean isPlank;

    //Constructor
    public Item(String name, double price, int quantity, int aisleNum) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.aisleNum = aisleNum;
        this.plankLength = 0; // Initialize plankLength to 0 by default
        this.isPlank = false; // Initialize isPlank to false by default
    }

    //Getters
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}
    public int getAisle() {return aisleNum;}
    public int getPlankLength() {return plankLength;}
    public boolean isPlank() {return isPlank;}

    //Setters
    public void setPlankLength(int plankLength) {
        this.plankLength = plankLength;
    }

    public void setIsPlank(boolean isPlank) {
        this.isPlank = isPlank;
    }
}