package com.company;

import java.io.Serializable;

public abstract class Food implements Serializable {

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

    @Override
    public String toString(){
        return View.capitalize(type).replace("_", " ");
    }
}
