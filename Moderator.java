import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import org.w3c.dom.Document;

// Moderator handles all the logic that has to do with the board 
// and player data and/or executing the actions of the entire game.
public class Moderator {
    private final static int MAXPLAYERS = 8;
    private int daysLeft;
    private String XMLBoardFile = "board.xml";
    private String XMLCardFile = "cards.xml";
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private Deck deck = new Deck();
    private Room specialTrailerRoom;
    private int currentPlayer = 0;

    public void endDay() {
        daysLeft--;
    }

    public int daysLeft() {
        return daysLeft;
    }

    public void endTurn () {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    // This method will completely set up all the necessary
    // player data that each player will need before beginning
    // Deadwood. Now, The data added to each player will be 
    // all the same, but, depending on the player count, 
    // adjustments will be made to the ranks, credits, and days
    // partaining to the game which the logic will all account for.
    public void setUpPlayerRules (int playerCount) {
        //check if the playerCount falls within the given limit for the game.
        validatePlayerCount(playerCount);
        //create all objects for the number of players.
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(specialTrailerRoom));
        }
        //set the data of all these players.
        calcAndSetPlayerStartRanks(playerCount);
        calcAndSetPlayerStartCredits(playerCount);
        calcAndSetStartDaysLeft(playerCount);
     }

    // Helper function to setUpPlayerRules.
    // The game must have at least 2 players but less than the maximum 
    // size. We check here, if its not valid, we throw an exception 
    // back to the controller.
    private void validatePlayerCount(int playerCount) throws NumberFormatException{
        if ((playerCount < 2) || (playerCount > MAXPLAYERS)) {
            throw new NumberFormatException();
        }
    }

    // Helper function to setUpPlayerRules.
    // The starting rank for all the players is dependent on the
    // amount of players that will be playing Deadwood. This 
    // method sets the ranks of all player objects.
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

    // Helper function to setUpPlayerRules.
    // The starting credits of every player is dependent to the 
    // size of the number of players playing. Each player object
    // gets set to the startCredits value.
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

    // Helper function to setUpPlayerRules.
    // A certain number of players will change the number
    // of days that the game is played for. This calculates 
    // and then sets the number of days that the game 
    // begins with.
    private void calcAndSetStartDaysLeft(int playerCount) {
        daysLeft = switch (playerCount) {
            case 2, 3 -> 3;
            default -> 4;
        };
    }

    // Setting all of the board is important. The main task to
    // set the board is to set all of the rooms that players 
    // will use. The data of each room is given as an XML file.
    // The XML file is parsed in XMLParser.java. This method will
    // completely set all the rooms using helper functions.
    public void getAndSetRoomsFromXMLDoc() throws Exception{
        XMLParser xmlparser = new XMLParser();
        Document document = xmlparser.newBoardDoc(XMLBoardFile);

        createRooms(xmlparser, document);
        setRoomsNeighbors(xmlparser, document);
        setActingSetTakes(xmlparser, document);
        setActingSetParts(xmlparser, document);

        // setCastingOfficeUpgrade(xmlparser, document);
    }

    // Helper function to getAndSetRoomsFromXMLDoc.
    // Creates room object subclasses of either trailer, casting office,
    // or an acting set. All room objects are added to the rooms
    // arraylist. Note: This only creates and names a room.
    private void createRooms(XMLParser xmlparser, Document document) throws Exception{
        String name;
        ArrayList<String> namesOfAllRooms = xmlparser.retrieveLocationNames(document);

        for (int i = 0; i < namesOfAllRooms.size(); i++) {
            name = namesOfAllRooms.get(i);

            switch(name) {
                case "trailer":
                    rooms.add(new Trailer("Trailers"));
                    specialTrailerRoom = rooms.getLast();
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

    private void setRoomsNeighbors(XMLParser xmlparser, Document document) throws Exception {
        ArrayList<String> neighbors = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
          neighbors = xmlparser.retrieveNeighborsNames(document, rooms.get(i).getName());
          rooms.get(i).setNeighbors(neighbors);
        }
    }

    private void setActingSetTakes(XMLParser xmlparser, Document document) throws Exception {
         ArrayList<String> takes = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getName() != "Casting Office" && rooms.get(i).getName() != "Trailers") {
                takes = xmlparser.retrieveActingSetTakes(document, rooms.get(i).getName());
                ((ActingSet)rooms.get(i)).setTakes(takes);
            }
        }       
    }

    // The name is parts because the board.xml defines each role information as such, but within the 
    // game, these parts will be defined by the name role(s).
    private void setActingSetParts(XMLParser xmlparser, Document document) throws Exception {
           ArrayList<String> parts = new ArrayList<>();
           ArrayList<Integer> levels = new ArrayList<>();
           ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getName() != "Casting Office" && rooms.get(i).getName() != "Trailers") {
                parts = xmlparser.retrieveActingSetParts(document, rooms.get(i).getName(), "name");
                levels = xmlparser.retrieveActingSetPartsAsIntegers(document, rooms.get(i).getName(), "level");
                lines = xmlparser.retrieveActingSetParts(document, rooms.get(i).getName(), "line");
                ((ActingSet)rooms.get(i)).setRoles(parts, levels, lines);
            }
        }             
    }

    private void setCastingOfficeUpgrade(XMLParser xmlparser, Document document) throws Exception {
        ArrayList<String> levels = new ArrayList<>();
        ArrayList<String> currencies = new ArrayList<>();
        ArrayList<String> amts = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getName().equals("Casting Office")) {
                levels = xmlparser.retrieveCastingOfficeParts(document, rooms.get(i).getName(), "level");
                currencies = xmlparser.retrieveCastingOfficeParts(document, rooms.get(i).getName(), "currency");
                amts = xmlparser.retrieveCastingOfficeParts(document, rooms.get(i).getName(), "amt");

                ((CastingOffice)rooms.get(i)).setUpgrades(levels, currencies, amts);
            }
        }       

    }

    public void getAndSetDeckFromXMLDoc() throws Exception {
        XMLParser xmlParser = new XMLParser();
        Document document = xmlParser.newCardsDoc(XMLCardFile);
        
        ArrayList<SceneCard> sceneCards = xmlParser.parseCards(document);
        deck.setCards(sceneCards);
    }

    public void dealSceneCards() {
        deck.shuffle();

        for (Room room : rooms) {
            if (room instanceof ActingSet set) {
                set.setSceneCard(deck.dealCard());
            }
        }
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayerNum() {
        return currentPlayer;
    }

    private void wrapScene(ActingSet set) {

    }

    public void handleAct(Player player) {

        Dice dice = new Dice();

        Room currentRoom = player.getRoom();
        Role currentRole = player.getRole();
        
        ActingSet actingSet = (ActingSet) currentRoom;
        SceneCard sceneCard = actingSet.getSceneCard();
        int budget = sceneCard.getBudget();

        
        int totalRoll = dice.roll(1) + player.getRehearsalBonus(currentRole);

        
        boolean success = (totalRoll >= budget);
        
        if (currentRole.isOnCard()) {
            if (success) {
                actingSet.removeShotCounter();
                player.addCredits(2);
            }
        } else {
            if (success) {
                actingSet.removeShotCounter();
                player.addDollars(1);
                player.addCredits(1);
            } else {
                player.addDollars(1);
            }
        }
        
        if (actingSet.getShotCounter() == 0) {
            wrapScene(actingSet);
        }
        
        player.clearRehearsalBonus(currentRole);
    }

    public void handleTakeRole(Player player, Role role) {

        if (player.getRank() < role.getLevel()) {
            throw new IllegalArgumentException("Player rank too low for this role");
        }
        
        if (!role.getAvailable()) {
            throw new IllegalStateException("Role is taken");
        }
        
        player.setRole(role);
        role.setAvailable(false);
    }

    
}