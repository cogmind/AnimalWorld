package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Store {

    private Player customer;
    private byte customers;
    private final static boolean ENOUGH_MONEY = true;
    private final static boolean NOT_ENOUGH_MONEY = false;
    private long money = 40000;

    private HashMap<String, Animal> birds = new HashMap<String, Animal>();
    private HashMap<String, Animal> cats = new HashMap<String, Animal>();
    private HashMap<String, Animal> livestock = new HashMap<String, Animal>();
    private HashMap<String, Animal> fishes = new HashMap<String, Animal>();
    private HashMap<String, Animal> marineMammals = new HashMap<String, Animal>();

    private HashMap<String, Food> seeds = new HashMap<String, Food>();
    private HashMap<String, Food> meats = new HashMap<String, Food>();
    private HashMap<String, Food> fishFood = new HashMap<String, Food>();

    public Store(Player customer, byte players) {
        this.customer = customer;
        this.customers = players;
        setupInventory();
    }

    private void setupInventory() {

        // Only one randomized gender available per turn

        // Animals in stock
        for (Bird.Type type: Bird.Type.values()){
            this.birds.put(type.toString(), new Bird(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price, type.diet, type.foodFactor, type.offspring));
        }
        for (Fish.Type type: Fish.Type.values()){
            this.fishes.put(type.toString(), new Fish(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price, type.diet, type.foodFactor, type.offspring));
        }
        for (Cat.Type type: Cat.Type.values()){
            this.cats.put(type.toString(), new Cat(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price, type.diet, type.foodFactor, type.offspring));
        }
        for (Livestock.Type type: Livestock.Type.values()){
            this.livestock.put(type.toString(), new Livestock(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price, type.diet, type.foodFactor, type.offspring));
        }
        for (MarineMammal.Type type: MarineMammal.Type.values()){
            this.marineMammals.put(type.toString(), new MarineMammal(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price, type.diet, type.foodFactor, type.offspring));
        }

        // Foods in stock
        for (Seed.Type type: Seed.Type.values()){
            this.seeds.put(type.toString(), new Seed(type.toString(), type.price));
        }
        for (Meat.Type type: Meat.Type.values()){
            this.meats.put(type.toString(), new Meat(type.toString(), type.price));
        }
        for (FishFood.Type type: FishFood.Type.values()){
            this.fishFood.put(type.toString(), new FishFood(type.toString(), type.price));
        }

    }

    public boolean sell(Object animalOrFood, int price) {
        // Helper method for sellAnimal and sellFood
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
    public boolean sellAnimal(String animalType, String specificAnimal, View view, Scanner scanner) {

        Animal animalForSale = null;

        //TODO comment out DEBUG statements
        System.out.printf("animalType: %s, specificAnimal: %s", animalType, specificAnimal);

        switch (animalType) {
            case "bird" -> animalForSale = birds.get(specificAnimal);
            case "cat" ->  animalForSale = cats.get(specificAnimal);
            case "livestock" -> animalForSale = livestock.get(specificAnimal);
            case "fish" -> animalForSale= fishes.get(specificAnimal);
            case "marine mammal" -> animalForSale = marineMammals.get(specificAnimal);
        }

        if (sell(animalForSale, animalForSale.getPrice())) {
            view.pleaseEnterName();
            animalForSale.setName(scanner.nextLine());
            customer.addAnimal(animalForSale);
            customer.setMoney(customer.getMoney() - animalForSale.getPrice());
            return ENOUGH_MONEY;
        } else { return NOT_ENOUGH_MONEY;}
    }

    // The store is buying, the customer is selling
    public void buyAnimal(Animal animal) {

        customer.setMoney(animal.getPrice());
        money -= animal.getPrice(); //TODO consider view displayStoreMoney

    }

    // The store is selling, the customer is buying
    public boolean sellFood(String foodType, String specificFood) {

        Food foodForSale = null;

        switch (foodType) {
            case "seed" -> foodForSale = seeds.get(specificFood);
            case "meat" ->  foodForSale = meats.get(specificFood);
            case "fish food" -> foodForSale = fishFood.get(specificFood);
        }

        if (sell(foodForSale, foodForSale.getPrice())) {
            customer.addFood(foodForSale);
            customer.setMoney(customer.getMoney() - foodForSale.getPrice());
            return ENOUGH_MONEY;
        } else {
            return NOT_ENOUGH_MONEY;
        }
    }
}
