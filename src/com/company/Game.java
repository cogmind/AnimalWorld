package com.company;

import java.util.Scanner;

public class Game {

    private View view = new View();
    private boolean gameOver = false;
    public final static byte MIN_ROUNDS = 5;
    public final static byte MAX_ROUNDS = 30;
    public final static byte MIN_PLAYERS = 1;
    public final static byte MAX_PLAYERS = 4;

    public Game() {

        while (!gameOver) {

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

            view.displayMainMenu();

        }
    }
}
