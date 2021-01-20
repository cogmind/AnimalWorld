package com.company;

public class Bird extends Animal {

    public enum Type {

        PIGEON(10,"BIRD_SEED", 1.3, (byte) 3),
        PARROT(15, "BIRD_SEED", 1.3, (byte) 2),
        OWL(30, "MICE", 2.0, (byte) 3),
        EAGLE(80, "STEAK", 4.0, (byte) 2);

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

    public Bird(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
