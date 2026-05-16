import java.util.ArrayList;

public class SceneCard {
    private String name;
    private String img;
    private int budget;
    private Scene scene;
    private ArrayList <Role> roles;

    public SceneCard (String name, String img, int budget, Scene scene, ArrayList<Role> roles) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.scene = scene;
        this.roles = roles;
        roles = new ArrayList<>();
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getBudget() {
        return budget;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
