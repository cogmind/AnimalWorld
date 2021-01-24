package com.company;

public class Cat extends Animal {
    public enum Type {

        KITTEN(20, new String[]{"MICE", "HERRING"}, 1.1, (byte) 0),
        HOUSE_CAT(30, new String[]{"MICE", "HERRING"}, 1.5, (byte) 5),
        LYNX(120, new String[]{"STEAK", "MICE", "HERRING"}, 1.7, (byte) 2),
        PANTHER(180, new String[]{"STEAK"}, 2.0, (byte) 3);

        int price;
        String[] diet;
        double foodFactor;
        byte offspring;

        Type(int price, String[] diet, double foodFactor, byte offspring) {
            this.price = price;
            this.diet = diet;
            this.foodFactor = foodFactor;
            this.offspring = offspring;
        }
    }
    public Cat(String type, String name, boolean isFemale, byte health, int price, String[] diet, double foodFactor, byte offspring){
        super(type, name, isFemale, health, price, diet, foodFactor, offspring);
    }
}
