import java.util.ArrayList;
import org.w3c.dom.Document;

public class Moderator {
    private final static int MAXPLAYERS = 8;
    private int daysLeft;
    private String XMLBoardFile = "board.xml";
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public void endDay() {
        daysLeft--;
    }

    public int daysLeft() {
        return daysLeft;
    }

    public void setUpPlayerRules (int playerCount) {
        validatePlayerCount(playerCount);
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player());
        }
        calcAndSetPlayerStartRanks(playerCount);
        calcAndSetPlayerStartCredits(playerCount);
        calcAndSetStartDaysLeft(playerCount);
       // players.forEach((player) -> {
       // player.setRoom(/*TODO */});
     }

    private void validatePlayerCount(int playerCount) throws NumberFormatException{
        if ((playerCount < 1) || (playerCount > MAXPLAYERS)) {
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

    public void getAndSetRoomsFromXMLDoc() throws Exception{
        Document document = null;
        XMLParser xmlparser = new XMLParser();
        ArrayList<String> namesOfAllRooms;

        document = xmlparser.newBoardDoc(XMLBoardFile);
        namesOfAllRooms = xmlparser.retrieveLocationNames(document);
        createRooms(namesOfAllRooms);
    }

    private void createRooms(ArrayList<String> names) {
        String name;
        for (int i = 0; i < names.size(); i++) {
            name = names.get(i);

            switch(name) {
                case "trailer":
                    rooms.add(new Trailer("Trailers"));
                    break;
                case "office":
                    rooms.add(new CastingOffice("Casting Office"));
                    break;
                default:
                    rooms.add(new ActingSet(name));
                    break;
            }
        }
    }

    private void setRoomNeighbors() {}

    private void setActingSetTakes() {}

    private void setActingSetParts() {}

    private void setCastingOfficeUpgrade() {}


    
}