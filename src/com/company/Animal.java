package com.company;

public abstract class Animal {

    private String name;
    private boolean isFemale;
    private byte health;
    private int price;

    public Animal(String name, boolean isFemale, byte health, int price) {
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
