public class PlayerDTO {
    private final int playerNumber;
    private final String dieImageFilename;
    private final int rank;
    private final int dollars;
    private final int credits;
    private final boolean isOnScene;
    private final int rehearsalCount;
    private final int sceneBudget;
    private final boolean isCardRevealed;
    private final String availableMoves;

    public PlayerDTO(int playerNumber, String dieImageFilename, int rank, 
                              int dollars, int credits, boolean isOnScene, 
                              int rehearsalCount, int sceneBudget, 
                              boolean isCardRevealed, String availableMoves) {
        this.playerNumber = playerNumber;
        this.dieImageFilename = dieImageFilename;
        this.rank = rank;
        this.dollars = dollars;
        this.credits = credits;
        this.isOnScene = isOnScene;
        this.rehearsalCount = rehearsalCount;
        this.sceneBudget = sceneBudget;
        this.isCardRevealed = isCardRevealed;
        this.availableMoves = availableMoves;
    }

    public int getPlayerNumber() { return playerNumber; }
    public String getDieImageFilename() { return dieImageFilename; }
    public int getRank() { return rank; }
    public int getDollars() { return dollars; }
    public int getCredits() { return credits; }
    public boolean isOnScene() { return isOnScene; }
    public int getRehearsalCount() { return rehearsalCount; }
    public int getSceneBudget() { return sceneBudget; }
    public boolean isCardRevealed() { return isCardRevealed; }
    public String getAvailableMoves() { return availableMoves; }
}
