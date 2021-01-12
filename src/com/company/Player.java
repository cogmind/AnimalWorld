package com.company;

import java.util.ArrayList;

public class Player {

    private String name;
    private int money;
    private static final int INITIAL_MONEY = 1000;
    private ArrayList<Animal> animals = new ArrayList<>();

    public Player(String name){
        this.setName(name);
        this.setMoney(INITIAL_MONEY);
    }

    public String getName() {
        return name;
    }

    // Private because name changes are not allowed.
    private void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}
