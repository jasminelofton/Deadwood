import java.util.ArrayList;

public class Moderator {
    ArrayList<Player> players;
    private final static int maxPlayers = 8;
    private int daysLeft;
    private Controller controller;

    public void endDay() {
        daysLeft--;
    }

    public int daysLeft() {
        return daysLeft;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setUpPlayerRules (int playerCount) {
        validatePlayerCount(playerCount);
        players = new ArrayList<Player>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
        calcAndSetPlayerStartRanks(playerCount);
        calcAndSetPlayerStartCredits(playerCount);
        calcAndSetStartDaysLeft(playerCount);
       // players.forEach((player) -> {
       // player.setRoom(/*TODO */});
     }

    private void validatePlayerCount(int playerCount) {
        if ((playerCount < 1) || (playerCount > maxPlayers)) {
            throw new NumberFormatException();
        }
    }

    // initialize all the starting ranks for every player playing
    private void calcAndSetPlayerStartRanks(int playerCount) {
        int startRank;

        if (playerCount >= 7) {
            startRank = 2;
        }
        else {
            startRank = 1;
        }

        players.forEach((player) -> {
            player.setRank(startRank);
        });
    }

    // Initialize all the start credits for all players
    private void calcAndSetPlayerStartCredits(int playerCount) {
        int startCredits;

        startCredits = switch (playerCount) {
            case 5 -> 2;
            case 6 -> 4;
            default -> 0;
        };

        players.forEach((player) -> {
            player.setCredits(startCredits);
        });
    }

    private void calcAndSetStartDaysLeft(int playerCount) {
        daysLeft = switch (playerCount) {
            case 2, 3 -> 3;
            default -> 4;
        };
    }

    
}