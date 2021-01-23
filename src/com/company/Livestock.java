package com.company;

public class Livestock extends Animal {

    public enum Type {

        CATTLE(30, "CORN", 1.5, (byte) 1),
        SHEEP(40, "CORN", 1.0, (byte) 1),
        BISON(60, "CORN", 1.7, (byte) 1);

        int price;
        String diet;
        double foodFactor;
        byte offspring;

        Type(int price, String diet, double foodFactor, byte offspring) {
            this.price = price;
            this.diet = diet;
            this.foodFactor = foodFactor;
            this.offspring = offspring;
        }
    }

    public Livestock(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
