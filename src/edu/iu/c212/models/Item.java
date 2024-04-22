package edu.iu.c212.models;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private int aisleNum;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAisle() {
        return aisleNum;
    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public int setAisle() {
//        return aisleNum;
//    }

    public String toString() {
        return name + " $" + price + " " + quantity;
    }
}
