package battleship.org;

import battleship.org.equipment.Mark;
import battleship.org.equipment.ShipTypes;

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
        while (battlefieldPlayer.checkIsAliveFleet() && battlefieldOpponent.checkIsAliveFleet()) {
            System.out.println(battlefieldOpponent.displayEnemy());
            System.out.println(DELIMETR);
            System.out.println(battlefieldPlayer.displayPrimary());
            System.out.println(PLAYER_ONE);
            if (Mark.GAME_OVER.equals(getCoordinatesShot(battlefieldOpponent))) {
                System.out.println(Mark.GAME_OVER.getMessage());
                return;
            }
            passTheMove(PASS_MOVE);

            if (battlefieldOpponent.checkIsAliveFleet()) {
                System.out.println(battlefieldPlayer.displayEnemy());
                System.out.println(DELIMETR);
                System.out.println(battlefieldOpponent.displayPrimary());
                System.out.println(PLAYER_TWO);
                getCoordinatesShot(battlefieldPlayer);
                if (Mark.GAME_OVER.equals(getCoordinatesShot(battlefieldPlayer))) {
                    System.out.println(Mark.GAME_OVER.getMessage());
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

    private Mark getCoordinatesShot(Battlefield fieldOfPlayer) {
        while (true) {
            String coordinates = scanner.next().toUpperCase().trim();
            try {
                return fieldOfPlayer.markShot(coordinates);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getCoordinatesShips(Battlefield fieldOfPlayer) {
        for (ShipTypes shipClass : ShipTypes.values()) {
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
