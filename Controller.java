import java.util.Scanner;

/*
    The controller acts as the connection between the view and the model of this program.
    The controller will... 
    1. The controller recieves input from view class.
    2. The controller then transcribes the input.
    3. The controller now will call the appropriate model method.

       The controller : Controller.Java
       The view class : View.Java
       The model class : Moderator.Java
*/
public class Controller {
    private View view;
    private Moderator moderator;
    private final Scanner scan = new Scanner(System.in);

    final static String introduction = 
    """
    Welcome to Deadwood!
    """;

    final static String ANSI_GREEN = "\u001B[32m";
    final static String ANSI_RED = "\u001B[31m";
    final static String ANSI_RESET = "\u001B[0m";

    final static String menuList = 
    """
    [m] move a step\n
    [a] act\n
    [t] take a role\n
    [r] rehearse\n
    [n] nothing\n
    [u] upgrade\n
    [e] end turn
    """;


    public Controller (Moderator moderator, View view) {
        this.moderator = moderator;
        this.view = view;
    }

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
            while(success) {


            //change string into an integer
            try {
                playerCount = Integer.parseInt(scan.nextLine()); 
                moderator.setUpPlayerRules(playerCount);
                success = false;
            } 
            catch (NumberFormatException e) {
                view.printStatement(invalidInputFromView);
            }
            catch (IllegalArgumentException e) {
                view.printStatement(invalidInputFromView);
            }
        }
        view.printStatement(validInputFromView);
    }

    public void startGame() {
        view.printStatement(introduction);
        handlePlayerCountInput();
        view.printStatement(menuList);
    }

    

}