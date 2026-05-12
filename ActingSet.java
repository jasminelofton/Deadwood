import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ActingSet extends Room {
    private String name;
    private List<Room> adjacencyList;
    private int shotCounter;
    private Optional<SceneCard> sceneCard;
    private List<Role> offCardRoles;


    public String getName() {
        return name;
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

}