// Represents the scene description attached to a SceneCard.
// Each scene has a number (matching the scene card number in cards.xml)
// and a flavour-text description shown to players.
public class Scene {
    private int number;
    private String description;

    public Scene(int number, String description) {
        this.number = number;
        this.description = description;
    }

    // Returns the scene number as listed in the card XML.
    public int getNumber() {
        return number;
    }

    // Returns the flavour-text blurb describing the scene.
    public String getDescription() {
        return description;
    }
}
