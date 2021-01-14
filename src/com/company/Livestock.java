package com.company;

public class Livestock extends Animal {
    public enum Type {

        CATTLE(30),
        SHEEP(40),
        BISON(60);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Livestock(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
