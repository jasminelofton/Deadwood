import java.util.Arrays;
import java.util.Random;

public class Dice {

    Random random = new Random();
    
    public int roll(int n){
        int total = 0;

        for(int i = 0; i < n; i++) {
            total += (random.nextInt(6) + 1);
        }

        return total;
    }


    public int[] rollForBonus(int n) {
        int[] die = new int[n];

        for (int i = 0; i < n; i++) {
            die[i] = random.nextInt(6) + 1; 
        }

        Arrays.sort(die);

        for (int i = 0; i < die.length / 2; i++) {
            int temp = die[i];
            die[i] = die[die.length - 1 - i];
            die[die.length - 1 - i] = temp;
        }

        return die;
    }
}