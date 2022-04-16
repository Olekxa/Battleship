package engine;


import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield();
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(battlefield, scanner);
        game.play();

    }
}
