package com.company;

public class FishFood extends Food {

    enum Type {

        KRILL(5),
        HERRING(15);

        int price;
        Type(int price){
            this.price = price;
        }
    }
    public FishFood(int price){
        super(price);
    }


}
