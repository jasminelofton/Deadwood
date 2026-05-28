import java.util.*;

// View is the presentation layer of the MVC pattern.
// All output to the player goes through this class and all raw input is read here.
// The program runs until the user quits via the Controller — View itself never exits.
public class View {
    Scanner scan = new Scanner(System.in);
    private final String chooseChoice = "choose a number";

    // Displays the Casting Office upgrade menu showing available ranks and their costs,
    // as well as the player's current dollar and credit balances.
    // Prints an error and returns early if the pricing lists have mismatched sizes.
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

        // Guard against mismatched pricing data before attempting to print the table.
        if (dollars.size() != credits.size() || credits.size() != rank.size()) {
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

    // Prompts the player to choose between paying in dollars or credits.
    public void CastingOffice_DollarsOrCredits() {
        System.out.println("In Dollars Or Credits?\n[d]\n[c]");
    }

    // Confirms that the rank upgrade was successfully processed.
    public void CastingOffice_Choice(int rank) {
        System.out.println("Congratulations. Rank upgraded to " + rank + ".");
    }

    // Displays a message when the player leaves the Casting Office upgrade menu.
    public void CastingOffice_Leaving() {
        System.out.println("Leaving the Casting Office front desk.");
    }

    // Prints any general statement to the console.
    public void printStatement(String statement) {
        System.out.println(statement);
    }

    // Reads and returns the next line of text entered by the player.
    public String AskForStatement() {
        return scan.nextLine();
    }
}