import java.awt.event.*;
import java.util.*;

// The controller acts as the connection between the view and the model of this program.
//     The controller will... 
//     1. The controller recieves input from view class.
//     2. The controller then transcribes the input.
//     3. The controller now will call the appropriate model method.

//        The controller : Controller.Java
//        The view class : situational
//        The model class : Moderator.Java

public class Controller {
    private final BoardLayersListener view;
    private final Moderator moderator;
    boolean completedFirstStepAction = false;

    public Controller (Moderator moderator, BoardLayersListener view) {
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
             (Note: Must be between 2-8 players)
            """;

        String invalidInputFromView = """ 
            Invalid number of players. Please try again.
             (Note: Must be between 2-8 players) 
            """ ;

        String validInputFromView ="""
            Sounds good! Let's get started.
            """;

        success = true;

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
                playerCount = Integer.parseInt(view.AskForStatement(requestInputFromView)); 
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

    // Loads cards.xml into the deck and then deals one card to every ActingSet.
    // Exits the program if the card file cannot be parsed.
    private void dealSceneCards() {
        try {
            moderator.getAndSetDeckFromXMLDoc();
        }
        catch (Exception e) {
            view.printStatement("Error fetching cards cards");
            System.exit(0);
        }

        moderator.dealSceneCards();

        ArrayList<Room> rooms = moderator.getRooms();
        for (Room room : rooms) {
            if (room instanceof ActingSet) {
                ActingSet actingSet = (ActingSet) room;
                
                if (actingSet.getSceneCard() != null) {
                    view.addSceneCard(actingSet);
                    view.addShotCounters(actingSet);
                }
            }
        }
    }

    // Builds a summary string of every player's location except the current player's.
    // Used to display opponent positions at the start of each turn.
    private String otherPlayersInfo() {
        StringBuilder otherPlayersInfo = new StringBuilder("Other players info: ");
        ArrayList<Player> players = moderator.getPlayers();
        int currentPlayerNum = moderator.getCurrentPlayerNum();

        for (int i = 0; i < players.size(); i++) {
            if (i != currentPlayerNum) {
                Player player = players.get(i);
                otherPlayersInfo.append("[Player " + (i+1) + "'s Location: " + player.getRoom().getName() + "] ");
            }
        }
        otherPlayersInfo.append("\n");
        return otherPlayersInfo.toString();
    }

    // Builds a one-line status string for the current player (rank, dollars, credits, location, role status).
    private String playerInfo() {
        Player currentPlayer = moderator.getCurrentPlayer();

        return "Player " + (moderator.getCurrentPlayerNum()+1) + " info: [" +
                "Rank: " + currentPlayer.getRank() + ", " +
                "Dollars: " + currentPlayer.getDollars() + ", " +
                "Credits: " + currentPlayer.getCredits() + ", " +
                "Location: " + currentPlayer.getRoom().getName() + ", " +
                "Working: " + currentPlayer.hasRole() + "]\n";
    }

    // Runs the interactive menu loop for one player's turn.
    // Each turn allows exactly one "first step" action (move, act, or rehearse),
    // plus any number of optional actions (take role, upgrade) before ending the turn.
    // The loop exits when the player chooses 'e' (end turn) or 'q' (quit).
    private void handlePlayerTurnInput(String input) {

        switch (input) {
            case "m":
                if (completedFirstStepAction) {
                    view.printStatement("You already made a move\n");
                    break;
                }
                if (handleMove()) {
                    completedFirstStepAction = true;
                    refreshDashboard();
                }
                break;
            case "a":
                if (completedFirstStepAction) {
                    view.printStatement("You already made a move\n");
                    break;
                }
                if (handleAct()) {
                    completedFirstStepAction = true;
                    refreshDashboard();
                }
                break;
            case "t":
                // Taking a role is allowed at any point during a turn (not a first-step action).
                handleTakeRole();
                refreshDashboard();
                break;
            case "r":
                if (completedFirstStepAction) {
                    view.printStatement("You already made a move\n");
                    break;
                }
                if (handleRehearse()) {
                    completedFirstStepAction = true;
                    refreshDashboard();
                }
                break;
            case "u":
                // Upgrading is an optional action and does not count as the first-step action.
                handleUpgrade();
                refreshDashboard();
                break;
            case "e":
                // End turn — advance to the next player.
                completedFirstStepAction = false;
                moderator.endTurn();

                if (moderator.checkEndOfDay() == true) {
                    view.printStatement("The day has ended. Players back to trailers.");
                    break;
                }

                if (moderator.daysLeft() == 0) {
                    view.printStatement("The game has ended. No days left.");
                    view.printStatement("Player " + moderator.calculateWinner() + " wins!");
                    System.exit(0);                
                }
                return;
            case "q":
                System.exit(0);
            default:
                view.printStatement("Invalid input, please try again. \n");
        }
    }

    // Prompts the current player to pick a neighboring room to move to.
    // Blocks movement if the player is currently working a role.
    // Loops until a valid room index is entered; returns true on a successful move.
    private boolean handleMove() {
        ArrayList<Room> rooms;
        Player currentPlayer;
        String inputString;
        int inputInt;
        Room destination;

        rooms = moderator.getRooms();
        
        currentPlayer = moderator.getCurrentPlayer();

        if (currentPlayer.hasRole()) {
            view.printStatement("You cannot move while working on a role. \n");
            return false;
        }

        // Print the full room list with indices so the player can pick by number.  
        StringBuilder roomNames = new StringBuilder("");

        for (int i = 0; i < rooms.size(); i++) {
            roomNames.append("[" + i + "] " + rooms.get(i).getName() + "\n");
        }
        String availableRooms = "Player " + (moderator.getCurrentPlayerNum()+1) + " can move to: " + currentPlayer.getRoom().getNeighbors().toString() + "\n";
        while (true) {

            inputString = view.AskForStatement(roomNames + availableRooms + "Pick a room:");

            try {
                inputInt = Integer.parseInt(inputString);

                if (inputInt < 0 || inputInt >= rooms.size())
                    throw new NumberFormatException("wrong number \n");

                // Validate that the chosen room is actually adjacent to the player's current room.
                boolean isRoomANeighbor = currentPlayer.getRoom().getNeighbors().contains(rooms.get(inputInt).getName());

                if (!isRoomANeighbor)
                    throw new IllegalArgumentException("not a neighbor \n");

                destination = rooms.get(inputInt);

                currentPlayer.setRoom(rooms.get(inputInt));
                view.printStatement(("moved to " + rooms.get(inputInt).getName())  + "\n");
                // if (rooms.get(inputInt) instanceof ActingSet set) {
                //     view.printStatement("Scene Info " + set.getSceneCard().toString());
                // }
                break;
            }
            catch (NumberFormatException e) {
                view.printStatement(e.getMessage());
            }
            catch (IllegalArgumentException e) {
                view.printStatement(e.getMessage());
            }
        }
        int x = destination.getX();
        int y = destination.getY();
        view.movePlayerToken(currentPlayer.getId(), x, y);

        if (destination instanceof ActingSet) {

            SceneCard card = ((ActingSet)destination).getSceneCard(); 

            if (((ActingSet)destination).getPlayerCount() <= 0) {
                view.revealSceneCard((ActingSet) destination, card);
            }
        }

        return true;
    }

    // Delegates an act attempt to the moderator.
    // Returns false (without consuming the first-step action) if the player has no role.
    private boolean handleAct() {
        Player currentPlayer = moderator.getCurrentPlayer();

        if (!currentPlayer.hasRole()) {
            view.printStatement("must be working on a role to act. \n");
            return false;
        }

        try {
            boolean success = moderator.handleAct(currentPlayer);
            boolean wrapScene = moderator.checkWrapScene(currentPlayer);

            if (success) {
                view.printStatement("Success! Your acting roll succeeded. A shot counter has been removed!");
            } else {
                view.printStatement("Failure! Your acting roll was too low. Better luck next time!");
            }

            view.removerShotCounter((ActingSet)currentPlayer.getRoom());

            if (wrapScene) {
                view.removeSceneCard((ActingSet)currentPlayer.getRoom());
            }
            return true;
        } catch (Exception e) {
            view.printStatement("Error while attempting to act " + e.getMessage()  + "\n");
            return false;
        }
    }

    // Presents available roles on the current ActingSet and lets the player pick one.
    // Validates that the player is on an ActingSet, doesn't already have a role,
    // and that the chosen role index is in bounds before calling the moderator.
    private void handleTakeRole() {
        Player currentPlayer = moderator.getCurrentPlayer();
        Room currentRoom = currentPlayer.getRoom();

        if (currentPlayer.hasRole()) {
            view.printStatement("You have already taken another role \n");
            return;
        }

        if (!(currentRoom instanceof ActingSet)) {
            view.printStatement("You must be on an acting set to take a role \n");
            return;
        }

        ActingSet actingSet = (ActingSet) currentRoom;
        ArrayList<Role> availableRoles = actingSet.getAllOpenRoles(currentPlayer.getRank());

        if (availableRoles.isEmpty()) {
            view.printStatement("no open roles for your rank. \n");
            return;
        }

        view.printStatement("Available roles:\n");
        for (int i = 0; i < availableRoles.size(); i++) {
            Role role = availableRoles.get(i);
            view.printStatement(i + " " + role.getPart() + " (Rank " + role.getLevel() + " " + (role.isOnCard() ? "On Card" : "Off Card") + ")\n");
        }

        String inputString = view.AskForStatement("Please select a role");

        try {
            int inputInt = Integer.parseInt(inputString);

            if (inputInt < 0 || inputInt >= availableRoles.size()) {
                throw new NumberFormatException("Invalid role number");
            }

            Role selectedRole = availableRoles.get(inputInt);
            moderator.handleTakeRole(currentPlayer, selectedRole);



            view.takeRole(currentPlayer.getId(), selectedRole, currentRoom.area);
            view.printStatement("You have taken role: " + selectedRole.getPart()  + "\n");

        } catch (NumberFormatException e) {
            view.printStatement("Invalid input \n");
        } catch (Exception e) {
            view.printStatement("Error occured taking role " + e.getMessage()  + "\n");
        }
    }

    // Adds one rehearsal chip to the current player's role via the moderator.
    // Returns false if the player has no role or if they already guarantee success.
    private boolean handleRehearse() {
        Player currentPlayer = moderator.getCurrentPlayer();

        if (!currentPlayer.hasRole()) {
            view.printStatement("you must be working on a role to rehearse. \n");
            return false;
        }

        try {
            moderator.handleRehearse(currentPlayer);
            view.printStatement("you rehearsed, add a practice chip. \n");
        } catch (Exception e) {
            view.printStatement("error while rehearsing:  " + e.getMessage()  + "\n");
            return false;
        }
        return true;
    }

    // Handles the rank upgrade flow inside the Casting Office.
    // The player must be in the Casting Office room; otherwise an error is shown.
    // Loops showing the upgrade menu until the player selects option 5 (leave).
    // Menu options 0–4 map to ranks 2–6 (option index + 2 = rank).
    // Player chooses to pay in dollars ('d') or credits ('c') and the cost is validated
    // before calling the moderator to apply the upgrade.
    private void handleUpgrade() {
            Player currPlayer;
            CastingOffice cO;
            String inputString;
            cO = moderator.getCastingOffice();
            currPlayer = moderator.getCurrentPlayer();

            // Guard: player must be physically in the Casting Office to upgrade.
            try {
                if (!currPlayer.getRoom().getName().equals(cO.getName()))
                    throw new IllegalStateException("Location " + currPlayer.getRoom().getName() + " is not " + cO.getName());
            } catch (Exception e) {
                view.printStatement(e.getMessage());
                return;
            }

            int input;
            while (true) {
                try {
                    String message = CastingOffice_WelcomeMessage(cO.getRanks(), cO.getMoneyPrices(), cO.getCreditPrices(), currPlayer.getDollars(), currPlayer.getCredits());

                    input = Integer.parseInt(view.AskForStatement(message));

                    // Option 5 exits the upgrade menu without making a purchase.
                    if (input == 5) {
                        view.printStatement("Leaving the Casting Office front desk.");
                        return;
                    }

                    // Menu indices 0–4 correspond to ranks 2–6 (add 2 to get the rank value).
                    int requestedRank = input += 2;

                    if (!cO.getRanks().contains(requestedRank))
                        throw new IllegalArgumentException(input + " is not a viable option.");

                    inputString = (view.AskForStatement("In Dollars Or Credits?\n[d]\n[c]")).toLowerCase();

                    if (inputString.contains("d")) {
                        if (currPlayer.getDollars() < cO.getMoneyCost(requestedRank))
                            throw new IllegalArgumentException(currPlayer.getDollars() + " is not enough dollars.\n");

                        moderator.playerUpgraded(requestedRank, cO.getMoneyCost(input), 'd');
                    } else if (inputString.contains("c")) {
                        if (currPlayer.getCredits() < cO.getCreditCost(input))
                            throw new IllegalArgumentException(currPlayer.getCredits() + " is not enough credits.\n");

                        moderator.playerUpgraded(requestedRank, cO.getCreditCost(input), 'c');
                    } else {
                        throw new IllegalArgumentException(input + " is not a viable d or c.\n");
                    }

                    view.printStatement("Congratulations. Rank upgraded to " + requestedRank + ".");

                } catch (Exception e) {
                    view.printStatement(e.getMessage());
                }
            }
        }

    // Displays the Casting Office upgrade menu showing available ranks and their costs,
    // as well as the player's current dollar and credit balances.
    // Prints an error and returns early if the pricing lists have mismatched sizes.
    public String CastingOffice_WelcomeMessage(List<Integer> rank, List<Integer> dollars,
        List<Integer> credits, int playerDollars, int playerCredits) {

        String welcomeMessage;
        StringBuilder pricing = new StringBuilder();

        welcomeMessage = String.format("""
            Welcome to the casting office front desk,
            You can exchange money or credits for an upgrade in rank.
            You hold:
            Dollars: %d
            Credits: %d
            Exchange for rank or leave the casting office. Thank you.
            """, playerDollars, playerCredits);

        pricing.append(welcomeMessage);
            
        pricing.append("""
            The Prices to Upgrade are as follows.\n
            [ ] Rank | Dollars | Credits\n
            """);

        // Guard against mismatched pricing data before attempting to print the table.
        if (dollars.size() != credits.size() || credits.size() != rank.size()) {
            view.printStatement(
                "Dollars contains " + dollars.size() + "elements but credits contains " +
                credits.size() + " elements and rank contains " + rank.size() + " elements");
            return "mismatched input data";
        }

        for (int i = 0; i < dollars.size(); i++) {
            pricing.append(String.format("[%d] %2d %8d %8d\n", i, rank.get(i), dollars.get(i),
            credits.get(i)));
        }
        pricing.append("[5] leave Casting Office desk.\n");
        pricing.append("choose a number");
        return pricing.toString();
    }



    // This function will be called in main, this function begins 
    // the actual game, starting with initializing the board, 
    // setting up players, all the way to ending the game and closing
    // it out.
    public void startGame() {
        view.printStatement("Welcome to Deadwood!");

        roomSetUp();

        dealSceneCards();

        handlePlayerCountInput();
        
        view.bTakeRole.addMouseListener(new boardMouseListener());
        view.bAct.addMouseListener(new boardMouseListener());
        view.bMove.addMouseListener(new boardMouseListener());
        view.bUpgrade.addMouseListener(new boardMouseListener());
        view.bRehearse.addMouseListener(new boardMouseListener());
        view.bEndTurn.addMouseListener(new boardMouseListener());

        String[] colors = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
    
        ArrayList<Player> players = moderator.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            
            p.setColor(colors[i]); 
            
            int startX = p.getRoom().getX() + (i * 15); 
            int startY = p.getRoom().getY();

            
            // System.out.println(startY);

            String dieAssetFile = "dice/" + p.getColor() + p.getRank() + ".png"; 
            p.setImg(dieAssetFile);
            
            view.addPlayerToken(i, dieAssetFile, startX, startY);
        }

        view.updatePlayerInfo(playerInfo() + otherPlayersInfo());


        // while (true) { //game

        //     while (true) { //day

        //             view.printStatement("Player " + (moderator.getCurrentPlayerNum()+1) + "'s turn.\n");

        //             handlePlayerTurnInput();
        //             if (moderator.checkEndOfDay() == true) {
        //                 view.printStatement("The day has ended. Players back to trailers.");
        //                 break;
        //             }
        //     }
        //     if (moderator.daysLeft() == 0) {
        //         view.printStatement("The game has ended. No days left.");
        //         break;
        //     }
        // }
        // view.printStatement("Player " + moderator.calculateWinner() + " wins!");
    }

    private void refreshDashboard() {
        Player current = moderator.getCurrentPlayer();
        int playerNum = moderator.getCurrentPlayerNum() + 1;
        
        boolean onSet = current.getRoom() instanceof ActingSet;
        int budget = 0;
        boolean isRevealed = true;
        
        if (onSet) {
            ActingSet actingSet = (ActingSet) current.getRoom();
            if (actingSet.getSceneCard() != null) {
                budget = actingSet.getSceneCard().getBudget();
            }
        }

        StringBuilder availableMoves = new StringBuilder();

        if (completedFirstStepAction) {
            availableMoves.append("[End Turn]");

            if (onSet && !current.hasRole()) {
                availableMoves.append("[Take Role] ");
            }
        } else {
            if (current.hasRole()) {
                availableMoves.append("[Act] [Rehearse] [End Turn]");
            } else {
                availableMoves.append("[Move] ");
                if (onSet) {
                    availableMoves.append("[Take Role] ");
                }
                availableMoves.append("[End Turn]");
            }
        }

        PlayerDTO dto = new PlayerDTO(
            playerNum,
            current.getImg(),
            current.getRank(),
            current.getDollars(),
            current.getCredits(),
            onSet,
            current.getRehearsalBonus(current.getRole()),
            budget,
            isRevealed,
            availableMoves.toString()
        );

        view.updatePlayerInfo(dto);
    }


  // This class implements Mouse Events
  class boardMouseListener implements MouseListener{
    
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== view.bAct){
            handlePlayerTurnInput("a");
         }
         else if (e.getSource()== view.bRehearse){
            handlePlayerTurnInput("r");
         }
         else if (e.getSource()== view.bMove){
            handlePlayerTurnInput("m");
         }
         else if (e.getSource()== view.bUpgrade){
            handlePlayerTurnInput("u");
         }
         else if (e.getSource()== view.bEndTurn){
            handlePlayerTurnInput("e");
         }
         else if (e.getSource() == view.bTakeRole) {
            handleTakeRole();
         }

         view.updatePlayerInfo(playerInfo() + otherPlayersInfo());
         refreshDashboard();
      }

      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
   }
}