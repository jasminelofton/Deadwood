import java.util.*;

// Represents the Casting Office room where players can spend dollars or credits
// to upgrade their rank. Rank determines which roles a player can take.
// Upgrade costs are loaded from board.xml via setUpgrades().
public class CastingOffice extends Room {
    public List<Integer> ranks;         // available ranks to upgrade to (2–6)
    public List<Integer> moneyPrices;   // dollar cost for each rank upgrade (parallel to ranks)
    public List<Integer> creditPrices;  // credit cost for each rank upgrade (parallel to ranks)
    // Maps each rank to a two-element array: [dollarCost, creditCost].
    private Map<Integer, int[]> upgradeOptions;

    public CastingOffice(String name) {
        super(name);
        upgradeOptions = new HashMap<>();
    }

    // Returns the dollar cost to upgrade to the given rank.
    public int getMoneyCost(int rank) {
        return upgradeOptions.get(rank)[0];
    }

    // Returns the credit cost to upgrade to the given rank.
    public int getCreditCost(int rank) {
        return upgradeOptions.get(rank)[1];
    }

    public List<Integer> getRanks() {
        return ranks;
    }

    public List<Integer> getMoneyPrices() {
        return moneyPrices;
    }

    public List<Integer> getCreditPrices() {
        return creditPrices;
    }

    // Populates upgrade pricing from the parallel lists parsed out of board.xml.
    // Ranks 2–6 with their dollar and credit costs are hardcoded as defaults;
    // the XML data is then iterated to build the upgradeOptions lookup map.
    // Throws IllegalArgumentException if a currency value is not "dollar" or "credit".
    public void setUpgrades(ArrayList<String> levels, ArrayList<String> currencies, ArrayList<String> amts) throws IllegalArgumentException {

        // Default pricing table matching the official Deadwood rules.
        ranks = new ArrayList<>(Arrays.asList());
        this.moneyPrices = new ArrayList<>(Arrays.asList());
        this.creditPrices = new ArrayList<>(Arrays.asList());

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

        // Build the rank → [dollarCost, creditCost] lookup map from the parallel lists.
        for (int i = 0; i < ranks.size(); i++) {
            upgradeOptions.put(ranks.get(i), new int[]{moneyPrices.get(i), creditPrices.get(i)});
        }
    }
}
