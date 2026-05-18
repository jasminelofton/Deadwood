import java.util.*;
// Program does not end until view ends program.
public class View {
    Scanner scan = new Scanner(System.in);
    private final String chooseChoice = "choose a number";

    // The welcome message to enter the casting office.
    public void CastingOffice_WelcomeMessage(List<Integer> rank, List<Integer> dollars, 
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

        pricing.append("""
            The Prices to Upgrade are as follows.\n
            [ ] Rank | Dollars | Credits\n
            """);

        if (dollars.size() != credits.size() && credits.size() != rank.size()) {
            System.out.println(
                "Dollars contains " + dollars.size() + "elements but credits contains " + 
                credits.size() + " elements and rank contains " + rank.size() + " elements");
            return;
        }

        System.out.println(welcomeMessage);

        for (int i = 0; i < dollars.size(); i++) {
            pricing.append(String.format("[%d] %2d %8d %8d\n", i, rank.get(i), dollars.get(i), 
            credits.get(i)));
        }
        pricing.append("[5] leave Casting Office desk.\n");

        System.out.println(pricing);

        System.out.println(chooseChoice);
    }

    public void CastingOffice_DollarsOrCredits() {
        System.out.println("In Dollars Or Credits?\n[d]\n[c]");
    }

    // Whether their upgraded choice could be processed based on their credits or dollars.
    public void CastingOffice_Choice(int rank) {
            System.out.println("Congratulations. Rank upgraded to " + rank + ".");
            return;
    }

    // The player is leaving the casting office terminal.
    public void CastingOffice_Leaving() {
        System.out.println("Leaving the Casting Office front desk.");
    }


    public void printStatement (String statement) {
        System.out.println(statement);
    }

    public String AskForStatement() {
        return scan.nextLine();
    }
}