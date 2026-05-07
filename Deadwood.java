/*


*/

public class Deadwood {
    public static void main(String[] args) {

        Moderator moderator = new Moderator();
       
        View view = new view();

        Controller controller = new Controller();

        view.setController(controller);

    }
}