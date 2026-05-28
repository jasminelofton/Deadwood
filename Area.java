// Represents a rectangular bounding box used by the GUI to position
// a role token or card image on the board.
// Coordinates (x, y) mark the top-left corner; h and w are height and width in pixels.
// Parsed from the <area> elements in board.xml and cards.xml.
public class Area {
    private int x;  // left edge in pixels
    private int y;  // top edge in pixels
    private int h;  // height in pixels
    private int w;  // width in pixels

    public Area(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }
}