package com.company;

public abstract class Animal {

    public enum Type {
    }
    private String type;
    private String name;
    private boolean isFemale;
    private byte health;

    public int getPrice() {
        return price;
    }

    private int price;

    public Animal(String type, String name, boolean isFemale, byte health, int price) {
        this.type = type;
        this.name = name;
        this.isFemale = isFemale;
        this.health = health;
        this.price = price;
    }

    public byte getHealth() {
        return health;
    }

    public void setHealth(byte health) {
        this.health = health;
    }

    public void eat(Food food) {

    }

    public void die() {

    }
}
