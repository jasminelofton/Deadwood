import java.util.Scanner;

// Program does not end until view ends program.
public class View {
    Scanner scan = new Scanner(System.in);
    Controller controller;

    private boolean continueDeterminant;

    public void View() {
        continueDeterminant = true
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setContinue(boolean bool) {
        continueDeterminant = bool;
    }

    public void introductionary() {
        System.out.println(introduction);
    }
// get the number of players
    public String requestPlayers() {
        System.out.println();
        //ask for players
        return "";
    }

//Ask for input
    public void startGame() {
        String input;
        String output;

        introductionary();
        requestPlayers();
        while (continueDeterminant) {
            System.out.println(menuList);
            input = scan.nextLine();
            output = controller.handleInput(input);
            System.out.println(output);
        }
    }

    public void printStatement (String statement) {
        System.out.println(statement);
    }
}