package com.company;

public class Cat extends Animal {
    public enum Type {

        KITTEN(20, "MICE", 0.5, (byte) 0),
        HOUSE_CAT(30, "MICE", 1.0, (byte) 5),
        LYNX(120, "STEAK", 2.0, (byte) 2),
        PANTHER(180, "STEAK", 4.0, (byte) 3);

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
    public Cat(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
