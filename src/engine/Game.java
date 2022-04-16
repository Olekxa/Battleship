package engine;

import java.util.Scanner;

public class Game {
    Battlefield battlefield;
    Scanner scanner;

    public Game(Battlefield battlefield, Scanner scanner) {
        this.battlefield = battlefield;
        this.scanner = scanner;
    }

    public void play() {
        System.out.println(battlefield);
        getCoordinatesShips();
        System.out.println("The game starts!");
        System.out.println(battlefield);
        getCoordinatesShot();
    }

    private void getCoordinatesShot() {
        System.out.println("Take a shot!");
        while (true) {
            String coordinates = scanner.next().toUpperCase();
            try {
                System.out.println(battlefield.markShot(coordinates));
                System.out.println(battlefield);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void getCoordinatesShips() {
        for (Ship ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.type, ship.sell);
            while (true) {
                String[] coordinates = new String[]{scanner.next().toUpperCase(), scanner.next().toUpperCase()};
                try {
                    battlefield.placeShip(ship, coordinates);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(battlefield);
        }
    }
}
