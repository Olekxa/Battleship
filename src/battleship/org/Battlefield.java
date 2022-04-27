package battleship.org;

import battleship.org.equipment.*;

import java.util.*;


public class Battlefield {
    private final Designations[][] battleField = new Designations[Constant.FIELD_SIZE][Constant.FIELD_SIZE];
    private final List<Ship> fleet = new ArrayList<>();

    protected Battlefield() {
        for (Designations[] designations : battleField) {
            Arrays.fill(designations, Designations.FOG);
        }
    }

    protected boolean checkIsAliveFleet() {
        return fleet.stream().anyMatch(Ship::isShipAlive);
    }

    protected void placeShip(ShipTypes shipClass, String[] coordinates) {
        var x = coordinates[0].charAt(0) - 65;
        var y = Integer.parseInt(coordinates[0].substring(1)) - 1;
        var x1 = coordinates[1].charAt(0) - 65;
        var y1 = Integer.parseInt(coordinates[1].substring(1)) - 1;

        if (x >= Constant.FIELD_SIZE || x < 0 ||
                y >= Constant.FIELD_SIZE || y < 0 ||
                x1 >= Constant.FIELD_SIZE || x1 < 0 ||
                y1 >= Constant.FIELD_SIZE || y1 < 0) {
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
        }

        var start = new int[]{Math.min(x, x1), Math.min(y, y1)};
        var end = new int[]{Math.max(x, x1), Math.max(y, y1)};

        if (start[0] != end[0] && start[1] != end[1])
            throw new IllegalArgumentException("Error! Wrong ship location! Try again:");

        var positionInSpace = (start[0] == end[0]) ? 1 : 0;

        if (Math.abs(start[positionInSpace] - end[positionInSpace]) + 1 != shipClass.getSell())
            throw new IllegalArgumentException("Error! Wrong length of the ship! Try again:");

        checkNeighbors(start, end);

        List<Coordinates> locationOfShip = new ArrayList<>();
        if (positionInSpace == 1) {
            for (int i = start[1]; i <= end[1]; i++) {
                battleField[start[0]][i] = Designations.CELL;
                locationOfShip.add(new Coordinates(start[0], i, Designations.CELL));
            }
        } else {
            for (int i = start[0]; i <= end[0]; i++) {
                battleField[i][start[1]] = Designations.CELL;
                locationOfShip.add(new Coordinates(i, start[1], Designations.CELL));
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
        if (Designations.CELL.equals(battleField[x][y]) || Designations.HIT.equals(battleField[x][y])) {
            battleField[x][y] = Designations.HIT;
            if (!isShipAlive(x, y) && checkIsAliveFleet()) {
                return Mark.GAME_CONTINUOUS_SINK_SHIP;
            }
            return checkIsAliveFleet() ? Mark.GAME_CONTINUOUS : Mark.GAME_OVER;
        }
        battleField[x][y] = Designations.MISS;
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
            for (Coordinates coordinates : ship.getCompartments()) {
                if (coordinates.getX() == x && coordinates.getY() == y) {
                    coordinates.setStatus(designations);
                }
            }
        }
    }

    public String displayPrimary() {
        return draw(battleField, Designations.CELL);
    }

    public String displayEnemy() {
        return displayShadow(battleField);
    }

    private String displayShadow(Designations[][] battleField) {
        return draw(battleField, Designations.FOG);
    }

    private String draw(Designations[][] battleField, Designations designations) {
        var builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10");
        builder.append(Constant.NEW_LINE);
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[i].length; j++) {
                if (j == 0) {
                    builder.append(fromIntToChar(i)).append(Constant.SPACE);
                }
                switch (battleField[i][j]) {
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
        var startPosX = (start[0] - 1 < 0) ? start[0] : start[0] - 1;
        var startPosY = (start[1] - 1 < 0) ? start[1] : start[1] - 1;
        var endPosX = (end[0] + 1 >= Constant.FIELD_SIZE) ? end[0] : end[0] + 1;
        var endPosY = (end[1] + 1 >= Constant.FIELD_SIZE) ? end[1] : end[1] + 1;

        for (int a = startPosX; a <= endPosX; a++) {
            for (int b = startPosY; b <= endPosY; b++) {
                if (battleField[a][b] == Designations.CELL)
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
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





