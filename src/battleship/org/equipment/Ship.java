package battleship.org.equipment;

import java.util.List;

public class Ship {
    private final ShipTypes type;
    private final List<Coordinates> compartments;

    public Ship(ShipTypes type, List<Coordinates> compartments) {
        this.type = type;
        this.compartments = compartments;
    }

    public boolean isShipAlive() {
        return compartments.stream()
                .anyMatch(x -> x.getStatus().equals(Designations.CELL));
    }

    public boolean exist(int x, int y) {
        for (Coordinates compartment : compartments) {
            if (compartment.getY() == y && compartment.getX() == x) {
                return true;
            }
        }
        return false;
    }

    public List<Coordinates> getCompartments() {
        return compartments;
    }
}
