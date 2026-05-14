public class Player {
    private int credits;
    private int dollars;
    private int rank;
    private Room room;
    private boolean working;

    public Player(Room trailer) {
        room = trailer;
        dollars = 0;
        working = false;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isWorking() {
        return working;
    }

    public int getCredits() {
        return credits;
    }

    public int getDollars() {
        return dollars;
    }

    public int getRank() {
        return rank;
    }

    public Room getRoom() {
        return room;
    }

    
}