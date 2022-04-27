package battleship.org.equipment;

public enum ShipTypes {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier");
//    BATTLESHIP(4, "Battleship"),
//    SUBMARINE(3, "Submarine"),
//    CRUISER(3, "Cruiser"),
//    DESTROYER(2, "Destroyer");

    final int sell;
    final String type;

    ShipTypes(int sell, String type) {
        this.sell = sell;
        this.type = type;
    }

    public int getSell() {
        return sell;
    }

    public String getType() {
        return type;
    }


}
