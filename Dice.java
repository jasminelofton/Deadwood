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
}