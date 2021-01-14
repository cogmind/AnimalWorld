package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Store {

    private Player customer;
    private byte customers;
    private final static boolean ENOUGH_MONEY = true;
    private final static boolean NOT_ENOUGH_MONEY = false;
    private long money = 40000;
    private ArrayList<Animal> birds = new ArrayList<>();
    private ArrayList<Animal> fishes = new ArrayList<>();
    private ArrayList<Animal> cats = new ArrayList<>();
    private ArrayList<Animal> livestock = new ArrayList<>();
    private ArrayList<Animal> marineMammals = new ArrayList<>();

    private ArrayList<Food> foodForSale = new ArrayList<>();

    public Store(Player customer, byte players) {
        this.customer = customer;
        this.customers = players;
        setupInventory();
    }

    private void setupInventory() {
        // For each type, stock up k worth of animals for each animal type depending on
        // how many players and initial money
        // Also, randomize gender
        for (Bird.Type type: Bird.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.birds.add(new Bird(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Fish.Type type: Fish.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.fishes.add(new Fish(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Cat.Type type: Cat.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.cats.add(new Cat(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Livestock.Type type: Livestock.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.livestock.add(new Livestock(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (MarineMammal.Type type: MarineMammal.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.marineMammals.add(new MarineMammal(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }


        //this.foodForSale;
    }

    public boolean sell(Object animalOrFood, int price) {

        boolean enoughMoney;

        if (customer.getMoney() < price) {
            enoughMoney = NOT_ENOUGH_MONEY;
        } else {
            enoughMoney = ENOUGH_MONEY;
            customer.setMoney(customer.getMoney() - price);
        }
        return enoughMoney;
    }

    // The store is selling, the customer is buying
    public boolean sellAnimal(Animal animal) {
        if (sell(animal, animal.getPrice())) {
            return ENOUGH_MONEY;
        } else { return NOT_ENOUGH_MONEY;}
    }

    // The store is buying, the customer is selling
    public void buyAnimal(String typeOfAnimal, Animal animal) {

        switch (typeOfAnimal) {
            case "bird" -> this.birds.remove(animal);
            case "fish" -> this.fishes.remove(animal);
            case "cat" -> this.cats.remove(animal);
            case "livestock" -> this.livestock.remove(animal);
            case "marine mammal" -> this.marineMammals.remove(animal);
        }
        money -= animal.getPrice(); //TODO view displayStoreMoney
    }

    // The store is selling, the customer is buying
    public boolean sellFood(Food food) {
        if (sell(food, food.getPrice())) {
            return ENOUGH_MONEY;
        } else {
            return NOT_ENOUGH_MONEY;
        }
    }
}
