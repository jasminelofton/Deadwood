// for the future interface
public class Take {
    int xCordinate;
    int yCordinate;
    int height;
    int width;
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
