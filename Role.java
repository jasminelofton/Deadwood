/* Represents a role that a player can take on an acting set.
 * Each role has a name (part), a minimum rank level required to take it,
 * a dialogue line the player "delivers" when acting, and flags for whether
 * the role is currently available and whether it lives on the scene card
 * (on-card) or on the set board itself (off-card).
 * On-card roles pay credits on success; off-card roles pay dollars on both
 * success and failure.
 */
class Role {

    private String part;
    private int level;      // minimum player rank required to take this role
    private String line;    // the dialogue line associated with this role
    private Boolean available;  // false once a player has taken this role
    private Boolean onCard;     // true if this role is on the scene card, false if on the set

    // Roles always start as available; onCard is set based on where the role was parsed from.
    public Role(String part, int level, String line, Boolean onCard) {
        this.part = part;
        this.level = level;
        this.line = line;
        this.available = true;
        this.onCard = onCard;
    }

    // Returns the role's name (e.g. "Lead Detective").
    public String getPart() {
        return part;
    }

    // Returns true if this role belongs to the scene card (on-card), false if it belongs to the set (off-card).
    public boolean isOnCard() {
        return onCard;
    }

    // Returns the minimum player rank needed to take this role.
    public int getLevel() {
        return level;
    }

    // Returns the dialogue line the player delivers when acting this role.
    public String getLine() {
        return line;
    }

    // Returns true if no player has taken this role yet.
    public Boolean getAvailable() {
        return available;
    }

    // Marks this role as taken (false) or available again (true).
    public void setAvailable(Boolean isAvail) {
        available = isAvail;
    }
}