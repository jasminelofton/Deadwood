/* This class represents a role for a board or card that a player can act as. 
 * Each role has a name, a level indicating its player minimum level requirenment
 * to act on this role, and a description of the role blurb.
 */



class Role {

    private String part;
    private String level;
    private String line;
    private Boolean available;
    private Boolean onCard;

    public Role(String part, String level, String line, Boolean onCard) {
        this.part = part;
        this.level = level;
        this.line = line;
        this.available = true;
        this.onCard = onCard;
    }

    public String getPart() {
        return part;
    }

    public boolean isOnCard() {
        return onCard;
    }

    public String getLevel() {
        return level;
    }

    public String getLine() {
        return line;
    }

    public Boolean getAvailable() {
        return available;
    }
}