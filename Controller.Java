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

    public Controller (Moderator moderator, View view) {
        this.moderator = moderator;
        this.view = view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setModerator(Moderator moderator) {
        this.moderator = moderator;
    }

    public void handlePlayerCountInput() {
        int playerCount;
        boolean success;

        String requestInputFromView = """
            Type the number of players playing today\n
             (Note: Must be between 1-8 players)\n
            """;

        String invalidInputFromView = """
            Not a valid number of players\n 
            """;

        success = true;
        while(success) {
            view.printStatement(requestInputFromView);

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


    }


    

}