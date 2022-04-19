package battleship.org.equipment;

public class Coordinates {
    private int x;
    private int y;
    private Designations status;

    public Coordinates(int x, int y, Designations status) {
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
