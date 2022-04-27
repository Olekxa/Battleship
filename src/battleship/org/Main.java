package battleship.org;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Battlefield player = new Battlefield();
        Battlefield opponent = new Battlefield();
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(player, opponent, scanner);
        game.play();
    }
}
