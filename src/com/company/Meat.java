package com.company;

public class Meat extends Food {

    enum Type {

        MICE(200),
        RAW_STEAK(500);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Meat(String type, int price) {
        super(type, price);
    }
}
