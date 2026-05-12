import java.util.ArrayList;
import java.util.Random;

public class ActingSet extends Room {
    private int counters;
    private ArrayList<Dice> die = new ArrayList<Dice>();

    public ActingSet (String name) {
        super(name);
    }

    // need to figure what this does
    private static int roleDice() {
        return -1;
    }

    // aware what this does, has general base 
    public int[] Act(Role role){
        counters--;
        return null;
    }

    // base
    private int[] wrapScene(){
        return null;
    }
}