package org.battleship.equipment;

public class Cell {
    private final int x;
    private final int y;
    private boolean pointedFire;
    private Ship ship;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.ship = null;
        this.pointedFire = false;
    }

    public Cell(int x, int y, Ship ship) {
        this.x = x;
        this.y = y;
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isPointedFire() {
        return pointedFire;
    }

    public void setPointedFire(boolean pointedFire) {
        this.pointedFire = pointedFire;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

