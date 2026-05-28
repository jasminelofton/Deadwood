// Entry point for the Deadwood board game application.
// Wires together the three MVC components and starts the game loop.
public class Deadwood {
    public static void main(String[] args) {
        // Model: owns all game state and rules.
        Moderator moderator = new Moderator();
        // View: handles all console I/O.
        View view = new View();
        // Controller: translates player input into model actions.
        Controller controller = new Controller(moderator, view);

        controller.startGame();
    }
}