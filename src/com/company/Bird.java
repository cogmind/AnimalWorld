package com.company;

public class Bird extends Animal {

    public enum Type {

        PIGEON(10,"BIRD_SEED"),
        PARROT(15, "BIRD_SEED"),
        OWL(30, "MICE"),
        EAGLE(80, "STEAK");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }

    public Bird(String type, String name, boolean isFemale, byte health, int price, String diet){
        super(type, name, isFemale, health, price, diet);
    }
}
