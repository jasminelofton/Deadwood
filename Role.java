/* This class represents a role for a board or card that a player can act as. 
 * Each role has a name, a level indicating its player minimum level requirenment
 * to act on this role, and a description of the role blurb.
 */



class Role {

    private String roleName;
    private int level;
    private String description;
    private Boolean available;

    public void role(String roleName, int level, String description) {
        this.roleName = roleName;
        this.level = level;
        this.description = description;
        this.available = false;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getAvailable() {
        return available;
    }
}