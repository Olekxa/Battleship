package org.battleship.equipment;

public enum ShipTypes {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int cell;
    private final String type;

    ShipTypes(int cell, String type) {
        this.cell = cell;
        this.type = type;
    }

    public int getCell() {
        return cell;
    }

    public String getType() {
        return type;
    }


}
