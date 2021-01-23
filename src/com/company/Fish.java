package com.company;

public class Fish extends Animal {
    public enum Type {

        GOLDFISH(5, "KRILL", 1.0, (byte) 6),
        COD(15, "KRILL", 1.0, (byte) 3),
        GIANT_CATFISH(40, "KRILL", 1.7, (byte) 4);

        int price;
        String diet;
        double foodFactor;
        byte offspring;

        Type(int price, String diet, double foodFactor, byte offspring){
            this.price = price;
            this.diet = diet;
            this.foodFactor = foodFactor;
            this.offspring = offspring;
        }
    }
    public Fish(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
