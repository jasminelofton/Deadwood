import java.util.HashMap;
import java.util.Map;

// Represents a single player in the Deadwood game.
// A player tracks their currency (dollars and credits), their rank,
// their current room on the board, and the role they are currently working on.
public class Player {
    private int credits;
    private int dollars;
    private int rank;
    private Room room;
    private Role role;
    // Maps each role to the number of rehearsal chips earned for that role.
    // Chips are cleared when the scene wraps or the player stops working the role.
    private Map<Role, Integer> rehearsalBonuses = new HashMap<>();

    // Players always start in the Trailers room with no role and no dollars.
    public Player(Room trailer) {
        room = trailer;
        dollars = 0;
        role = null;
    }

    // Adds n rehearsal chips to the given role, creating the entry if it doesn't exist yet.
    public void addRehearsalBonus(Role role, Integer n) {
        if (rehearsalBonuses.containsKey(role)) {
            rehearsalBonuses.put(role, rehearsalBonuses.get(role) + n);
        } else {
            rehearsalBonuses.put(role, n);
        }
    }

    // Removes all rehearsal chips for the given role (called when scene wraps).
    public void clearRehearsalBonus(Role role) {
        rehearsalBonuses.remove(role);
    }

    // Returns accumulated rehearsal chips for the given role, or 0 if none exist.
    public int getRehearsalBonus(Role role) {
        return rehearsalBonuses.getOrDefault(role, 0);
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Returns true if the player is currently assigned to a role on an acting set.
    public boolean hasRole() {
        if (role == null) return false;
        return true;
    }

    public int getCredits() {
        return credits;
    }

    public int getDollars() {
        return dollars;
    }

    public int getRank() {
        return rank;
    }

    public Room getRoom() {
        return room;
    }

    public Role getRole() {
        return role;
    }

    public void addCredits(int n) {
        credits += n;
    }

    public void addDollars(int n) {
        dollars += n;
    }

    public void removeCredits(int n) {
        credits -= n;
    }

    public void removeDollars(int n) {
        dollars -= n;
    }
}