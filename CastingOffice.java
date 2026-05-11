import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CastingOffice extends Room {
    private String instructions;
    List<Integer> ranks;
    List<Integer> moneyPrices;
    List<Integer> creditPrices;

    
    public CastingOffice(String name) {
        super(name);

        this.ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6));
        this.moneyPrices = new ArrayList<>(Arrays.asList(4, 10, 18, 28, 40));
        this.creditPrices = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 25));
    }
}
