import java.util.Scanner;

// Program does not end until view ends program.
public class View {
    Scanner scan = new Scanner(System.in);

    public void printStatement (String statement) {
        System.out.println(statement);
    }

    public String AskForStatement() {
        return scan.nextLine();
    }
}