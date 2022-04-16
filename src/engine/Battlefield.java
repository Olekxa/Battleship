package engine;

import java.util.Arrays;


public class Battlefield {
    private final Designations[][] battleField = new Designations[Constant.FIELD_SIZE][Constant.FIELD_SIZE];

    public Battlefield() {
        for (Designations[] designations : battleField) {
            Arrays.fill(designations, Designations.FOG);
        }
    }

    public void placeShip(Ship ship, String[] coordinates) {
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

        if (Math.abs(start[positionInSpace] - end[positionInSpace]) + 1 != ship.sell)
            throw new IllegalArgumentException("Error! Wrong length of the Submarine! Try again:");

        checkNeighbors(start, end);
        if (positionInSpace == 1)
            for (int i = start[1]; i <= end[1]; i++) battleField[start[0]][i] = Designations.CELL;
        else for (int i = start[0]; i <= end[0]; i++) battleField[i][start[1]] = Designations.CELL;
    }

    public String markShot(String coordinates) {
        var x = coordinates.charAt(0) - 65;
        var y = Integer.parseInt(coordinates.substring(1)) - 1;

        if (x >= Constant.FIELD_SIZE || x < 0 ||
                y >= Constant.FIELD_SIZE || y < 0) {
            throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
        }
        if (Designations.CELL.equals(battleField[x][y])) {
            battleField[x][y] = Designations.HIT;
            return "You hit a ship!";
        }
        battleField[x][y] = Designations.MISS;
        return "You missed!";
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

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10");
        builder.append(Constant.NEW_LINE);
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[i].length; j++) {
                if (j == 0) {
                    builder.append((char) (65 + i)).append(Constant.SPACE);
                }
                switch (battleField[i][j]) {
                    case FOG -> builder.append(Designations.FOG.getMark());
                    case CELL -> builder.append(Designations.CELL.getMark());
                    case HIT -> builder.append(Designations.HIT.getMark());
                    case MISS -> builder.append(Designations.MISS.getMark());
                }
                if (j < 9) builder.append(Constant.SPACE);
            }
            if (i < 9) builder.append(Constant.NEW_LINE);
        }
        return builder.toString();
    }
}





