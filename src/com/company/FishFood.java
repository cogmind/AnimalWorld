package com.company;

public class FishFood extends Food {

    enum Type {

        KRILL(50),
        HERRING(180);

        int price;
        Type(int price){
            this.price = price;
        }
    }
    public FishFood(String type, int price){
        super(type, price);
    }

}
