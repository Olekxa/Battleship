package org.battleship.equipment;

import java.util.List;

public class Ship {
    private final ShipTypes type;
    private final List<Cell> compartments;

    public Ship(ShipTypes type, List<Cell> compartments) {
        this.type = type;
        this.compartments = compartments;
    }

    public boolean isShipAlive() {
        return compartments.stream()
                .anyMatch(x -> !x.isPointedFire());
    }

    public boolean exist(int x, int y) {
        for (Cell compartment : compartments) {
            if (compartment.getY() == y && compartment.getX() == x) {
                return true;
            }
        }
        return false;
    }

    public List<Cell> getCompartments() {
        return compartments;
    }
}
