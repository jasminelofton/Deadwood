/* This class represents a role for a board or card that a player can act as. 
 * Each role has a name, a level indicating its player minimum level requirenment
 * to act on this role, and a description of the role blurb.
 */



class Role {

    private String RoleName;
    private int Level;
    private String Description;

    public Role(String RoleName, int Level, String Description) {
        this.RoleName = RoleName;
        this.Level = Level;
        this.Description = Description;
    }

    public String getRoleName() {
        return RoleName;
    }

    public int getLevel() {
        return Level;
    }

    public String getDescription() {
        return Description;
    }
}