package com.company;

public class MarineMammal extends Animal {

    public enum Type {

        SEA_OTTER(25, "herring"),
        SEAL(100, "herring"),
        POLAR_BEAR(250, "raw steak"),
        BLUE_WHALE(1000, "krill");

        int price;
        String diet;

        Type(int price, String diet){
            this.price = price;
            this.diet = diet;
        }
    }

    public MarineMammal(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
