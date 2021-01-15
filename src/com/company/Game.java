package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private final View view = new View();
    private boolean gameOver = false;
    private ArrayList<Player> allPlayers = new ArrayList<>(); //TODO Try if I can use final here, intelliJ recommends

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

        while (!(rounds >= MIN_ROUNDS && rounds <= MAX_ROUNDS)) {
            view.howManyRounds();
            rounds = scanner.nextByte();

            if (rounds < 5 || rounds > 30) {
                view.roundsOutOfBounds();
            }
        }

        while (!(players >= MIN_PLAYERS && players <= MAX_PLAYERS)) {
            view.howManyPlayers();
            players = scanner.nextByte();

            if (players < MIN_PLAYERS || players > MAX_PLAYERS) {
                view.playersOutOfBounds();
            }
        }

        //Define players
        String name;
        for(byte player = 1; player <= players; player++) {
            name = scanner.nextLine();
            Player newPlayer = new Player(name, player);
            allPlayers.add(newPlayer);
        }
        byte menuChoice;
        // TODO for each player
        // TODO If all players and rounds have been consumed, set gameOver to true OR break
        while (!gameOver && rounds > 0) {
            for(Player player: allPlayers) {
                //TODO Set gameOver condition ???

                // View statistics
                view.readyPlayerNo(player.getNumber());
                view.displayMoney(player.getMoney());
                view.displayTotalHealth(player.getTotalHealth());
                view.displayAverageHealth(player.getAverageHealth());

                view.displayMainMenu();
                menuChoice = scanner.nextByte();

                byte MENU_START = 1;
                byte MENU_END = 5;

                if (menuChoice < MENU_START || menuChoice > MENU_END) {
                    view.menuOutOfBounds(MENU_END);
                } else {

                    switch (menuChoice) {
                        case 1 -> buyAnimal(player, scanner);
                        case 2 -> buyFood(player, scanner);
                        case 3 -> feed(player, scanner);
                        case 4 -> breed(player, scanner);
                        case 5 -> sell(player, scanner);
                    }
                }
                rounds--;
            }
            if (allPlayers.size() == 0) {
                gameOver = true; // TODO check if program halts after removing players
            }
        }

        view.endOfGame();
    }

    public void buyAnimal(Player player, Scanner scanner) {
        view.displayBuyAnimalMenu();
        Store animalStore = new Store(player, players);
        byte menuChoice = scanner.nextByte();

        byte MENU_START = 1;
        byte MENU_END = 5;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.menuOutOfBounds(MENU_END);
        } else {
            switch (menuChoice) {
                case 1 -> buyBird(player, scanner, animalStore);
                case 2 -> buyCat(player, scanner, animalStore);
                case 3 -> buyLivestock(player, scanner, animalStore);
                case 4 -> buyFish(player, scanner, animalStore);
                case 5 -> buyMarineMammal(player, scanner, animalStore);
            }
        }
    }

    public void buyBird(Player player, Scanner scanner, Store animalStore) {

        view.displayBuyBirdMenu();
        byte menuChoice = scanner.nextByte();

        byte MENU_START = 1;
        byte MENU_END = 4;

        if (menuChoice < MENU_START || menuChoice > MENU_END) {
            view.menuOutOfBounds(MENU_END);
        } else {
            Bird.Type choice = Bird.Type.values()[menuChoice - 1]; // DEBUGABLE Requires that the order is the same in menu
            // If sale is successful also updates player's (as customer in store) attributes
            boolean successfulSale = animalStore.sellAnimal("bird", choice.toString());
            if (!successfulSale) {
                view.unsuccessfulSale();
            } else {
                view.successfulSale();
            }
        }
    }

    public void buyCat(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyCatMenu();
    }

    public void buyLivestock(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyLivestockMenu();
    }

    public void buyFish(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyFishMenu();
    }

    public void buyMarineMammal(Player player, Scanner scanner, Store animalStore) {
        view.displayBuyMarineMammalMenu();
    }

    public void buyFood(Player player, Scanner scanner) {
        view.displayBuyFoodMenu();
        Store animalStore = new Store(player, players);
    }

    public void feed(Player player, Scanner scanner) {
        view.displayFeedMenu();
    }

    public void breed(Player player, Scanner scanner) {
        view.displayBreedMenu();
    }

    public void sell(Player player, Scanner scanner) {
        view.displaySellAnimalMenu();
    }
}
