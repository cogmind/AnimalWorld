package com.company;

import java.util.Scanner;

public class Game {

    private View view = new View();
    private boolean gameOver = false;

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

        byte menuChoice;
        // TODO for each player
        // TODO If all players and rounds have been consumed, set gameOver to true OR break
        while (!gameOver && rounds > 0) {

            view.displayMainMenu();
            menuChoice = scanner.nextByte();

            final byte MENU_START = 1;
            final byte MENU_END = 5;

            if (menuChoice < MENU_START || menuChoice > MENU_END) {
                view.pleaseSelect();
            } else {

                switch (menuChoice) {
                    case 1 -> buyAnimal();
                    case 2 -> buyFood();
                    case 3 -> feed();
                    case 4 -> breed();
                    case 5 -> sell();
                }

            }
            rounds--;
        }
        view.endOfGame();
    }

    public void buyAnimal() {
        view.displayBuyAnimalMenu();

    }

    public void buyFood() {
        view.displayBuyFoodMenu();
    }

    public void feed() {
        view.displayFeedMenu();
    }

    public void breed() {
        view.displayBreedMenu();
    }

    public void sell() {
        view.displaySellAnimalMenu();
    }
}
