package battleship.org;

import battleship.org.equipment.Constant;
import battleship.org.equipment.Mark;
import battleship.org.equipment.Ship;

import java.util.Scanner;

import static battleship.org.equipment.Constant.*;

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
        getCoordinatesShips(battlefieldPlayer);
        passTheMove(PASS_MOVE);

        System.out.println("Player 2, place your ships to the game field");

        System.out.println(battlefieldOpponent.displayPrimary());
        getCoordinatesShips(battlefieldOpponent);
        passTheMove(PASS_MOVE);
        fight();
    }

    private void fight() {
        System.out.println("The game starts!");
        while (battlefieldPlayer.checkAliveShip() || battlefieldOpponent.checkAliveShip()) {
            System.out.println(battlefieldOpponent.displayEnemy());
            System.out.println(DELIMETR);
            System.out.println(battlefieldPlayer.displayPrimary());
            System.out.println(PLAYER_ONE);
            getCoordinatesShot(battlefieldOpponent);
            passTheMove(PASS_MOVE);

            if (battlefieldOpponent.checkAliveShip()) {
                System.out.println(battlefieldPlayer.displayEnemy());
                System.out.println(DELIMETR);
                System.out.println(battlefieldOpponent.displayPrimary());
                System.out.println(PLAYER_TWO);
                getCoordinatesShot(battlefieldPlayer);
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

    private void getCoordinatesShot(Battlefield fieldOfPlayer) {
        Mark flag;
        while (true) {
            String coordinates = scanner.next().toUpperCase().trim();
            try {
                flag = fieldOfPlayer.markShot(coordinates);
                System.out.println(flag.getMessage());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getCoordinatesShips(Battlefield fieldOfPlayer) {
        for (Ship shipClass : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipClass.getType(), shipClass.getSell());
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
