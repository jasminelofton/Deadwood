public class Player {
    private int credits;
    private int dollars;
    private int rank;
    private Room room;

    public Player() {
        dollars = 0;
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