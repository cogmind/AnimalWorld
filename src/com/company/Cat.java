package com.company;

public class Cat extends Animal {
    public enum Type {
        KITTEN(20),
        HOUSECAT(30),
        LYNX(120),
        PANTHER(180);

        int price;
        Type(int price){
           this.price = price;
        }
    }
    public Cat(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
