package org.battleship;

import org.battleship.equipment.Mark;
import org.battleship.equipment.ShipTypes;

import java.util.Scanner;

import static org.battleship.equipment.Constant.*;

public class Game {
    private final Battlefield battlefieldPlayer;
    private final Battlefield battlefieldOpponent;
    private final Scanner scanner;

    public Game(Scanner scanner) {
        this.battlefieldPlayer = new Battlefield();
        this.battlefieldOpponent = new Battlefield();
        this.scanner = scanner;
    }

    public void play() {
        System.out.println("Player 1, place your ships to the game field");

        System.out.println(battlefieldPlayer.displayPrimary());
        readCoordinatesShips(battlefieldPlayer);
        passTheMove();

        System.out.println("Player 2, place your ships to the game field");

        System.out.println(battlefieldOpponent.displayPrimary());
        readCoordinatesShips(battlefieldOpponent);
        passTheMove();
        fight();
    }

    private void fight() {
        System.out.println("The game starts!");
        while (battlefieldPlayer.checkIsAliveFleet() && battlefieldOpponent.checkIsAliveFleet()) {
            System.out.println(battlefieldOpponent.displayEnemy());
            System.out.println(DELIMITER);
            System.out.println(battlefieldPlayer.displayPrimary());
            System.out.println(PLAYER_ONE);
            Mark mark = readCoordinatesShot(battlefieldOpponent);
            System.out.println(mark.getMessage());
            if (Mark.GAME_OVER.equals(mark)) {
                return;
            }
            passTheMove();

            if (battlefieldOpponent.checkIsAliveFleet()) {
                System.out.println(battlefieldPlayer.displayEnemy());
                System.out.println(DELIMITER);
                System.out.println(battlefieldOpponent.displayPrimary());
                System.out.println(PLAYER_TWO);
                mark = readCoordinatesShot(battlefieldPlayer);
                System.out.println(mark.getMessage());
                if (Mark.GAME_OVER.equals(mark)) {
                    return;
                }
                passTheMove();
            }
        }
    }

    private void passTheMove() {
        System.out.println(PASS_MOVE);
        scanner.nextLine();
        String enter = scanner.nextLine();
        System.out.println(enter);
        while (true) {
            if (enter.equals("")) {
                clearScreen();
                break;
            }
        }
    }

    private Mark readCoordinatesShot(Battlefield fieldOfPlayer) {
        while (true) {
            String coordinates = scanner.nextLine().toUpperCase().trim();
            try {
                return fieldOfPlayer.markShot(coordinates);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void readCoordinatesShips(Battlefield fieldOfPlayer) {
        for (ShipTypes shipClass : ShipTypes.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipClass.getType(), shipClass.getCell());
            while (true) {
                String[] coordinates = scanner.nextLine().trim().toUpperCase().split(" ");        //Here we change next()
                try {
                    fieldOfPlayer.placeShip(shipClass, coordinates);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(fieldOfPlayer.displayPrimary());
        }
    }

    private void clearScreen() {
        for (int i = 0; i < CLEAR_SCREEN; ++i) System.out.println();
    }
}
