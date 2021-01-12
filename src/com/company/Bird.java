package com.company;

public class Bird extends Animal {

    public enum Type {

        pigeon(10),
        parrot(15),
        owl(30),
        eagle(80);

        int price;

        Type(int price) {
            this.price = price;
        }
    }

    public Bird(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
