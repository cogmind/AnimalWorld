package com.company;

import java.util.*;

public class View {

    // Gather all Sysouts and Syserrors here
    public View() {

    }

    public static String capitalize(String input) { return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();}

    public void youNeedAtLeastTwoAnimals() {
        System.err.println("You need at least two animals to breed.");
    }

    private void printMenu(String input) {
        System.out.println("\n".repeat(60));
        System.out.println(input);
    }

    private void displayError(String input){
        System.err.println(input);
    }


    public void createLineFeed() {
        System.out.println("\n");
    }

    public void create60Lines() {
        printMenu("");
    }

    public void displayAnimalWorld() {
        System.out.println("ANIMAL WORLD v1.0 by Daniel Labbé");
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

    public void displayAnimals(ArrayList<Animal> animals, ArrayList<Integer> healthReductions) {

        Iterator<Animal> animalIterator = animals.iterator();
        Iterator<Integer> healthReductionIterator = healthReductions.iterator();

        while (animalIterator.hasNext() && healthReductionIterator.hasNext()) {
            System.out.printf(animalIterator.next().toString() + "(-" + healthReductionIterator.next() + " HP). ");
        }
    }

    public void displayFoods(LinkedHashMap<Food, Integer> foods) {
        System.out.println();
        for (Map.Entry kilosFood : foods.entrySet()) {
            System.out.print(kilosFood.getKey() + ": " + kilosFood.getValue() + "kg ");
        }
        System.out.println();
    }

    public void howManyRounds() {
        System.out.println("How many rounds (" + Game.MIN_ROUNDS + "-" + Game.MAX_ROUNDS + ") would you like to play?");
    }

    public void roundsOutOfBounds() {
        displayError("Rounds must be between " + Game.MIN_ROUNDS + " and " + Game.MAX_ROUNDS + ". Try again");
    }

    public void howManyPlayers() {
        System.out.println("How many players ("+ Game.MIN_PLAYERS + "-" + Game.MAX_PLAYERS + ")?");
    }

    public void playersOutOfBounds() {
        displayError("The number of players has to be between " + Game.MIN_PLAYERS + " and " + Game.MAX_PLAYERS + ".");
    }

    public void pleaseEnterNameForPlayer(byte number) {
        System.out.print("Please enter a name for player number " + number + ": ");
    }

    public void displayMainMenu() {
        System.out.println("OPTIONS (Select one)" +
                "\n1. Buy animals" +
                "\n2. Buy food " +
                "\n3. Feed animals" +
                "\n4. Breed new animals (success rate " + Game.BREEDING_SUCCESS_RATE + "%) " +
                "\n5. Sell animals" +
                "\n6. Save game" +
                "\n7. Quit game"
                );
    }

    public void menuOutOfBounds(byte menu_end){
        displayError("Please select an option between 1 and " + menu_end + ".");
    }

    public void displayEndOfTurn() {
        System.out.println("End of turn. Press ENTER");
    }

    public void displayBuyAnimalMenu() {
        printMenu("BUY ANIMAL\nOPTIONS\n1. Bird\n2. Cat\n3. Livestock\n4. Fish\n5. Marine mammal\n");

    }

    public void displayBuyBirdMenu() {
        printMenu("BIRD MENU\n1. Pigeon (" + Bird.Type.PIGEON.price + "€" +
                ")\n2. Parrot (" + Bird.Type.PARROT.price + "€" +
                ")\n3. Owl (" + Bird.Type.OWL.price  + "€" +
                ")\n4. Eagle (" + Bird.Type.EAGLE.price + "€"
                );
    }

    public void displayBuyCatMenu() {
        printMenu("CAT MENU\n1. Kitten (" + Cat.Type.KITTEN.price + "€)" +
                "\n2. House cat (" + Cat.Type.HOUSE_CAT.price + "€)" +
                "\n3. Lynx (" + Cat.Type.LYNX.price + "€)" +
                "\n4. Panther (" + Cat.Type.PANTHER.price + "€)"
                );
    }

    public void displayBuyLivestockMenu() {
        printMenu("LIVESTOCK MENU" +
                "\n1. Cattle (" + Livestock.Type.CATTLE.price + "€)" +
                "\n2. Sheep (" + Livestock.Type.SHEEP.price + "€)" +
                "\n3. Bison (" + Livestock.Type.BISON.price + "€)"
                );
    }

    public void displayBuyFishMenu() {
        printMenu("FISH MENU" +
                "\n1. Goldfish (" + Fish.Type.GOLDFISH.price + "€)" +
                "\n2. Giant cat fish (" + Fish.Type.GIANT_CATFISH.price + "€)"
                );
    }

    public void displayBuyMarineMammalMenu() {
        printMenu("MARINE MAMMAL MENU" +
                "\n1. Sea otter (" + MarineMammal.Type.SEA_OTTER.price + "€)" +
                "\n2. Seal (" + MarineMammal.Type.SEAL.price + "€)" +
                "\n3. Polar bear (" + MarineMammal.Type.POLAR_BEAR.price + "€)" +
                "\n4. Blue whale ("+ MarineMammal.Type.BLUE_WHALE.price + "€)"
                );
    }


    public void displayBuyFoodMenu() {
        printMenu("BUY FOOD\nOPTIONS\n1. Seeds\n2. Meats\n3. Fish Food");
    }

    public void displayFeedMenu() {
        //TODO feeding menu
        printMenu("FEEDING MENU\nSelect what animal to feed");
    }

    public void displayBreedMenu() {
        System.out.println("BREEDING MENU\nSELECT ANIMAL");

    }

    //TODO
    public void displayAnimalsMenu(ArrayList<Animal> animals) {
        printMenu("OWNED ANIMALS\n");
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

    public void readyPlayerNo(byte number, String name) {
        System.out.println("Ready player no " + number + " (" + name + ")");
    }

    public void unsuccessfulSale() {
        System.out.println("Not enough money. Sale unsuccessful.");
    }

    public void successfulSale() {
        System.out.println("Sufficient funds. Sale successful!");
    }

    public void displayBuySeedMenu() {
        printMenu("SEEDS MENU" +
                "\n1. Bird seeds (" + Seed.Type.BIRD_SEED.price + "€)" +
                "\n2. Corn (" + Seed.Type.CORN.price + "€)"
                );
    }

    public void displayBuyMeatMenu() {
        printMenu("MEATS MENU" +
                "\n1. Mice ("  + Meat.Type.MICE.price + "€)" +
                "\n2. Raw steak (" + Meat.Type.RAW_STEAK.price + "€)"
                );
    }

    public void displayBuyFishFoodMenu() {
        printMenu("FISH FOOD MENU" +
                "\n1. Krill (" + FishFood.Type.KRILL.price + "€)" +
                "\n2. Herring (" + FishFood.Type.HERRING.price + "€)"
                );
    }

    public void displaySelectFoodMenu(LinkedHashMap<Food, Integer> foods) {
        int i = 0;
        for(Food food : foods.keySet()) {
            i++;
            System.out.println(i + ". " + food);
        }
    }

    public void successfulFeed() {
        System.out.println("Feed successful!");
    }

    public void unsuccessfulFeed() {
        System.out.println("Cannot feed that food to the animal. Wrong diet!");
    }

    public void pleaseEnterNameForAnimal() {
        System.out.println("Please enter a name for the animal: ");
    }

    public void sameSpecies() {
        System.out.println("Same species");
    }

    public void notSameSpecies() {
        System.out.println("Unable to breed different species.");
    }

    public void differentGender() {
        System.out.println("Different genders");
    }

    public void sameGender() {
        System.out.println("Same gender. Unable to breed.");
    }

    public void noFoodAvailable() {
        System.out.println("No food available. You have to buy some food first.");
    }

    public void noAnimalsAvailable() {
        System.out.println("No animals available.");
    }

    public void unsuccessfulBreeding() {
        System.out.println("Breeding unsuccessful (50% success rate)");
    }

    public void successfulBreeding() {
        System.out.println("Breeding successful!");
    }

    public void animalsDied(int size, byte number) {
        System.out.println(size + " animals died for player " + number + ".");
    }

    public void playerGameOver(byte number, String name) {
        System.out.println("No animals and no cash left! GAME OVER player " + number + " (" + name + ")");
    }

    public void printHighScores(ArrayList<Player> allPlayers) {

        // We need a tree map to sort the entries
        // Initialized with a descending order
        TreeMap<Long, String> highScore = new TreeMap<>(Collections.reverseOrder());

        // Sort by money (long) as key
        for (Player player: allPlayers) {
            highScore.put(player.getMoney(), player.getName());
        }

        Set<Map.Entry<Long, String>> entries = highScore.entrySet();

        // Printing value before key produces a high score table
        int j = 1;
        for (Map.Entry<Long, String>  entry : entries) {
            System.out.println(j + ". " + entry.getValue() + " " + entry.getKey());
            j++;
        }
    }

    public void displayGenderMenu() {
        System.out.println("Please choose a gender: \n1. Female\n2. Male");
    }

    public void displayHowManyKilosToBuy(String foodType, int maxKilos) {
        System.out.println("How many kilos of " + View.capitalize(foodType).replace("_", " ")
                + " would you like to purchase (max " + maxKilos + ") ?");
    }

    public void displayHowManyKilosToFeed(String foodType) {
        System.out.println("How many kilos of " + View.capitalize(foodType).replace("_", " ")
                + " would you like to feed?");
    }

    public void pleaseEnterFileName() {
        System.out.println("Please enter a file name: ");
    }

    public void dataWrittenToDisk() {
        System.out.println("Your data has been written to disk");
    }

    public void displayPreloadMenu() {
        System.out.println("START" +
                "\n1. NEW GAME" +
                "\n2. LOAD GAME");
    }

    public void reallyQuit() {
        System.out.println("Are you sure you wish to quit the game? (Y/N)");
    }

    public void dataNotWrittenToDisk() {
        System.out.println("Error. Data NOT written to disk.");
    }

    public void displayPleaseEnterOnlyNumbers() {
        System.err.println("Error. Please enter only numbers.");
    }
}
