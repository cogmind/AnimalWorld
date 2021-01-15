package com.company;

public class Livestock extends Animal {

    public enum Type {

        CATTLE(30, "CORN"),
        SHEEP(40, "CORN"),
        BISON(60, "CORN");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }

    public Livestock(String type, String name, boolean isFemale, byte health, int price, String diet){
        super(type, name, isFemale, health, price, diet);
    }
}
