package org.battleship;

import org.battleship.equipment.Mark;
import org.battleship.equipment.ShipTypes;

import java.util.Scanner;

import static org.battleship.equipment.Constant.*;

public class Game {
    private final Battlefield battlefieldPlayer;
    private final Battlefield battlefieldOpponent;
    private final Scanner scanner;

    public Game(Battlefield battlefieldPlayer, Battlefield battlefieldOpponent, Scanner scanner) {
        this.battlefieldPlayer = battlefieldPlayer;
        this.battlefieldOpponent = battlefieldOpponent;
        this.scanner = scanner;
    }

    public void play() {
        System.out.println("Player 1, place your ships to the game field");

        System.out.println(battlefieldPlayer.displayPrimary());
        readCoordinatesShips(battlefieldPlayer);
        passTheMove(PASS_MOVE);

        System.out.println("Player 2, place your ships to the game field");

        System.out.println(battlefieldOpponent.displayPrimary());
        readCoordinatesShips(battlefieldOpponent);
        passTheMove(PASS_MOVE);
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
            passTheMove(PASS_MOVE);

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
                passTheMove(PASS_MOVE);
            }
        }
    }

    private void passTheMove(String msg) {
        System.out.println(msg);
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
            String coordinates = scanner.next().toUpperCase().trim();
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
                String[] coordinates = new String[]{scanner.next().toUpperCase(), scanner.next().toUpperCase()};
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
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
