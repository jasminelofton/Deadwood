import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActingSet extends Room {
    private List<Room> adjacencyList;
    private int shotCounter;
    private Optional<SceneCard> sceneCard;
    private List<Role> offCardRoles;
    private ArrayList<String> parts;
    private ArrayList <String> takes; // May convert to Integer later if needed.

    public ActingSet(String name) {
        super(name);
    }


    public List<Room> getAdjacencyList() {
        return adjacencyList;
    }

    public int getShotCounter() {
        return shotCounter;
    }

    public Optional<SceneCard> getSceneCard() {
        return sceneCard;
    }

    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = Optional.of(sceneCard);
    }

    public void removeSceneCard() {
        this.sceneCard = Optional.empty();
    }

    public List<Role> getOffCardRoles() {
        return offCardRoles;
    }

    public List<Role> getOffCardRoles(int rank) {
        List<Role> roles = new ArrayList<>();

        for (Role r : offCardRoles) {
            if (r.getLevel() <= rank) {
                roles.add(r);
            }
        }

        return roles;
    }

    public void setTakes(ArrayList<String> takes) {
        this.takes = takes;
    }

    public void setParts(ArrayList<String> parts, ArrayList<String> levels, ArrayList<String> lines) {
        this.parts = parts;
    }

}