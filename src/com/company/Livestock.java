package com.company;

public class Livestock extends Animal {

    public enum Type {

        CATTLE(30, "corn"),
        SHEEP(40, "corn"),
        BISON(60, "corn");

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }

    public Livestock(String type, String name, boolean isFemale, byte health, int price){
        super(type, name, isFemale, health, price);
    }
}
