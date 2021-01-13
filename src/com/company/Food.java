package com.company;

public abstract class Food {

    private String name;
    private int healthPoints = 100;
    private int price;

    public Food(int price) {

    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price * healthPoints / 100;
    }
}
