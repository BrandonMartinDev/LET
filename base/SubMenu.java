package base;

public abstract class SubMenu extends Menu { // Class that extends menu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public SubMenu(String menuNameString) {
        // Passes menuNameString to parent class
        super(menuNameString);
    }

    // -- == [[ METHODS ]] == -- \\

    public boolean promptToDisplayTransactionList() {

        // Prompts user whether to display current transaction list

        System.out.print("Display current transaction list? (Y/n): ");

        try {

            // Reads input from scanner and discards rest of tokens

            String input = scnr.next().toLowerCase();
            scnr.nextLine();

            // Returns whether input is "y" or not

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid option entered, please try again...");
                return promptToDisplayTransactionList();
            }

        } catch (Error e) {

            // If an error occurs
            // Warns user and reprompts for user to try again

            System.out.println(e.getMessage());
            return promptToDisplayTransactionList();

        }

    }

    public String promptForString(String promptString) {

        // Initializes input variable

        String input;

        // Prompts user for a string

        System.out.println();
        System.out.print(promptString + ": ");

        // Attempts to read input from scanner

        try {
            input = scnr.nextLine();
        } catch (Error e) {

            // If an error occurs:
            // Warns user to enter the string as text
            // Discard tokens from scanner
            // Reprompt user for string

            System.out.println("Please enter the " + promptString.toLowerCase() + " as text!");
            scnr.nextLine();
            return promptForString(promptString);

        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt
        // If it isn't, return input

        if (input.isBlank()) {
            System.out.println("\"" + promptString + "\" cannot be blank!");
            return promptForString(promptString);
        } else {
            return input;
        }

    }

    public boolean promptToConfirmOption() {

        // Prompts user whether something is ok or not

        System.out.print("\nIs this ok? (Y/n): ");

        try {

            // Reads input from scanner and discards rest of tokens

            String input = scnr.next().toLowerCase();
            scnr.nextLine();

            // Returns whether input is "y" or not

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid option entered, please try again...");
                return promptToConfirmOption();
            }

        } catch (Error e) {

            // If an error occurs
            // Warns user and reprompts for user to try again

            System.out.println(e.getMessage());
            return promptToConfirmOption();

        }

    }

    public void promptToSwitchToMainMenu() {

        // Prompt user to go back to main menu
        // Waits for user to press enter by getting nextLine from scanner

        System.out.print("Press enter to go back to main menu...");
        scnr.nextLine();

        // Gets first menu from subMenus hashmap (MainMenu) and switches to it
        Menu.subMenus.get(0).switchToMenu();

    }

}
