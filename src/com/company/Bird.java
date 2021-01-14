package com.company;

public class Bird extends Animal {

    public enum Type {

        PIGEON(10),
        PARROT(15),
        OWL(30),
        EAGLE(80);

        int price;

        Type(int price) {
            this.price = price;
        }
    }

    public Bird(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
