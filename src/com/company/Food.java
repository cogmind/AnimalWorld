package com.company;

public abstract class Food {

    private String type;
    private int healthPoints = 10;
    private int price;

    public Food(String type, int price) {
        this.type = type;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price * healthPoints / 100;
    }
}
