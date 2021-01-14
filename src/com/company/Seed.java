package com.company;

public class Seed extends Food {

    enum Type {

        BIRDSEED(10),
        CORN(20);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Seed(int price) {
        super(price);
    }
}
