package com.company;

import java.util.ArrayList;

public class View {

    //TODO Gather all Sysouts here
    public View() {

    }

    private void print(String input) {
        System.out.println("\n".repeat(60));
        System.out.println(input);
    }

    private void error(String input){
        System.err.println(input);
    }

    public void lineFeed() {
        print("\n");
    }

    public void displayMoney(long money){
        System.out.print(money + "€\t");
    }

    public void displayAverageHealth(ArrayList<Integer> health){
        // TODO Compute average health
        int HP_TOTAL = 0;
        int length = 0;
        for(int hp : health) {
            HP_TOTAL += hp;
            length += 1;
        }
        System.out.printf("Total HP: %f Avg HP: %d", ((HP_TOTAL + 0.0) / length), HP_TOTAL);
    }

    public void howManyRounds() {
        System.out.println("How many rounds (" + Game.MIN_ROUNDS + "-" + Game.MAX_ROUNDS + ") would you like to play?");
    }


    public void roundsOutOfBounds() {
        error("Rounds must be between " + Game.MIN_ROUNDS + " and " + Game.MAX_ROUNDS + ". Try again");
    }

    public void howManyPlayers() {
        print("How many players ("+ Game.MIN_PLAYERS + "-" + Game.MAX_PLAYERS + ")?");
    }

    public void playersOutOfBounds() {
        error("The number of players has to be between " + Game.MIN_PLAYERS + " and " + Game.MAX_PLAYERS + ".");
    }

    public void displayMainMenu() {
        System.out.println("OPTIONS (Select one)\n1. Buy animals\n2. Buy food \n3. Feed animals\n4. Breed new animals (success rate " + Game.BREEDING_SUCCESS_RATE + "%) \n5. Sell animals\n");
    }

    public void pleaseSelect(){
        error("Please select an option between 1 and 5.");
    }

    public void displayBuyAnimalMenu() {
        //TODO animal store menu
        print("TMP Store menu goes here");
    }

    public void displayBuyFoodMenu() {
        //TODO food store menu
        print("TMP Food menu goes here");
    }

    public void displayFeedMenu() {
        //TODO feeding menu
        print("TMP Feed menu goes here");
    }

    public void displayBreedMenu() {
        print("TMP Breeding menu goes here");

    }

    public void displaySellAnimalMenu() {
        print("TMP Sell animal menu goes here");
    }

    public void endOfGame() {
        System.out.println("End of game. Exiting...");
        System.exit(0);
    }
}
