import java.util.ArrayList;
import java.util.List;

// Represents one of the ten filming locations on the Deadwood board.
// Each ActingSet holds a face-down SceneCard, a fixed number of takes (shot counter),
// permanent off-card roles (defined in board.xml), and the players currently working there.
// On-card players act on roles from the SceneCard; off-card players act on the set's own roles.
public class ActingSet extends Room {
    private List<Room> adjacencyList;  // unused — neighbor data is stored in Room.neighbors
    private int shotCounter;           // number of takes remaining before the scene wraps
    private SceneCard sceneCard;       // the scene card currently placed on this set (null if wrapped)
    private ArrayList<Role> roles;     // permanent off-card roles belonging to this set
    private ArrayList<Take> takes;   // take labels from board.xml; size initialises shotCounter
    private ArrayList<Player> onCardPlayers;   // players working on-card (scene card) roles
    private ArrayList<Player> offCardPlayers;  // players working off-card (set board) roles

    public ActingSet(String name) {
        super(name);
        roles = new ArrayList<>();
        takes = new ArrayList<>();
        onCardPlayers = new ArrayList<>();
        offCardPlayers = new ArrayList<>();
        sceneCard = null;
    }

    // Returns the neighboring room names (delegates to the inherited neighbors list).
    public ArrayList<String> getAdjacentNeigbors() {
        return neighbors;
    }

    // Returns the number of takes left before this scene wraps.
    public int getShotCounter() {
        return shotCounter;
    }

    // Returns the SceneCard currently placed on this set, or null if the scene has wrapped.
    public SceneCard getSceneCard() {
        return sceneCard;
    }

    // Returns players currently working on-card roles at this set.
    public ArrayList<Player> getOnCardPlayers() {
        return onCardPlayers;
    }

    // Registers a player as working an on-card role at this set.
    public void addOnCardplayer(Player p) {
        onCardPlayers.add(p);
    }

    // Returns players currently working off-card roles at this set.
    public ArrayList<Player> getOffCardPlayers() {
        return offCardPlayers;
    }

    public int getPlayerCount() {
        return onCardPlayers.size() + offCardPlayers.size();
    }

    // Registers a player as working an off-card role at this set.
    public void addOffCardplayer(Player p) {
        offCardPlayers.add(p);
    }

    // Removes all players from both on-card and off-card lists (called after scene wraps).
    public void clearPlayers() {
        offCardPlayers.clear();
        onCardPlayers.clear();
    }

    // Places a new SceneCard on this set (called at the start of each day).
    public void setSceneCard(SceneCard sceneCard) {
        this.sceneCard = sceneCard;
    }

    // Removes the SceneCard once the scene wraps (shot counter hits zero).
    public void removeSceneCard() {
        this.sceneCard = null;
    }

    // Decrements the shot counter by one after a successful act.
    public void removeShotCounter() {
        shotCounter--;
    }

    // Resets the shot counter to the number of takes on this set (used at start of each day).
    public void resetShotCounter() {
        shotCounter = takes.size();
    }

    // Returns all roles (both off-card set roles and on-card scene card roles).
    // Throws if no scene card is present.
    public ArrayList<Role> getAllRoles() throws IllegalStateException {
        if (sceneCard == null) {
            throw new IllegalStateException("No scene card on this acting set.");
        }

        ArrayList<Role> allRoles = new ArrayList<>(roles);
        allRoles.addAll(sceneCard.getRoles());

        return allRoles;
    }

    // Returns all currently available roles (not yet taken).
    // Note: this method has a bug — it adds the off-card roles list twice, which can
    // cause ConcurrentModificationException when iterating and removing. Use getAllOpenRoles(int rank) instead.
    public ArrayList<Role> getAllOpenRoles() throws IllegalStateException {
        if (sceneCard == null) {
            throw new IllegalStateException("No scene card on this acting set.");
        }

        ArrayList<Role> allRoles = new ArrayList<>(roles);
        allRoles.addAll(sceneCard.getRoles());
        allRoles.addAll(roles);  // bug: off-card roles added twice
        for (Role role : allRoles) {
            if (!role.getAvailable()) {
                allRoles.remove(role);  // bug: modifying list while iterating
            }
        }

        return allRoles;
    }

    // Returns all available roles whose level requirement is at or below the given rank.
    // Combines off-card (set) roles and on-card (scene card) roles into one filtered list.
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

    // Stores the take labels from board.xml and initialises the shot counter.
    public void setTakes(ArrayList<Take> takes) {
        this.takes = takes;
        shotCounter = takes.size();
    }
 
    // Returns all takes for this set (used by the GUI to render shot counter tokens).
    public ArrayList<Take> getTakes() {
        return takes;
    }

    // Builds the permanent off-card roles for this set from parallel lists of
    // part names, rank levels, and dialogue lines parsed out of board.xml.
    public void setRoles(ArrayList<String> parts, ArrayList<Integer> levels, ArrayList<String> lines) {
        for (int i = 0; i < parts.size(); i++) {
            roles.add(new Role(parts.get(i), levels.get(i), lines.get(i), false));
        }
    }
}