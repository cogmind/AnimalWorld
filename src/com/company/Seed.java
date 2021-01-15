package com.company;

public class Seed extends Food {

    enum Type {

        BIRDSEED(100),
        CORN(200);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Seed(String type, int price) {
        super(type, price);
    }
}
