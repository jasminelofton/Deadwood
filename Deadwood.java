/*


*/public class Deadwood {
    public static void main(String[] args) {

        Moderator moderator = new Moderator();
        View view = new View();
        Controller controller = new Controller(moderator, view);

        view.setController(controller);
        moderator.setController(controller);
        
        controller.startGame();
    }
}