package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Store {

    private Player customer;
    private byte customers;
    private final static boolean ENOUGH_MONEY = true;
    private final static boolean NOT_ENOUGH_MONEY = false;
    private long money = 40000;
    ArrayList<Animal> animalsForSale = new ArrayList<>();
    ArrayList<Food> foodForSale = new ArrayList<>();

    public Store(Player customer, byte players) {
        this.customer = customer;
        this.customers = players;
        setupInventory();
    }

    private void setupInventory() {
        // For each type, buy k worth of animals for each animal type depending on how many players and initial money
        for (Bird.Type type: Bird.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.animalsForSale.add(new Bird(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Fish.Type type: Fish.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.animalsForSale.add(new Fish(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Cat.Type type: Cat.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.animalsForSale.add(new Cat(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (Livestock.Type type: Livestock.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.animalsForSale.add(new Livestock(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
            }
        }
        for (MarineMammal.Type type: MarineMammal.Type.values()){
            for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {
                this.animalsForSale.add(new MarineMammal(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
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

    public boolean buyAnimal(Animal animal) {
        //TODO
        this.animalsForSale.remove(animal);
        return false;
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
