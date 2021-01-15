package com.company;

public class Cat extends Animal {
    public enum Type {

        KITTEN(20, "mice"),
        HOUSE_CAT(30, "mice"),
        LYNX(120, "raw steak"),
        PANTHER(180, "raw steak");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }
    public Cat(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
