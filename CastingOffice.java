import java.util.*;

public class CastingOffice extends Room {
    public List<Integer> ranks;
    public List<Integer> moneyPrices;
    public List<Integer> creditPrices;
    private Map<Integer, int[]> upgradeOptions;
    

    
    public CastingOffice(String name) {
        super(name);
        upgradeOptions = new HashMap<>();
    }

    public int getMoneyCost(int rank){
        return upgradeOptions.get(rank)[0];
    }

    public int getCreditCost(int rank){
        return upgradeOptions.get(rank)[1];
    }

    public List<Integer> getRanks(){
        return ranks;
    }

    public List<Integer> getMoneyPrices(){
        return moneyPrices;
    }

    public List<Integer> getCreditPrices(){
        return creditPrices;
    }

    public void setUpgrades(ArrayList<String> levels, ArrayList<String> currencies, ArrayList<String> amts) throws IllegalArgumentException {

        ranks = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6));
        this.moneyPrices = new ArrayList<>(Arrays.asList(4, 10, 18, 28, 40));
        this.creditPrices = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 25));

        int rank;

        for (int i = 0; i < levels.size(); i++) {
            rank = Integer.parseInt(levels.get(i));
            if (!ranks.contains(rank)) {
                ranks.add(rank);
            }

            int price = Integer.parseInt(amts.get(i));

            if (currencies.get(i).equals("dollar")) {
            moneyPrices.add(price);
            } else if (currencies.get(i).equals("credit")) {
                creditPrices.add(price);
            } else {
                throw new IllegalArgumentException("not dollar or credit");
            }
        } 

        for (int i = 0; i < ranks.size(); i++){
            upgradeOptions.put(ranks.get(i), new int[]{moneyPrices.get(i), creditPrices.get(i)});
            
        }
    }

}
