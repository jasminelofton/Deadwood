import java.util.ArrayList;

// Base class for all locations on the Deadwood board.
// Subclasses are ActingSet, Trailer, and CastingOffice.
// Each room has a name and a list of neighboring room names that players can move to.
public class Room {
    String name;
    Area area;
    ArrayList<String> neighbors;  // names of rooms directly reachable from this one

    // No-arg constructor required for subclass instantiation patterns.
    public Room(){}

    public Room(String name) {
        this.name = name;
        neighbors = new ArrayList<>();
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return area.getX();
    }

    public int getY() {
        return area.getY();
    }

    // Replaces the full neighbor list (populated from board.xml during setup).
    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<String> getNeighbors() {
        return neighbors;
    }
}