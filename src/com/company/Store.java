package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

//    private HashMap<Animal.Type, ArrayList<Animal>> birds = new HashMap<Animal.Type, new ArrayList<>>();
//    private HashMap<Animal.Type, ArrayList<Animal>> cats = new HashMap<Animal.Type, new ArrayList<>>();
//    private HashMap<Animal.Type, ArrayList<Animal>> livestock = new HashMap<Animal.Type, new ArrayList<>>();
//    private HashMap<Animal.Type, ArrayList<Animal>> fishes = new HashMap<Animal.Type, new ArrayList<>>();
//    private HashMap<Animal.Type, ArrayList<Animal>> marineMammals = new HashMap<Animal.Type, new ArrayList<>>();

    //TODO as above
    //private ArrayList<Food> foodForSale = new ArrayList<>();

    public Store(Player customer, byte players) {
        this.customer = customer;
        this.customers = players;
        setupInventory();
    }

    private void setupInventory() {

        // For each type, stock up each type of animal for each animal type
        // Also, randomize gender
        //TODO Remember to restock (new chance at gender) every turn by running setupInventory() = endless store
        //TODO Display inventory (x blue whales etc)
        //for (int i = 0; i < Math.floor(Player.INITIAL_MONEY * customers / type.price); i++) {

        for (Bird.Type type: Bird.Type.values()){
            this.birds.put(type.toString(), new Bird(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
        }
        for (Fish.Type type: Fish.Type.values()){
            this.fishes.put(type.toString(), new Fish(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
        }
        for (Cat.Type type: Cat.Type.values()){
            this.cats.put(type.toString(), new Cat(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
        }
        for (Livestock.Type type: Livestock.Type.values()){
            this.livestock.put(type.toString(), new Livestock(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
        }
        for (MarineMammal.Type type: MarineMammal.Type.values()){
            this.marineMammals.put(type.toString(), new MarineMammal(type.toString(), "", new Random().nextBoolean(), (byte) 100, type.price));
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
    public boolean sellAnimal(String animalType, String specificAnimal) {
        Animal animalForSale = null;

        switch (animalType) {
            case "bird" -> animalForSale = birds.get(specificAnimal);
            case "cat" ->  animalForSale = cats.get(specificAnimal);
            case "livestock" -> animalForSale = livestock.get(specificAnimal);
            case "fish" -> animalForSale= fishes.get(specificAnimal);
            case "marine mammal" -> animalForSale = marineMammals.get(specificAnimal);
        }

        if (sell(animalForSale, animalForSale.getPrice())) {
            customer.addAnimal(animalForSale);
            customer.setMoney(customer.getMoney() - animalForSale.getPrice());
            return ENOUGH_MONEY;
        } else { return NOT_ENOUGH_MONEY;}
    }

    // The store is buying, the customer is selling
    public void buyAnimal(String typeOfAnimal, String animalString) {
        Animal animal = null;

        switch (typeOfAnimal) {
            case "bird" -> animal = this.birds.get(animalString);
            case "fish" -> animal = this.fishes.get(animalString);
            case "cat" -> animal = this.cats.get(animalString);
            case "livestock" -> animal = this.livestock.get(animalString);
            case "marine mammal" -> animal = this.marineMammals.get(animalString);
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
