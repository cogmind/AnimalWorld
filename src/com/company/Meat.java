package com.company;

public class Meat extends Food {

    enum Type {

        MICE(100),
        RAW_STEAK(200);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Meat(String type, int price) {
        super(type, price);
    }
}
