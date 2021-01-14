package com.company;

public class Meat extends Food {

    enum Type {

        MICE(20),
        RAW_STEAK(50);

        int price;
        Type(int price){
            this.price = price;
        }
    }
    public Meat(int price) {
        super(price);
    }
}
