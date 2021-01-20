package com.company;

public class MarineMammal extends Animal {

    public enum Type {

        SEA_OTTER(25, "HERRING", 1.0, (byte) 1),
        SEAL(100, "HERRING", 5.0, (byte) 3),
        POLAR_BEAR(250, "STEAK", 4.0, (byte) 2),
        BLUE_WHALE(1000, "KRILL", 8.0,(byte) 1);

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

    public MarineMammal(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
