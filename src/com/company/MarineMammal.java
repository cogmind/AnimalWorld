package com.company;

public class MarineMammal extends Animal {

    public enum Type {

        SEA_OTTER(25),
        SEAL(100),
        POLAR_BEAR(250),
        BLUE_WHALE(1000);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public MarineMammal(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
