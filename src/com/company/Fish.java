package com.company;

public class Fish extends Animal {
    public enum Type {

        GOLDFISH(5, "krill"),
        COD(15, "krill"),
        GIANT_CATFISH(40, "krill");

        int price;
        String diet;

        Type(int price, String diet){
            this.price = price;
            this.diet = diet;
        }
    }
    public Fish(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
