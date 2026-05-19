// for the future interface
public class Take {
    boolean isShotCounter;

    public Take() {
        isShotCounter = true;
    }

    public void removeShotCounter() {
        isShotCounter = false;
    }

    public boolean isShotCounter() {
        return isShotCounter;
    }
}
