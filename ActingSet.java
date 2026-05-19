import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActingSet extends Room {
    private List<Room> adjacencyList;
    private int shotCounter;
    private SceneCard sceneCard;
    private ArrayList<Role> roles;    
    private ArrayList <String> takes; // May convert to Integer later if needed.

    public ActingSet(String name) {
        super(name);
        roles = new ArrayList<>();
        takes = new ArrayList<>();
        sceneCard = null;
    }


    public ArrayList<String> getAdjacentNeigbors() {
        return neighbors;
    }

    public int getShotCounter() {
        return shotCounter;
    }

    public SceneCard getSceneCard() {
        return sceneCard;
    }

    // TODO
    public ArrayList<Player> getOnCardPlayers() {

    }
    
    // TODO
    public ArrayList<Player> getOffCardPlayers() {

    }

    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = sceneCard;
    }

    public void removeSceneCard() {
        this.sceneCard = null;
    }

    public void removeShotCounter() {
        shotCounter--;
    }

    // should return roles from both the set and the scene card.
    public ArrayList<Role> getAllRoles() throws IllegalStateException {
        if (sceneCard == null) {
            throw new IllegalStateException("No scene card on this acting set.");
        }

        ArrayList<Role> allRoles = new ArrayList<>(roles);
        allRoles.addAll(sceneCard.getRoles());

        return allRoles;
    }

    public ArrayList<Role> getAllOpenRoles() throws IllegalStateException {
        if (sceneCard == null) {
            throw new IllegalStateException("No scene card on this acting set.");
        }

        ArrayList<Role> allRoles = new ArrayList<>(roles);
        allRoles.addAll(sceneCard.getRoles());
        allRoles.addAll(roles);
        for(Role role : allRoles) {
            if(!role.getAvailable()) {
                allRoles.remove(role);
            }
        }

        return allRoles;
    }

    // overload to filter by rank too
    public ArrayList<Role> getAllOpenRoles(int rank) throws IllegalStateException {

        if (sceneCard == null) {
            throw new IllegalStateException("no scene card");
        }

        ArrayList<Role> openRoles = new ArrayList<>();

        ArrayList<Role> combinedRoles = new ArrayList<>(roles);
        combinedRoles.addAll(sceneCard.getRoles());

        for (Role role : combinedRoles) {
            if (role.getAvailable() && role.getLevel() <= rank) {
                openRoles.add(role);
            }
        }

        return openRoles;
    }

    public void setTakes(ArrayList<String> takes) {
        this.takes = takes; // will likely need its own class later
        shotCounter = takes.size();
    }

    public void setRoles(ArrayList<String> parts, ArrayList<Integer> levels, ArrayList<String> lines) {
        for (int i = 0; i < parts.size(); i++) {
            roles.add(new Role(parts.get(i), levels.get(i), lines.get(i), false));
        }
    }

}