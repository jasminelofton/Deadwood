// Represents a single "take" marker on an ActingSet.
// Each acting set starts with a fixed number of takes (read from board.xml).
// When a player successfully acts, one take is removed (shot counter goes down).
// When all takes are gone the scene wraps.
// Currently ActingSet tracks takes as a count (int shotCounter); this class
// exists to support a future GUI that will display individual take tokens.
public class Take {
    boolean isShotCounter;  // true while this take marker is still on the board

    public Take() {
        isShotCounter = true;
    }

    // Flips this take marker off — called when a successful act uses it up.
    public void removeShotCounter() {
        isShotCounter = false;
    }

    // Returns true if this take marker is still present on the set.
    public boolean isShotCounter() {
        return isShotCounter;
    }
}
