/* This class represents a role for a board or card that a player can act as. 
 * Each role has a name, a level indicating its player minimum level requirenment
 * to act on this role, and a description of the role blurb.
 */



class Role {

    private String part;
    private String level;
    private String line;
    private Boolean available;

    public Role(String part, String level, String line) {
        this.part = part;
        this.level = level;
        this.line = line;
        this.available = true;
    }

    public String getPart() {
        return part;
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