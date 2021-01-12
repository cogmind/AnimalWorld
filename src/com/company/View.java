package com.company;

public class View {

    //TODO Gather all Sysouts here
    public View(){

    }

    private void print(String input){
        System.out.println("\n".repeat(60));
        System.out.println(input);
    }
    public void lineFeed() {
        print("\n");
    }

    public void howManyRounds() {
        print("How many rounds (" + Game.MIN_ROUNDS + "-" + Game.MAX_ROUNDS + ") would you like to play?");
    }


    public void roundsOutOfBounds(){
        print("Rounds must be between " + Game.MIN_ROUNDS + " and " + Game.MAX_ROUNDS + ". Try again");
    }

    public void howManyPlayers(){
        print("How many players ("+ Game.MIN_PLAYERS + "-" + Game.MAX_PLAYERS + ")?");
    }

    public void playersOutOfBounds(){
        print("The number of players has to be between " + Game.MIN_PLAYERS + " and " + Game.MAX_PLAYERS + ".");
    }

    public void displayMainMenu() {
        print("Options\n1. Buy animals\n2. Feed animals\n3. Skip round");
    }

    public void displayAnimalMenu(){
        //TODO animal store menu
        print("TMP Store menu goes here");
    }

    public void displayFeedMenu(){
        //TODO (animal) feed store menu
        print("TMP Feed menu goes here");
    }
}
