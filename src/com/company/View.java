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

    public void displayAverageHealth(int averageHealth){
        System.out.printf("Avg HP: %d\n", averageHealth);
    }

    public void displayTotalHealth(int totalHealth){
        System.out.printf("Total HP: %d\t", totalHealth);
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

    public void menuOutOfBounds(byte menu_end){
        error("Please select an option between 1 and " + menu_end + ".");
    }

    public void displayBuyAnimalMenu() {
        print("BUY ANIMAL\nOPTIONS\n1. Bird\n2. Cat\n3. Livestock\n4. Fish\n5. Marine mammal\n");

    }

    public void displayBuyBirdMenu() {
        print("BIRD MENU\n1. Pigeon\n2. Parrot\n3. Owl\n4. Eagle\n");
    }

    public void displayBuyCatMenu() {
        print("CAT MENU\n1. Kitten\n2. House cat\n3. Lynx\n4. Panther\n");
    }

    public void displayBuyLivestockMenu() {
        print("LIVESTOCK MENU\n1. Cattle\n2. Sheep\n3. Bison\n");
    }

    public void displayBuyFishMenu() {
        print("FISH MENU\n1. Goldfish\n2. Giant cat fish\n");
    }

    public void displayBuyMarineMammalMenu() {
        print("MARINE MAMMAL MENU\n1. Sea otter\n2. Seal\n3. Polar bear\n4. Blue whale\n");
    }


    public void displayBuyFoodMenu() {
        print("BUY FOOD\nOPTIONS\n1. Seeds\n2. Meats\n3. Fish Food");
    }

    public void displayFeedMenu() {
        //TODO feeding menu
        print("FEEDING MENU\nSelect what animal to feed");
    }

    public void displayBreedMenu() {
        //TODO
        print("TMP Breeding menu goes here");

    }

    //TODO
    public void displayAnimalsMenu(ArrayList<Animal> animals) {
        print("OWNED ANIMALS\n");
        int i = 0;
        for (Animal animal : animals) {
            i++;
            System.out.println(i + ". " + animal);
        }
    }

    public void endOfGame() {
        System.out.println("End of game. Exiting...");
        System.exit(0);
    }

    public void readyPlayerNo(byte number) {
        System.out.println("Ready player no " + number);
    }

    public void unsuccessfulSale() {
        print("Not enough money. Sale unsuccessful.");
    }

    public void successfulSale() {
        print("Sufficient funds. Sale successful!");
    }

    public void displayBuySeedMenu() {
        print("SEEDS MENU\n1. Bird seeds\n2. Corn");
    }

    public void displayBuyMeatMenu() {
        print("MEATS MENU\n1. Mice\n2. Raw steak");
    }

    public void displayBuyFishFoodMenu() {
        print("FISH FOOD MENU\n1. Krill\n2. Herring");
    }

    public void displaySelectFoodMenu(ArrayList<Food> foods) {
        for(Food food : foods) {
            System.out.println(food);
        }
    }

    public void successfulFeed() {
        System.out.println("Feed successful!");
    }

    public void unsuccessfulFeed() {
        System.out.println("Cannot feed that food to the animal. Wrong diet!");
    }
}
