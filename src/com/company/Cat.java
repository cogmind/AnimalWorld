package com.company;

public class Cat extends Animal {
    public enum Type {

        KITTEN(20, "MICE"),
        HOUSE_CAT(30, "MICE"),
        LYNX(120, "STEAK"),
        PANTHER(180, "STEAK");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }
    public Cat(String type, String name, boolean isFemale, byte health, int price, String diet){
        super(type, name, isFemale, health, price, diet);
    }
}
