package org.battleship;

import org.battleship.equipment.*;

import java.util.*;

public class Battlefield {
    private final Cell[][] fieldOfBattle;
    private final List<Ship> fleet;

    protected Battlefield() {
        this.fleet = new ArrayList<>();
        this.fieldOfBattle = new Cell[Constant.FIELD_SIZE][Constant.FIELD_SIZE];
        for (int i = 0; i < fieldOfBattle.length; i++) {
            for (int j = 0; j < fieldOfBattle[i].length; j++) {
                fieldOfBattle[i][j] = new Cell(i, j, Designations.FOG);
            }
        }
    }

    protected boolean checkIsAliveFleet() {
        return fleet.stream().anyMatch(Ship::isShipAlive);
    }

    protected void placeShip(ShipTypes shipClass, String[] coordinates) {
        var x = fromCharToInt(coordinates[0].charAt(0));
        var y = Integer.parseInt(coordinates[0].substring(1)) - 1;
        var x1 = fromCharToInt(coordinates[1].charAt(0));
        var y1 = Integer.parseInt(coordinates[1].substring(1)) - 1;

        if (x >= Constant.FIELD_SIZE || x < 0 ||
                y >= Constant.FIELD_SIZE || y < 0 ||
                x1 >= Constant.FIELD_SIZE || x1 < 0 ||
                y1 >= Constant.FIELD_SIZE || y1 < 0) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }

        var start = new int[]{Math.min(x, x1), Math.min(y, y1)};
        var end = new int[]{Math.max(x, x1), Math.max(y, y1)};

        if (start[0] != end[0] && start[1] != end[1]) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }
        var positionInSpace = (start[0] == end[0]) ? Constant.VERTICAL : Constant.HORIZONTAL;

        if (Math.abs(start[positionInSpace] - end[positionInSpace]) + 1 != shipClass.getCell()) {
            throw new IllegalArgumentException("Error! Wrong length of the ship! Try again:");
        }
        checkNeighbors(start, end);

        List<Cell> locationOfShip = new ArrayList<>();
        if (Constant.VERTICAL == positionInSpace) {
            for (int i = start[1]; i <= end[1]; i++) {
                fieldOfBattle[start[0]][i].setStatus(Designations.CELL);
                locationOfShip.add(new Cell(start[0], i, Designations.CELL));
            }
        } else {
            for (int i = start[0]; i <= end[0]; i++) {
                fieldOfBattle[i][start[1]].setStatus(Designations.CELL);
                locationOfShip.add(new Cell(i, start[1], Designations.CELL));
            }
        }
        fleet.add(new Ship(shipClass, locationOfShip));
    }

    protected Mark markShot(String coordinates) {
        var x = fromCharToInt(coordinates.charAt(0));
        var y = Integer.parseInt(coordinates.substring(1)) - 1;

        if (x >= Constant.FIELD_SIZE || x < 0 ||
                y >= Constant.FIELD_SIZE || y < 0) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        mark(x, y, Designations.HIT);
        if (Designations.CELL.equals(fieldOfBattle[x][y].getStatus()) || Designations.HIT.equals(fieldOfBattle[x][y].getStatus())) {
            fieldOfBattle[x][y].setStatus(Designations.HIT);
            if (!isShipAlive(x, y) && checkIsAliveFleet()) {
                return Mark.GAME_CONTINUOUS_SINK_SHIP;
            }
            return checkIsAliveFleet() ? Mark.GAME_CONTINUOUS : Mark.GAME_OVER;
        }
       fieldOfBattle[x][y].setStatus(Designations.MISS);
        return Mark.GAME_CONTINUOUS_MISS;
    }

    private boolean isShipAlive(int x, int y) {
        return !fleet.stream()
                .filter(z -> z.exist(x, y))
                .filter(Ship::isShipAlive)
                .toList()
                .isEmpty();
    }

    private void mark(int x, int y, Designations designations) {
        for (Ship ship : fleet) {
            for (Cell cell : ship.getCompartments()) {
                if (cell.getX() == x && cell.getY() == y) {
                    cell.setStatus(designations);
                }
            }
        }
    }

    public String displayPrimary() {
        return draw(fieldOfBattle, Designations.CELL);
    }

    public String displayEnemy() {
        return draw(fieldOfBattle, Designations.FOG);
    }

    private String draw(Cell[][] fieldOfBattle, Designations designations) {
        var builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10");
        builder.append(Constant.NEW_LINE);
        for (int i = 0; i < fieldOfBattle.length; i++) {
            for (int j = 0; j < fieldOfBattle[i].length; j++) {
                if (j == 0) {
                    builder.append(fromIntToChar(i)).append(Constant.SPACE);
                }
                switch (fieldOfBattle[i][j].getStatus()) {
                    case FOG -> builder.append(Designations.FOG.getMark());
                    case CELL -> builder.append(designations.getMark());
                    case HIT -> builder.append(Designations.HIT.getMark());
                    case MISS -> builder.append(Designations.MISS.getMark());
                }
                if (j < 9) builder.append(Constant.SPACE);
            }
            if (i < 9) builder.append(Constant.NEW_LINE);
        }
        return builder.toString();
    }

    private void checkNeighbors(int[] start, int[] end) {
        var startPosX = Math.max(0, start[0] - 1);
        var startPosY = Math.max(0, start[1] - 1);
        var endPosX = Math.min(end[0] + 1, Constant.FIELD_SIZE - 1);
        var endPosY = Math.min(end[1] + 1, Constant.FIELD_SIZE - 1);

        for (int a = startPosX; a <= endPosX; a++) {
            for (int b = startPosY; b <= endPosY; b++) {
                if (fieldOfBattle[a][b].getStatus().equals(Designations.CELL)) {
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
                }
            }
        }
    }

    private int fromCharToInt(char a) {
        return a - 65;
    }

    private char fromIntToChar(int i) {
        return (char) (65 + i);
    }
}





