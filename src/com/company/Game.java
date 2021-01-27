package com.company;

import java.util.*;

public class Game {

    private final View view = new View();
    public static final String SAVE_PATH = "./sav/";
    private boolean gameOver = false;
    private ArrayList<Player> allPlayers = new ArrayList<>(); //TODO Try if I can use final here, intelliJ recommends
    private ArrayList<Player> highScore = new ArrayList<>();

    public byte players;
    public final static byte MIN_ROUNDS = 5;
    public final static byte MAX_ROUNDS = 30;
    public final static byte MIN_PLAYERS = 1;
    public final static byte MAX_PLAYERS = 4;
    public final static byte BREEDING_SUCCESS_RATE = 50;

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
            case 2: ArrayList<Player> allPlayers = loadGame(scanner);
        }

        while (!(rounds >= MIN_ROUNDS && rounds <= MAX_ROUNDS)) {

            while (rounds == -1 || (rounds < 5 || rounds > 30)) {
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
                view.readyPlayerNo(player.getNumber(), player.getName());
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

                menuChoice = scanNextByte(scanner);

                byte MENU_START = 1;
                byte MENU_END = 7;
                int successfulFeedNumber = -1;

                if (menuChoice < MENU_START || menuChoice > MENU_END) {
                    view.displayMenuOutOfBounds(MENU_END);
                } else {

                    switch (menuChoice) {
                        case 1 -> buyAnimal(player, scanner);
                        case 2 -> buyFood(player, scanner);
                        case 3 -> {successfulFeedNumber = feed(player, scanner);}
                        case 4 -> breed(player, scanner);
                        case 5 -> sell(player, scanner);
                        case 6 -> saveGame(allPlayers, scanner);
                        case 7 -> quitGame(scanner);
                    }
                }
                view.displayEndOfTurn();
                scanner.nextLine();
                player.setHealthReductions(reduceHealthAndManageDeath(player));
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
                rounds--;
            }
            if (allPlayers.size() == 0) {
                gameOver = true; // TODO check if program halts after removing players
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

    private ArrayList<Integer> reduceHealthAndManageDeath(Player player) {
        //Reduce health and record dead animals
        int i = 0;
        ArrayList<Animal> allAnimals = player.getAnimals();

        int healthReduction;
        ArrayList<Integer> healthReductions = new ArrayList<>(allAnimals.size());

        for (Animal animal : allAnimals) {
            healthReduction = animal.fatigueHealth();
            healthReductions.add(healthReduction);
            if (animal.getHealth() <= 0) {
                player.addDeadAnimals(animal);
            }
            i++;
        }
        //Remove dead animals
        for (Animal deadAnimal : player.getDeadAnimals()) {
            player.removeAnimal(deadAnimal);
        }

        return healthReductions;
    }

    public void buyAnimal(Player player, Scanner scanner) {
        view.displayBuyAnimalMenu();
        Store animalStore = new Store(player, players);
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 5;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
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
            // Since this is a queue, the last item is the newest
            player.getAnimal(player.getAnimals().size() - 1).setName(name);

            view.displayGenderMenu();

            byte gender = scanNextByte(scanner);
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
    }

    public void buyBird(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyBirdMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 4;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            Bird.Type choice = Bird.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("bird", choice.toString(), view, scanner);
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }
    }

    public void buyCat(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyCatMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 4;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            Cat.Type choice = Cat.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("cat", choice.toString(), view, scanner);
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }
    }

    public void buyLivestock(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyLivestockMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 3;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            Livestock.Type choice = Livestock.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("livestock", choice.toString(), view, scanner);
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }

    }

    public void buyFish(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyFishMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 2;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            Fish.Type choice = Fish.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("fish", choice.toString(), view, scanner);
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }

    }

    public void buyMarineMammal(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyMarineMammalMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 4;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            MarineMammal.Type choice = MarineMammal.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("marine mammal", choice.toString(), view, scanner);
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }
    }

    public void buyFood(Player player, Scanner scanner) {
        view.displayBuyFoodMenu();
        Store foodStore = new Store(player, players);
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 3;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
            switch (menuChoice) {
                case 1 -> buySeed(player, scanner, foodStore);
                case 2 -> buyMeat(player, scanner, foodStore);
                case 3 -> buyFishFood(player, scanner, foodStore);
            }
        }
    }

    private void buySeed(Player player, Scanner scanner, Store foodStore) {
        view.displayBuySeedMenu();
        byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = 2;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
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
    }

    private void buyMeat(Player player, Scanner scanner, Store foodStore) {
        view.displayBuyMeatMenu();
        byte menuChoice = scanner.nextByte();

        byte MENU_START = 1;
        byte MENU_END = 2;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
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
    }

    private void buyFishFood(Player player, Scanner scanner, Store foodStore) {
        view.displayBuyFishFoodMenu();
        byte menuChoice = scanner.nextByte();

        byte MENU_START = 1;
        byte MENU_END = 2;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        } else {
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
    }

    public int feed(Player player, Scanner scanner) {

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
        byte MENU_START = 1;
        byte MENU_END = (byte) player.getAnimals().size();

        while (menuChoice < MENU_START || menuChoice > MENU_END || menuChoice == -1) {
            menuChoice = scanNextByte(scanner);

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }

        Animal animalToFeed = player.getAnimal(menuChoice - 1);
        LinkedHashMap<Food, Integer> foods = player.getFoods();
        view.displaySelectFoodMenu(foods);

        MENU_END = (byte) player.getFoods().size();

        while (menuChoice < MENU_START || menuChoice > MENU_END || menuChoice == -1) {

            menuChoice = scanNextByte(scanner);

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
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

    public void breed(Player player, Scanner scanner) {


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
        byte MENU_START = 1;
        byte MENU_END = (byte) player.getAnimals().size();

        while (menuChoice1 < MENU_START || menuChoice1 > MENU_END || menuChoice1 == -1) {

            menuChoice1 = scanNextByte(scanner);

            if (menuChoice1 < MENU_START || menuChoice1 > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
            }
        }
        Animal breedingAnimal1 = startingAnimals.get(menuChoice1 - 1);
        startingAnimals.remove(menuChoice1 - 1);

        view.displayAnimalsMenu(startingAnimals);

        while (menuChoice2 < MENU_START || menuChoice2 > MENU_END || menuChoice2 == -1) {

            menuChoice2 = scanNextByte(scanner);
            if (menuChoice1 < MENU_START || menuChoice1 > MENU_END) {
                view.displayMenuOutOfBounds(MENU_END);
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
        // + 1 guarantees at least one offspring
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

    public void sell(Player player, Scanner scanner) {

        if (player.getAnimals().size() == 0){
            view.noAnimalsAvailable();
            return;
        }

        Store sellingStore = new Store(player, players);
        view.displayAnimalsMenu(player.getAnimals());
        Byte menuChoice = scanNextByte(scanner);

        byte MENU_START = 1;
        byte MENU_END = (byte) player.getAnimals().size();

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.displayMenuOutOfBounds(MENU_END);
        }

        Animal animal = player.getAnimal(menuChoice - 1);
        sellingStore.buyAnimal(animal);
        player.setMoney(player.getMoney() + animal.getPrice());
        player.removeAnimal(menuChoice - 1);
    }

    public static int getMaxKilos(Player player, int price) {
        return (int) Math.floor(player.getMoney() / price);
    }
    public void sellAll(Player player) {
        Store sellingStore = new Store(player, players);
        ArrayList<Animal> allAnimals = player.getAnimals();
        for (Animal animal : allAnimals) {
            player.setMoney(player.getMoney() + animal.getPrice());
            //player.removeAnimal(animal); //ConcurrentModification. Skipped. TODO Check
        }
    }
}
