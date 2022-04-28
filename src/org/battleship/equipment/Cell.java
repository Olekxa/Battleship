package org.battleship.equipment;

public class Cell {
    private final int x;
    private final int y;
    private Designations status;

    public Cell(int x, int y, Designations status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    public void setStatus(Designations status) {
        this.status = status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Designations getStatus() {
        return status;
    }
}
