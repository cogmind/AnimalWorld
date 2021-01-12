package com.company;

public class Fish extends Animal {
    public enum Type {
        GOLDFISH(5),
        COD(15),
        GIANT_CATFISH(40);

        int price;
        Type(int price){
            this.price = price;
        }
    }
    public Fish(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
