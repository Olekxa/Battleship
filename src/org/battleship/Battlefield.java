package org.battleship;

import org.battleship.equipment.*;

import java.util.*;
import java.util.function.Predicate;

public class Battlefield {
    private final Cell[][] fieldOfBattle;
    List<Ship> fleet;

    protected Battlefield() {
        this.fieldOfBattle = new Cell[Constant.FIELD_SIZE][Constant.FIELD_SIZE];
        for (int i = 0; i < fieldOfBattle.length; i++) {
            for (int j = 0; j < fieldOfBattle[i].length; j++) {
                fieldOfBattle[i][j] = new Cell(i, j);
            }
            fleet = new ArrayList<>();
        }
    }

    protected boolean checkIsAliveFleet() {
        return fleet.stream().anyMatch(Ship::statusOfShip);
    }

    protected void placeShip(ShipTypes shipClass, String[] coordinates) {
        if (Constant.ACCEPTED_LENGTH < coordinates.length
                || "".equals(coordinates[0])
                || "".equals(coordinates[1])) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
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
        Ship ship = new Ship(shipClass, locationOfShip);
        fleet.add(ship);
        if (Constant.VERTICAL == positionInSpace) {
            for (int i = start[1]; i <= end[1]; i++) {
                fieldOfBattle[start[0]][i].setShip(ship);
                Cell point = fieldOfBattle[start[0]][i];
                locationOfShip.add(point);
            }
        } else {
            for (int i = start[0]; i <= end[0]; i++) {
                fieldOfBattle[i][start[1]].setShip(ship);
                Cell point = fieldOfBattle[i][start[1]];
                locationOfShip.add(point);
            }
        }
    }

    protected Mark markShot(String coordinates) {
        if (coordinates.length() < 2 || coordinates.length() > 3) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        var x = fromCharToInt(coordinates.charAt(0));
        var y = Integer.parseInt(coordinates.substring(1)) - 1;

        if (x >= Constant.FIELD_SIZE || x < 0 ||
                y >= Constant.FIELD_SIZE || y < 0) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        mark(x, y);
        if (fieldOfBattle[x][y].getShip() != null && fieldOfBattle[x][y].isPointedFire()) {
            fieldOfBattle[x][y].setPointedFire(true);
            if (isShipSank(x, y) && checkIsAliveFleet()) {
                return Mark.GAME_CONTINUOUS_SINK_SHIP;
            }
            return checkIsAliveFleet() ? Mark.GAME_CONTINUOUS : Mark.GAME_OVER;
        }
        fieldOfBattle[x][y].setPointedFire(true);
        return Mark.GAME_CONTINUOUS_MISS;
    }

    private boolean isShipSank(int x, int y) {
        return !fieldOfBattle[x][y].isShipAlive();
    }

    private void mark(int x, int y) {
        for (Cell[] cells : fieldOfBattle) {
            for (Cell cell : cells) {
                if (cell.getX() == x && cell.getY() == y) {
                    cell.setPointedFire(true);
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
                Designations mark;
                if (fieldOfBattle[i][j].isPointedFire()) {
                    if (fieldOfBattle[i][j].getShip() != null) {
                        mark = Designations.HIT;
                    } else {
                        mark = Designations.MISS;
                    }
                } else {
                    if (fieldOfBattle[i][j].getShip() != null) {
                        mark = designations;
                    } else {
                        mark = Designations.FOG;
                    }
                }
                builder.append(mark.getMark());
                if (j < Constant.FIELD_SIZE - 1) builder.append(Constant.SPACE);
            }
            if (i < Constant.FIELD_SIZE - 1) builder.append(Constant.NEW_LINE);
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
                if (fieldOfBattle[a][b].getShip() != null) {
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
                }
            }
        }
    }

    private int fromCharToInt(char a) {
        return (a - 'A');
    }

    private char fromIntToChar(int i) {
        return (char) ('A' + i);
    }
}





