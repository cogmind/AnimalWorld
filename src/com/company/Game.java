package com.company;

import java.util.*;

public class Game {

    private final View view = new View();
    private boolean gameOver = false;
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private ArrayList<Player> highScore = new ArrayList<>();
    private byte players;
    private static final int MAX_ANIMALS_FOR_SALE = 3;
    private static final byte PROBABILITY_SURVIVAL = 50;

    public static final String SAVE_PATH = "./sav/";
    public final static byte MIN_ROUNDS = 5;
    public final static byte MAX_ROUNDS = 30;
    public final static byte MIN_PLAYERS = 1;
    public final static byte MAX_PLAYERS = 4;
    public final static byte BREEDING_SUCCESS_RATE = 50;
    public final static byte PROBABILITY_SICK = 20;
    public final static byte SICK_FEE = 10; // A percentage of the original price for the animal
    public final static double FEED_PERCENTAGE = 0.10; // Covaries with food factor

    public Game() {

        Scanner scanner = new Scanner(System.in);
        byte rounds = -1;
        players = -1;

        view.displayAnimalWorld();
        view.createLineFeed();
        view.displayPreloadMenu();
        byte menuChoice = scanNextByte(scanner);

        switch (menuChoice) {
            case 1: break;
            case 2: allPlayers = loadGame(scanner);
        }

        while (!(rounds >= MIN_ROUNDS && rounds <= MAX_ROUNDS)) {

            while (rounds < 5 || rounds > 30) {
                view.howManyRounds();
                rounds = scanNextByte(scanner); // Separate function to catch errors
                System.out.println(rounds);
                if (rounds < 5 || rounds > 30) {
                    view.roundsOutOfBounds();
                }
            }
        }

        if (allPlayers.size() == 0) {
            // Define players for New game
            while (!(players >= MIN_PLAYERS && players <= MAX_PLAYERS)) {
                view.howManyPlayers();
                players = scanNextByte(scanner);

                if (players < MIN_PLAYERS || players > MAX_PLAYERS) {
                    view.playersOutOfBounds();
                }
            }

            String name;
            for(byte player = 1; player <= players; player++) {
                view.pleaseEnterNameForPlayer(player);
                name = scanner.nextLine();
                Player newPlayer = new Player(name, player);
                allPlayers.add(newPlayer);
            }
        }

        // Main game loop
        while (!gameOver && rounds > 0) {
            for(Player player: allPlayers) {

                player.updateAllHealth();

                // View statistics
                view.create60Lines();
                view.readyPlayerNo(player.getNumber(), player.getName(), rounds);
                view.displayMoney(player.getMoney());
                view.displayTotalHealth(player.getTotalHealth());
                view.displayAverageHealth(player.getAverageHealth());
                view.displayAnimals(player.getAnimals(), player.getHealthReductions()); // Include health reductions
                view.displayFoods(player.getFoods());

                view.displayMainMenu();

                if (player.getDeadAnimals().size() > 0) {
                    view.animalsDied(player.getDeadAnimals().size(), player.getNumber());
                    player.clearDeadAnimals();
                }

                int successfulFeedNumber = -1;
                byte MENU_START = 1;
                byte MENU_END = 7;
                menuChoice = -1;

                while (menuChoice < MENU_START || menuChoice > MENU_END) {

                    menuChoice = scanNextByte(scanner);
                    if (menuChoice < MENU_START || menuChoice > MENU_END) {
                        view.displayMenuOutOfBounds(MENU_END);
                    }
                }

                switch (menuChoice) {
                    case 1 -> buyAnimal(player, scanner);
                    case 2 -> buyFood(player, scanner);
                    case 3 -> successfulFeedNumber = feed(player, scanner);
                    case 4 -> breed(player, scanner);
                    case 5 -> sell(player, scanner);
                    case 6 -> saveGame(allPlayers, scanner);
                    case 7 -> quitGame(scanner);
                }

                player.setHealthReductions(reduceHealthAndManageDeath(player, successfulFeedNumber));
                manageSickAnimals(player, scanner);

                view.displayEndOfTurn();
                scanner.nextLine();

                view.create60Lines();

                // If player loses, remove player
                // Add player to high score table
                if (player.getMoney() <= 0 && player.getAnimals().size() == 0) {
                    view.playerGameOver(player.getNumber(), player.getName());
                    if (allPlayers.size() > 1) {
                        highScore.add(player);
                        allPlayers.remove(player);
                    } else {
                        gameOver = true;
                    }
                }
            }
            rounds--;
            if (allPlayers.size() == 0) {
                gameOver = true;
            }
        }

        // End of game logic
        for (Player player: allPlayers) {
            sellAll(player);
            highScore.add(player); // Add remaining players to high score table
        }

        view.printHighScores(highScore);
        view.endOfGame();
    }

    private void manageSickAnimals(Player player, Scanner scanner) {

        final byte SICK_ANIMALS = 0;
        final byte FEES = 1;

        ArrayList[] sickInfo = player.getSickAnimals();

        if (sickInfo[SICK_ANIMALS].size() > 0) {
            int totalFee = view.displaySickAnimals(sickInfo[SICK_ANIMALS], sickInfo[FEES]);
            String veterinaryChoice = scanner.nextLine();
            if (veterinaryChoice.matches("y|Y|Yes|YES")) {
                player.setMoney(player.getMoney() - totalFee);
                view.displayVeterinaryReceipt(sickInfo, player);
                player.letSickAnimalsDie(sickInfo[SICK_ANIMALS], PROBABILITY_SURVIVAL, view);
            } else {
                player.letSickAnimalsDie(sickInfo[SICK_ANIMALS], 0, view);
            }

        }
    }

    private void quitGame(Scanner scanner) {
        view.reallyQuit();
        String choice = scanner.nextLine();
        if (choice.matches("y|Y|Yes|YES")) {
            System.exit(0);
        }
    }

    private void saveGame(ArrayList<Player> data, Scanner scanner) {

        view.pleaseEnterFileName();
        String filePath = scanner.nextLine();

        // Assuming these file separators works on all OS:s
        boolean successfulWrite = Serializer.serialize(SAVE_PATH + filePath, data);
        if (successfulWrite) {
            view.dataWrittenToDisk();
        } else {
            view.dataNotWrittenToDisk();
        }
    }

    private ArrayList<Player> loadGame(Scanner scanner) {

        view.displayFiles();
        view.pleaseEnterFileName();
        String filePath = scanner.nextLine();

        allPlayers = (ArrayList) Serializer.deserialize(SAVE_PATH + filePath);

        return allPlayers;
    }

    private byte scanNextByte(Scanner scanner) {

        byte menuChoice = -1;

        try {
            menuChoice = scanner.nextByte();
        } catch (InputMismatchException inputMismatch) {
            System.err.println("ERROR: Input mismatch. Please enter a number.");
        }

        scanner.nextLine();
        return menuChoice;
    }

    private ArrayList<Integer> reduceHealthAndManageDeath(Player player, int successfulFeedNumber) {
        //Reduce health and record dead animals
        ArrayList<Animal> allAnimals = player.getAnimals();

        int healthReduction;
        ArrayList<Integer> healthReductions = new ArrayList<>(allAnimals.size());

        int i = 0;
        for (Animal animal : allAnimals) {
            if (i != successfulFeedNumber) { // Except the most recently fed animal
                healthReduction = -1 * animal.fatigueHealth();
                healthReductions.add(healthReduction);
                if (animal.getHealth() <= 0) {
                    player.addDeadAnimals(animal);
                }
            } else {
                healthReductions.add(0); // Most recently fed animal
            }
            i++;
        }
        //Remove dead animals
        for (Animal deadAnimal : player.getDeadAnimals()) {
            player.removeAnimal(deadAnimal);
        }

        return healthReductions;
    }

    private void buyAnimal(Player player, Scanner scanner) {

        view.displayBuyAnimalMenu();
        Store animalStore = new Store(player, players);

        byte menuChoice = scanNextByte(scanner);

        final byte MENU_START = 1;
        byte menu_end = 5;

        while (menuChoice < MENU_START || menuChoice > menu_end) {

            menuChoice = scanNextByte(scanner);
            if (menuChoice < MENU_START || menuChoice > menu_end) {
                view.displayMenuOutOfBounds(menu_end);
            }
        }
        switch (menuChoice) {
            case 1 -> buyBird(player, scanner, animalStore);
            case 2 -> buyCat(player, scanner, animalStore);
            case 3 -> buyLivestock(player, scanner, animalStore);
            case 4 -> buyFish(player, scanner, animalStore);
            case 5 -> buyMarineMammal(player, scanner, animalStore);
        }

        view.pleaseEnterNameForAnimal();
        String name = scanner.next();
        scanner.nextLine();
        // Since an arraylist is a queue, the last item is the newest added
        player.getAnimal(player.getAnimals().size() - 1).setName(name);

        view.displayGenderMenu();

        byte gender = -1;
        menu_end = 2;

        while (gender == -1 || gender < MENU_START || gender > menu_end) {

            gender = scanNextByte(scanner);

            if (gender > 3) {
                view.displayOnlyTwoGendersAvailable();
            }

        }

        boolean isFemale;

        if (gender == 1) {
            isFemale = true;
        } else if (gender == 2) {
            isFemale = false;
        } else {
            Random random = new Random();
            isFemale = random.nextBoolean();
        }
        // Get last animal added and set gender
        player.getAnimal(player.getAnimals().size() - 1).setGender(isFemale);
    }

    private void buyBird(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyBirdMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 4;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }
        Bird.Type choice = Bird.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        boolean successfulSale = animalStore.sellAnimal("bird", choice.toString());
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyCat(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyCatMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 4;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Cat.Type choice = Cat.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        boolean successfulSale = animalStore.sellAnimal("cat", choice.toString());
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyLivestock(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyLivestockMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 3;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Livestock.Type choice = Livestock.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        boolean successfulSale = animalStore.sellAnimal("livestock", choice.toString());
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyFish(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyFishMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 2;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Fish.Type choice = Fish.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        boolean successfulSale = animalStore.sellAnimal("fish", choice.toString());
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyMarineMammal(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyMarineMammalMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 4;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        MarineMammal.Type choice = MarineMammal.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        boolean successfulSale = animalStore.sellAnimal("marine mammal", choice.toString());
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyFood(Player player, Scanner scanner) {

        view.displayBuyFoodMenu();
        Store foodStore = new Store(player, players);
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 3;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        switch (menuChoice) {
            case 1 -> buySeed(player, scanner, foodStore);
            case 2 -> buyMeat(player, scanner, foodStore);
            case 3 -> buyFishFood(player, scanner, foodStore);
        }

    }

    private void buySeed(Player player, Scanner scanner, Store foodStore) {

        view.displayBuySeedMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 2;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Seed.Type choice = Seed.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        view.displayHowManyKilosToBuy(choice.name(), getMaxKilos(player, choice.price));
        int kilos = -1;
        try {
            kilos = scanner.nextInt();
        } catch (InputMismatchException inputMismatchException) {
            view.displayPleaseEnterOnlyNumbers();
        }
        scanner.nextLine();
        boolean successfulSale = foodStore.sellFood("seed", choice.toString(), kilos);
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
    }

    private void buyMeat(Player player, Scanner scanner, Store foodStore) {

        view.displayBuyMeatMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 2;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Meat.Type choice = Meat.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        view.displayHowManyKilosToBuy(choice.name(), getMaxKilos(player, choice.price));
        int kilos = scanner.nextInt();
        scanner.nextLine();
        boolean successfulSale = foodStore.sellFood("meat", choice.toString(), kilos);
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();

        }
    }

    private void buyFishFood(Player player, Scanner scanner, Store foodStore) {

        view.displayBuyFishFoodMenu();
        byte menuChoice = -1;

        final byte MENU_START = 1;
        final byte MENU_END = 2;

        while (menuChoice < MENU_START || menuChoice > MENU_END) {

            menuChoice = scanner.nextByte();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        FishFood.Type choice = FishFood.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
        // If sale is successful also updates player's (as customer in store) attributes
        view.displayHowManyKilosToBuy(choice.name(), getMaxKilos(player, choice.price));
        int kilos = scanner.nextInt();
        scanner.nextLine();
        boolean successfulSale = foodStore.sellFood("fish food", choice.toString(), kilos);
        if (!successfulSale) {
            view.unsuccessfulSale();
        } else {
            view.successfulSale();
        }
}

    private int feed(Player player, Scanner scanner) {

        if (player.getFoods().size() == 0) {
            view.noFoodAvailable();
            return -1;
        }
        if (player.getAnimals().size() == 0) {
            view.noAnimalsAvailable();
            return -1;
        }

        view.displayFeedMenu();
        view.displayAnimalsMenu(player.getAnimals());

        byte menuChoice = -1;
        final byte MENU_START = 1;
        byte menu_end = (byte) player.getAnimals().size();

        while (menuChoice < MENU_START || menuChoice > menu_end) {
            menuChoice = scanNextByte(scanner);

            if (menuChoice < MENU_START || menuChoice > menu_end) {
                view.displayMenuOutOfBounds(menu_end);
            }
        }

        Animal animalToFeed = player.getAnimal(menuChoice - 1);

        LinkedHashMap<Food, Integer> foods = player.getFoods();
        view.displaySelectFoodMenu(foods);

        menu_end = (byte) player.getFoods().size();

        while (menuChoice < MENU_START || menuChoice > menu_end) {

            menuChoice = scanNextByte(scanner);

            if (menuChoice < MENU_START || menuChoice > menu_end) {
                view.displayMenuOutOfBounds(menu_end);
            }
        }

        Food foodForAnimal = player.getFood(menuChoice - 1);

        view.displayHowManyKilosToFeed(foodForAnimal.toString());
        int kilos = scanner.nextInt();
        scanner.nextLine();

        boolean successfulFeed = player.feed(animalToFeed, foodForAnimal, kilos);
        if (successfulFeed) {
            view.successfulFeed();
            return menuChoice - 1;
        } else {
            view.unsuccessfulFeed();
            return -1;
        }
    }

    private void breed(Player player, Scanner scanner) {

        if (player.getAnimals().size() < 2) {
            view.youNeedAtLeastTwoAnimals();
            return;
        }

        view.displayBreedMenu();

        //Create a copy of the animal array list
        ArrayList<Animal> startingAnimals = new ArrayList<Animal>(player.getAnimals());

        view.displayAnimalsMenu(startingAnimals);

        byte menuChoice1 = -1;
        byte menuChoice2 = -1;
        final byte MENU_START = 1;
        byte menu_end = (byte) player.getAnimals().size();

        while (menuChoice1 < MENU_START || menuChoice1 > menu_end) {

            menuChoice1 = scanNextByte(scanner);

            if (menuChoice1 < MENU_START || menuChoice1 > menu_end) {
                view.displayMenuOutOfBounds(menu_end);
            }
        }
        Animal breedingAnimal1 = startingAnimals.get(menuChoice1 - 1);
        startingAnimals.remove(menuChoice1 - 1);
        menu_end = (byte) player.getAnimals().size();

        view.displayAnimalsMenu(startingAnimals);

        while (menuChoice2 < MENU_START || menuChoice2 > menu_end) {

            menuChoice2 = scanNextByte(scanner);
            if (menuChoice1 < MENU_START || menuChoice1 > menu_end) {
                view.displayMenuOutOfBounds(menu_end);
            }
        }

        Animal breedingAnimal2 = startingAnimals.get(menuChoice2 - 1);

        if (breedingAnimal1.getType().equals(breedingAnimal2.getType())) {
            view.sameSpecies();
            if (breedingAnimal1.getGender() != breedingAnimal2.getGender()) {
                view.differentGender();
                breedAnimals(breedingAnimal1, breedingAnimal2, player, scanner, view);
            } else {
                view.sameGender();
            }
        } else {
            view.notSameSpecies();
        }
    }

    private void breedAnimals(Animal breedingAnimal1, Animal breedingAnimal2, Player player, Scanner scanner, View view) {

        String type = breedingAnimal1.getType();
        int price = breedingAnimal1.getPrice();
        String[] diet = breedingAnimal1.getDiet();
        double foodFactor = breedingAnimal1.getFoodFactor();
        byte max_offspring = breedingAnimal1.getOffspring();

        String classname = breedingAnimal1.getClass().getSimpleName();

        // Randomize with the offspring attribute as the max number of offsprings
        // + 1 guarantees at least one offspring (if successful)
        Random random = new Random();
        byte offsprings = breedingAnimal1.getOffspring();
        offsprings =  (byte) (Math.round(random.nextDouble() * offsprings+ 1));

        String name;
        if (random.nextBoolean()) {
            view.successfulBreeding(offsprings);
            for (byte i = 0; i < offsprings; i++) {
                view.pleaseEnterNameForAnimal();
                name = scanner.next();
                boolean isFemale = random.nextBoolean();

                switch (classname) {
                    case "Bird" -> {
                        Bird animal = new Bird(type, name, isFemale, (byte) 100, price, diet, foodFactor, max_offspring);
                        player.addAnimal(animal);
                    }
                    case "Cat" -> {
                        Cat animal = new Cat(type, name, isFemale, (byte) 100, price, diet, foodFactor, max_offspring);
                        player.addAnimal(animal);
                    }
                    case "Livestock" -> {
                        Livestock animal = new Livestock(type, name, isFemale, (byte) 100, price, diet, foodFactor, max_offspring);
                        player.addAnimal(animal);
                    }
                    case "Fish" -> {
                        Fish animal = new Fish(type, name, isFemale, (byte) 100, price, diet, foodFactor, max_offspring);
                        player.addAnimal(animal);
                    }
                    case "MarineMammal" -> {
                        MarineMammal animal = new MarineMammal(type, name, isFemale, (byte) 100, price, diet, foodFactor, max_offspring);
                        player.addAnimal(animal);
                    }
                }
            }
        } else {
            view.unsuccessfulBreeding();
        }
    }

    private void sell(Player player, Scanner scanner) {

        int animalsAvailable = player.getAnimals().size();

        if (animalsAvailable == 0){
            view.noAnimalsAvailable();
            return;
        }

        Store sellingStore = new Store(player, players);

        if (animalsAvailable > MAX_ANIMALS_FOR_SALE) {
            animalsAvailable = MAX_ANIMALS_FOR_SALE;
        }

        while (animalsAvailable > 0) {
            view.displaySellAnimals(animalsAvailable);
            view.displayAnimalsMenu(player.getAnimals());
            byte menuChoice = scanNextByte(scanner);

            final byte MENU_START = 1;
            final byte MENU_END = (byte) player.getAnimals().size();

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }

            Animal animal = player.getAnimal(menuChoice - 1);
            sellingStore.buyAnimal(animal);
            player.setMoney(player.getMoney() + animal.getPrice());
            player.removeAnimal(menuChoice - 1);

            animalsAvailable -= 1;
            if (animalsAvailable > 0) {
                view.displaySellMoreAnimals();
                String choice = scanner.nextLine();
                if (!choice.matches("y|Y|Yes|YES")) {
                    return;
                }
            }
        }
    }

    private static int getMaxKilos(Player player, int price) {
        return (int) Math.floor(player.getMoney() / price);
    }

    private void sellAll(Player player) {

        Store sellingStore = new Store(player, players);
        ArrayList<Animal> allAnimals = player.getAnimals();
        for (Animal animal : allAnimals) {
            player.setMoney(player.getMoney() + animal.getPrice());
        }
    }
}
