package com.company;

public abstract class Food {

    private String type;
    private int price;

    public Food(String type, int price) {
        this.type = type;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String toString(){
        return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase().replace("_", " ");
    }
}
