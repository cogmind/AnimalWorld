package com.company;

public class Seed extends Food {

    enum Type {

        BIRD_SEED(70),
        CORN(120);

        int price;
        Type(int price){
            this.price = price;
        }
    }

    public Seed(String type, int price) {
        super(type, price);
    }
}
