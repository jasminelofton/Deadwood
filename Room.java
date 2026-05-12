import java.util.ArrayList;

public class Room {
    String name;
    ArrayList<String> neighbors;

    public Room(){}

    public Room(String name) {
        this.name = name;
        neighbors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setNeighbors(ArrayList<String> neighbors) {
        this.neighbors = neighbors;
    }
}