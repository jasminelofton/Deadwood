import java.util.ArrayList;

// Represents a scene card drawn from the deck and placed face-down on an ActingSet.
// A card has a film title (name), an image filename, a production budget (used as the
// dice target when acting), a Scene with flavour text, and a list of on-card roles.
public class SceneCard {
    private String name;    // title of the film being shot
    private String img;     // filename of the card image for the GUI
    private int budget;     // dice roll must meet or exceed this value for a successful act
    private Scene scene;
    private ArrayList<Role> roles;  // on-card roles parsed from cards.xml

    // Note: the line `roles = new ArrayList<>()` below reassigns the local parameter,
    // NOT this.roles, so this.roles correctly keeps the passed-in list. That line is dead code.
    public SceneCard(String name, String img, int budget, Scene scene, ArrayList<Role> roles) {
        this.name = name;
        this.img = img;
        this.budget = budget;
        this.scene = scene;
        this.roles = roles;
        roles = new ArrayList<>();  // dead code: reassigns local parameter, has no effect
    }

    // Returns the on-card roles for this scene card.
    public ArrayList<Role> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    // Returns the image filename used by the GUI to display this card.
    public String getImg() {
        return img;
    }

    // Returns the budget (the dice target players must meet or beat when acting).
    public int getBudget() {
        return budget;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    // Appends a role to this card's role list (used during XML parsing).
    public void addRole(Role role) {
        this.roles.add(role);
    }
}
