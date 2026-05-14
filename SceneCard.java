import java.util.ArrayList;

public class SceneCard {
    private ArrayList <Role> roles;

    public SceneCard (ArrayList<Role> roles) {
        this.roles = roles;
        roles = new ArrayList<>();
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}
