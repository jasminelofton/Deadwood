import java.util.*;

public class CastingOffice extends Room {
    private String instructions;
    public List<Integer> ranks;
    public List<Integer> moneyPrices;
    public List<Integer> creditPrices;
    private Map<Integer, int[]> upgradeOptions;

    
    public CastingOffice(String name) {
        super(name);

        this.ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6));
        this.moneyPrices = new ArrayList<>(Arrays.asList(4, 10, 18, 28, 40));
        this.creditPrices = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 25));

        upgradeOptions = new HashMap<>();
        for (int i = 0; i < ranks.size(); i++){
            upgradeOptions.put(ranks.get(i), new int[]{moneyPrices.get(i), creditPrices.get(i)});
        }
    }

    public int getMoneyCost(int rank){
        return upgradeOptions.get(rank)[0];
    }

    public int getCreditCost(int rank){
        return upgradeOptions.get(rank)[1];
    }

    public String getInstructions(){
        return instructions;
    }

    public List<Integer> getRanks(int rank){
        return ranks;
    }

    public List<Integer> getMoneyPrices(int rank){
        return moneyPrices;
    }

    public List<Integer> getCreditPrices(int rank){
        return creditPrices;
    }

    public Map<Integer, int[]> getUpgradeOptions(int rank){
        return upgradeOptions;
    }

    public void setUpgrades(ArrayList<String> levels, ArrayList<String> Currencies, ArrayList<String> amts) {
        //TODO
    }
}
