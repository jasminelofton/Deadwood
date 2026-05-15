import java.util.ArrayList;
// The controller acts as the connection between the view and the model of this program.
//     The controller will... 
//     1. The controller recieves input from view class.
//     2. The controller then transcribes the input.
//     3. The controller now will call the appropriate model method.

//        The controller : Controller.Java
//        The view class : View.Java
//        The model class : Moderator.Java

public class Controller {
    private final View view;
    private final Moderator moderator;

    private final String introduction = 
    """
    Welcome to Deadwood!
    """;

    final static String ANSI_GREEN = "\u001B[32m";
    final static String ANSI_RED = "\u001B[31m";
    final static String ANSI_RESET = "\u001B[0m";

    private final String menuList = 
    """
    [m] move a step\n
    [a] act\n
    [t] take a role\n
    [r] rehearse\n
    [n] nothing\n
    [u] upgrade\n
    [e] end turn\n
    [q] quit game
    """;


    public Controller (Moderator moderator, View view) {
        this.moderator = moderator;
        this.view = view;
    }

    // All the tasks of requesting, retrieving, setting, and 
    // comfirming the player size for the Deadwood game will be 
    // accomplished here.  
    public void handlePlayerCountInput() {
        int playerCount;
        boolean success;

        String requestInputFromView = """
            Type the number of players playing today.
             (Note: Must be between 1-8 players)
            """;

        String invalidInputFromView = ANSI_RED + """ 
            Invalid number of players. Please try again.
             (Note: Must be between 1-8 players) 
            """ + ANSI_RESET;

        String validInputFromView = ANSI_GREEN + """
            Sounds good! Let's get started.
            """ + ANSI_RESET;

        success = true;
        view.printStatement(requestInputFromView);

        // This will request call the view to request input from the 
        // user for as many loops as needed until a valid input is 
        // finally given from the view. A valid input being an 
        // integer value within the minimum and maximum size limit
        // for the Deadwood game.        
        while(success) {

            //This will attempt to change a string given by the view
            // (this is the users input) into an integer that fits within
            // the parameters of the minimum number of players and 
            // maximum number of players elegible to play this game.
            try {
                playerCount = Integer.parseInt(view.AskForStatement()); 
                moderator.setUpPlayerRules(playerCount);
                success = false;
            } 

            // number does not fit within the limit size 
            // of the number of players that are allowed
            // to play this game.
            catch (NumberFormatException e) {
                view.printStatement(invalidInputFromView);
            }

            // Upon the conversion from a string to an integer, the
            // program is unable to complete the conversion as 
            // the given input from the view(user input) cannot be 
            // converted into a number.
            catch (IllegalArgumentException e) {
                view.printStatement(invalidInputFromView);
            }
        }
        view.printStatement(validInputFromView);
    }

    // All the logic for communicating with the moderator to set
    // up the rooms on the board will be accomplished in this 
    // function. Confirmation messages will be sent out to the view
    // before and after the rooms have been set successfully. This
    // function will completely end the program if the board cannot
    // get initialized (likely due to the xml file failing to either
    // retrieve or parse according to the logic within moderator).
    private void roomSetUp() {
        String boardFailure = 
        """
        Failure to set up board
        """;
        String settingUp = 
        """
        Setting up Board...
        """;
        String complete = 
        """
        Setup complete.
        """;

        view.printStatement(settingUp);
        try {
            moderator.getAndSetRoomsFromXMLDoc();
            view.printStatement(complete);
        }
        catch (Exception e) {
            view.printStatement(boardFailure + e);
            System.exit(1);
        }
    }

    private String otherPlayersInfo() {
        StringBuilder otherPlayersInfo = new StringBuilder("Other players info: ");
        ArrayList<Player> players = moderator.getPlayers();
        int currentPlayerNum = moderator.getCurrentPlayerNum();

        for (int i = 0; i < players.size(); i++) {
            if (i != currentPlayerNum) {
                Player player = players.get(i);
                otherPlayersInfo.append("[Player " + (i+1)  + "'s Location: " + player.getRoom().getName() + "] ");

            }
        }
        otherPlayersInfo.append("\n");
        return otherPlayersInfo.toString();
    }

    private String playerInfo () {
        Player currentPlayer = moderator.getCurrentPlayer();

        return "Player " + (moderator.getCurrentPlayerNum()+1) + " info: [" +
                "Rank: " + currentPlayer.getRank() + ", " +
                "Dollars: " + currentPlayer.getDollars() + ", " +
                "Credits: " + currentPlayer.getCredits() + ", " +
                "Location: " + currentPlayer.getRoom().getName() + ", " +
                "Working: " + currentPlayer.isWorking() + "]\n";
    }
    private void handlePlayerTurnInput() {
        String input;
        boolean completedFirstStepAction = false;
        
        while (true) {
            view.printStatement(playerInfo());
            view.printStatement(otherPlayersInfo());
            view.printStatement(menuList);
            input = view.AskForStatement();            
            
            switch(input) {
                    case "m":
                        // move
                        if (completedFirstStepAction) {
                            view.printStatement(ANSI_RED + "You already moved/acted/rehearsed" + ANSI_RESET + "\n");
                             break;
                        }
                        if (handleMove()) {
                            completedFirstStepAction = true;
                        }
                        break;
                    case "a":

                        // act
                        break;
                    case "t":
                        // take a role, (opt)second step action
                        break;
                    case "r":

                        // rehearse
                        break;
                    case "u":
                        // upgrade, (opt) second step action
                        break;
                    case "e":
                        // end turn, (opt)second step action
                        moderator.endTurn();
                        return;
                    case "q":
                        // quit game, (opt) second step action
                        System.exit(0);
                    default:
                        view.printStatement(ANSI_RED + "Invalid input, please try again." + ANSI_RESET + "\n");
                        handlePlayerTurnInput();
            }
        }
    }

    private boolean handleMove() {
        // move
        ArrayList<Room> rooms;
        Player currentPlayer;
        String inputString;
        int inputInt;

        rooms = moderator.getRooms();
        currentPlayer = moderator.getCurrentPlayer();

        // Player mustn't be working on a set to move.
        if (currentPlayer.isWorking()) {
            view.printStatement(ANSI_RED + "You cannot move while working on a role." + ANSI_RESET + "\n");
            return false;
        }

        // continually ask for input until user picks a valid number.
        while (true) {
            
            // prints all rooms to view
            for (int i = 0; i < rooms.size(); i++) {
                view.printStatement("[" + i + "] " + rooms.get(i).getName() + "\n");
            }

            view.printStatement("Player " + (moderator.getCurrentPlayerNum()+1) + " can move to: " + currentPlayer.getRoom().getNeighbors().toString());
            view.printStatement("Pick a room:"); 

            inputString = view.AskForStatement();

            try {
                inputInt = Integer.parseInt(inputString);

                if (inputInt < 0 || inputInt >= rooms.size()) throw new NumberFormatException(ANSI_RED + "wrong number" + ANSI_RESET + "\n");

                // Checks if room is a neighbor of the players current location.
                boolean isRoomANeighbor =currentPlayer.getRoom().getNeighbors().contains(rooms.get(inputInt).getName());

                if (!isRoomANeighbor) throw new IllegalArgumentException(ANSI_RED + "not a neighbor" + ANSI_RESET + "\n");
                    currentPlayer.setRoom(rooms.get(inputInt));
                    view.printStatement((ANSI_GREEN +"moved to " + rooms.get(inputInt).getName()) + ANSI_RESET + "\n");    
                    break;            
            } 

            catch (NumberFormatException e) {
                view.printStatement(e.getMessage());
            }

            catch (IllegalArgumentException e) {
                view.printStatement(e.getMessage());
            }
        }



        //successful move
        return true;
    }

    private void handleAct() {
        // act
    }

    private void handleTakeRole() {
        // take a role
    }

    private void handleRehearse() {
        // rehearse
    }

    private void handleUpgrade() {
        // upgrade
    }

    // This function will be called in main, this function begins 
    // the actual game, starting with initializing the board, 
    // setting up players, all the way to ending the game and closing
    // it out.
    public void startGame() {
        view.printStatement(introduction);
        roomSetUp();
        handlePlayerCountInput();

        while (true) { //game
            while (true) { //day
                    view.printStatement("Player " + (moderator.getCurrentPlayerNum()+1) + "'s turn.\n");
                    handlePlayerTurnInput();
            }
        }
    }

}