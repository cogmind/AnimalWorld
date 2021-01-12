package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private View view = new View();
    private boolean gameOver = false;
    private ArrayList<Player> allPlayers = new ArrayList<>();

    public final static byte MIN_ROUNDS = 5;
    public final static byte MAX_ROUNDS = 30;
    public final static byte MIN_PLAYERS = 1;
    public final static byte MAX_PLAYERS = 4;
    public final static byte BREEDING_SUCCESS_RATE = 50;

    public Game() {

        Scanner scanner = new Scanner(System.in);
        byte rounds = -1;
        byte players = -1;

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

        for(Player player: allPlayers) {

            byte menuChoice;
            // TODO for each player
            // TODO If all players and rounds have been consumed, set gameOver to true OR break
            while (!gameOver && rounds > 0) {

                //TODO Set gameOver condition ???

                // View statistics
                view.readyPlayerNo(player.getNumber());
                view.displayMoney(player.getMoney());
                view.displayTotalHealth(player.getTotalHealth());
                view.displayAverageHealth(player.getAverageHealth());

                view.displayMainMenu();
                menuChoice = scanner.nextByte();

                final byte MENU_START = 1;
                final byte MENU_END = 5;

                if (menuChoice < MENU_START || menuChoice > MENU_END) {
                    view.menuOutOfBounds();
                } else {

                    switch (menuChoice) {
                        case 1 -> buyAnimal(player);
                        case 2 -> buyFood(player);
                        case 3 -> feed(player);
                        case 4 -> breed(player);
                        case 5 -> sell(player);
                    }

                }
                rounds--;
            }
        }

        view.endOfGame();
    }

    public void buyAnimal(Player player) {
        view.displayBuyAnimalMenu();

    }

    public void buyFood(Player player) {
        view.displayBuyFoodMenu();
    }

    public void feed(Player player) {
        view.displayFeedMenu();
    }

    public void breed(Player player) {
        view.displayBreedMenu();
    }

    public void sell(Player player) {
        view.displaySellAnimalMenu();
    }
}
