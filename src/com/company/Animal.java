package com.company;

import java.util.Random;

public abstract class Animal {

    public enum Type {

        NULL(0, "", 0.0, (byte) 0);

        int price;
        String diet;
        double foodFactor;
        byte offspring;

        Type(int price, String diet, double foodFactor, byte offspring) {
            this.price = price;
            this.diet = diet;
            this.foodFactor = foodFactor;
            this.offspring = offspring;
        }
    }
    private String type;
    private String name;
    private boolean isFemale;
    private byte health;
    private int price;
    private String diet;
    double foodFactor;
    byte offspring;

    public Animal(String type, String name, boolean isFemale, byte health, int price, String diet, double foodFactor, byte offspring) {
        this.type = type;
        this.name = name;
        this.isFemale = isFemale;
        this.health = health;
        this.price = price;
        this.diet = diet;
        this.foodFactor = foodFactor;
        this.offspring = offspring;
    }

    public String getType() {
        return type;
    }

    public String getDiet() {
        return diet;
    }

    public double getFoodFactor() {
        return foodFactor;
    }

    public byte getOffspring() {
        return offspring;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(boolean isFemale) {
        this.isFemale = isFemale;
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

    public void fatigueHealth() {
        Random random = new Random();
        int lowerBound = 10;
        int upperBound = 30;
        int x = random.nextInt(upperBound - lowerBound) + lowerBound;
        health = (byte) ((int) health - x);
    }

    @Override
    public String toString() {
        String female = isFemale ? "Female" : "Male";
        return name + " " + View.capitalize(type) + " (" + female + ") "+ health + " HP. ";
    }

    public boolean eat(Food food, int kilos) {

        boolean ATE_FOOD = true;
        boolean DID_NOT_EAT_FOOD = false;

        diet = View.capitalize(diet).replace("_", " ");

        if (diet.equals(food.toString()))  {
            while (kilos > 0) {
                setHealth((byte) Math.round(((0.10 / getFoodFactor()) + 1) * getHealth()));
                if (getHealth() > 100) {
                    setHealth((byte) 100);
                }
                kilos -= 1;
            }
            return ATE_FOOD;
        } else {
            return DID_NOT_EAT_FOOD;
        }
    }

    public boolean getGender() {
        return isFemale;
    }

}
