import java.util.HashMap;
import java.util.Map;

public class Player {
    private int credits;
    private int dollars;
    private int rank;
    private Room room;
    private Role role;
    private Map<Role, Integer> rehearsalBonuses = new HashMap<>();

    public Player(Room trailer) {
        room = trailer;
        dollars = 0;
        role = null;
    }

    public void addRehearsalBonus(Role role, Integer n) {
        if (rehearsalBonuses.containsKey(role)) {
            rehearsalBonuses.put(role, rehearsalBonuses.get(role) + n);
        } else {
            rehearsalBonuses.put(role, n);
        }
    }

    public void clearRehearsalBonus(Role role) {
        rehearsalBonuses.remove(role);
    }

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