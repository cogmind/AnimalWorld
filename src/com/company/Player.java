package com.company;

import java.util.ArrayList;

public class Player {

    private String name;
    private byte number;
    private long money;
    public static final long INITIAL_MONEY = 1000;
    private ArrayList<Byte> allHealth = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Food> foods = new ArrayList<>();

    public Player(String name, byte number){
        this.setName(name);
        this.number = number;
        this.setMoney(INITIAL_MONEY);
    }

    public String getName() {
        return name;
    }

    public byte getNumber() {
        return number;
    }

    // Private because name changes are not allowed.
    private void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void updateAllHealth() {
        allHealth.clear();
        for(Animal animal : animals) {
            allHealth.add(animal.getHealth());
        }
    }

    public int getAverageHealth() {

        if (allHealth.size() > 0) {
            int hp_total = 0;
            int length = 0;
            for (int hp : allHealth) {
                hp_total += hp;
                length += 1;
            }
            // TODO Problematic casting
            return (int) Math.round((Double.valueOf(hp_total) / Double.valueOf(length)));
        } else {
            return 0;
        }
    }

    public int getTotalHealth() {

        if (allHealth.size() > 0) {
            int hp_total = 0;
            for (int hp : allHealth) {
                hp_total += hp;
            }
            return hp_total;
        } else {
            return 0;
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public ArrayList getAnimals() {
        return animals;
    }

    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    public void removeAnimal(int i) {
        animals.remove(i);
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public boolean feed(Animal animalToFeed, Food food) {

        boolean SUCCESSFUL = true;
        boolean UNSUCCESSFUL = false;

        // Check if compatible with diet
        if (animalToFeed.eat(food)) {
            foods.remove(food);
            return SUCCESSFUL;
        } else {
            return UNSUCCESSFUL;
        }
    }

    public Food getFood(int i) {
        return foods.get(i);
    }
}
