package com.company;

public class Bird extends Animal {

    public enum Type {

        PIGEON(10,"bird seeds"),
        PARROT(15, "bird seeds"),
        OWL(30, "mice"),
        EAGLE(80, "raw steak");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }

    public Bird(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
