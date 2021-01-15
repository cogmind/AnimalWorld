package com.company;

public abstract class Animal {

    public enum Type {

        NULL(0, "");

        public int getPrice() {
            return price;
        }

        public String getDiet() {
            return diet;
        }

        int price;
        String diet;

        Type(int price, String diet) {
            this.price = price;
            this.diet = diet;
        }
    }
    private String type;
    private String name;
    private boolean isFemale;
    private byte health;
    private int price;
    private String diet;

    public Animal(String type, String name, boolean isFemale, byte health, int price, String diet) {
        this.type = type;
        this.name = name;
        this.isFemale = isFemale;
        this.health = health;
        this.price = price;
        this.diet = diet;
    }

    public int getPrice() {
        return Math.round(price * health / 100);
    }

    public byte getHealth() {
        return health;
    }

    public void setHealth(byte health) {
        this.health = health;
    }

    @Override
    public String toString() {
        String female = isFemale ? "Female" : "Male";
        return "" + type + name + " (" + female + ") "+ health + " HP";
    }

    public boolean eat(Food food) {
        boolean ATE_FOOD = true;
        boolean DID_NOT_EAT_FOOD = false;

        System.out.println(diet);
        System.out.println(food.toString());
        if (diet == food.toString()) {
            setHealth((byte)Math.round(1.10 * getHealth()));
            if (getHealth() > 100) {
                setHealth((byte) 100);
            }
            return ATE_FOOD;
        } else {
            return DID_NOT_EAT_FOOD;
        }
    }

    public void die() {

    }
}
