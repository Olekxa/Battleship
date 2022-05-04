package org.battleship.equipment;

public class Cell {
    private final int x;
    private final int y;
    private boolean pointedFire;
    private boolean placedShip;

    public Cell(int x, int y, boolean placedShip) {
        this.x = x;
        this.y = y;
        this.placedShip = placedShip;
        this.pointedFire = false;
    }

    public boolean isPointedFire() {
        return pointedFire;
    }

    public boolean isPlacedShip() {
        return placedShip;
    }

    public void setPointedFire(boolean pointedFire) {
        this.pointedFire = pointedFire;
    }

    public void setPlacedShip(boolean placedShip) {
        this.placedShip = placedShip;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

