import java.util.Arrays;
import java.util.Random;

// Handles all dice rolling for the game.
public class Dice {

    Random random = new Random();

    // Rolls n six-sided dice and returns their combined total.
    // Used when a player acts (roll(1) + rehearsal bonus vs. budget).
    public int roll(int n) {
        int total = 0;

        for (int i = 0; i < n; i++) {
            total += (random.nextInt(6) + 1);
        }

        return total;
    }

    // Rolls n dice for the scene-wrap bonus payout and returns them sorted
    // in descending order. The highest dice values go to the highest-ranked roles.
    // Arrays.sort produces ascending order; the second loop reverses it in-place.
    public int[] rollForBonus(int n) {
        int[] die = new int[n];

        for (int i = 0; i < n; i++) {
            die[i] = random.nextInt(6) + 1;
        }

        // Sort ascending first, then reverse to get descending order.
        Arrays.sort(die);

        for (int i = 0; i < die.length / 2; i++) {
            int temp = die[i];
            die[i] = die[die.length - 1 - i];
            die[die.length - 1 - i] = temp;
        }

        return die;
    }
}