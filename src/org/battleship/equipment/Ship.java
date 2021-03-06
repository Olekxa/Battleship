package org.battleship.equipment;

import java.util.List;

public class Ship {
    private final List<Cell> compartments;

    public Ship(ShipTypes type, List<Cell> compartments) {
        this.compartments = compartments;
    }

    public boolean statusOfShip() {
        return compartments.stream()
                .anyMatch(x -> !x.isPointedFire());
    }

    public List<Cell> getCompartments() {
        return compartments;
    }
}
