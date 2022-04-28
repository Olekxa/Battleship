package org.battleship.equipment;

public enum Designations {
    FOG("~"),
    CELL("O"),
    HIT("X"),
    MISS("M");

    private final String mark;

    Designations(String mark) {
        this.mark = mark;
    }

    public String getMark() {
        return mark;
    }
}
