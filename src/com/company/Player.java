package com.company;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {

    private String name;
    private byte number;
    private long money;
    public static final long INITIAL_MONEY = 1000;
    private ArrayList<Byte> allHealth = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<Animal> deadAnimals = new ArrayList<>();
    private LinkedHashMap<Food, Integer> foods = new LinkedHashMap();


    private ArrayList<Integer> healthReductions = new ArrayList<>();

    public Player(String name, byte number) {
        this.setName(name);
        this.number = number;
        this.setMoney(INITIAL_MONEY);
    }

    public ArrayList<Integer> getHealthReductions() {
        return healthReductions;
    }

    public void setHealthReductions(ArrayList<Integer> healthReductions) {
        this.healthReductions = healthReductions;
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

    public void addFood(Food food, int kilos) {

        if (foods.containsKey(food)) {
            foods.replace(food, kilos + foods.get(food));
        } else {
            foods.put(food, kilos);
        }
    }

    public ArrayList getAnimals() {
        return animals;
    }

    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public void removeAnimal(int i) {
        animals.remove(i);
    }

    public ArrayList<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public void clearDeadAnimals() {
        deadAnimals.clear();
    }

    public LinkedHashMap<Food, Integer> getFoods() {
        return foods;
    }

    public boolean feed(Animal animalToFeed, Food food, int kilos) {

        boolean SUCCESSFUL = true;
        boolean UNSUCCESSFUL = false;

        // Check if compatible with diet
        boolean ate_food = animalToFeed.eat(food, kilos);

        if (ate_food) {
            foods.replace(food, foods.get(food) - kilos);
            if (foods.get(food) <= 0) {
                foods.remove(food);
            }
            return SUCCESSFUL;
        } else {
            return UNSUCCESSFUL;
        }
    }

    public Food getFood(int i) {
        //A Linked Hash Map is ordered and allows us to do this
        System.out.println(foods);
        System.out.println(foods.entrySet());
        System.out.println(foods.keySet());
        return (Food) foods.keySet().toArray()[i];
    }

    public void addDeadAnimals(Animal animal) {
        deadAnimals.add(animal);
    }

    public ArrayList[] getSickAnimals() {
        Random random = new Random();
        int r;
        ArrayList<Integer> fees = new ArrayList<>();
        ArrayList<Integer> sickAnimals = new ArrayList<>();
        int counter = 0;
        ArrayList<Animal> animals = getAnimals();
        for (Animal animal : animals){
            r = random.nextInt(100);
            if (r <= Game.PROBABILITY_SICK) {
                sickAnimals.add(counter);
                fees.add(Math.round((100 + Game.SICK_FEE) * animal.getOriginalPrice() / 100));
            }
            counter += 1;
        }

        ArrayList[] sickInfo = {sickAnimals, fees};

        return sickInfo;
    }

    public void letSickAnimalsDie(ArrayList<Integer> sickAnimals, int survivalRate) {

        Random random = new Random();
        int r = 0;

        Collections.reverse(sickAnimals);
        for (int i = sickAnimals.size() - 1; i >= 0; i--) {
            r = random.nextInt(100);
            if (survivalRate < r) {
                removeAnimal(sickAnimals.get(i));
            }
        }
    }
}
