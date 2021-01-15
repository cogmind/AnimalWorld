package com.company;

public class MarineMammal extends Animal {

    public enum Type {

        SEA_OTTER(25, "HERRING"),
        SEAL(100, "HERRING"),
        POLAR_BEAR(250, "STEAK"),
        BLUE_WHALE(1000, "KRILL");

        int price;
        String diet;

        Type(int price, String diet){
            this.price = price;
            this.diet = diet;
        }
    }

    public MarineMammal(String type, String name, boolean isFemale, byte health, int price, String diet){
        super(type, name, isFemale, health, price, diet);
    }
}
