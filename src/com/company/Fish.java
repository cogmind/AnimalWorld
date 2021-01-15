package com.company;

public class Fish extends Animal {
    public enum Type {

        GOLDFISH(5, "KRILL"),
        COD(15, "KRILL"),
        GIANT_CATFISH(40, "KRILL");

        int price;
        String diet;

        Type(int price, String diet){
            this.price = price;
            this.diet = diet;
        }
    }
    public Fish(String type, String name, boolean isFemale, byte health, int price, String diet){
        super(type, name, isFemale, health, price, diet);
    }
}
