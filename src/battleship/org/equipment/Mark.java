package battleship.org.equipment;

public enum Mark {
    GAME_OVER("You sank the last ship. You won. Congratulations!"),
    GAME_CONTINUOUS("You hit a ship!"),
    GAME_CONTINUOUS_MISS("You missed."),
    GAME_CONTINUOUS_SINK_SHIP("You sank a ship!");
    final String message;

    Mark(String result) {
        this.message = result;
    }

    public String getMessage() {
        return message;
    }
}
