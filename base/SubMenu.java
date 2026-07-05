package base;

public abstract class SubMenu extends Menu { // Class that extends menu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public SubMenu(String menuNameString) {
        // Passes menuNameString to parent class
        super(menuNameString);
    }

    // -- == [[ METHODS ]] == -- \\

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

    public void promptToSwitchToMainMenu() {

        // Prompt user to go back to main menu
        // Waits for user to press enter by getting nextLine from scanner

        System.out.print("Press enter to go back to main menu...");
        scnr.nextLine();

        // Gets first menu from subMenus hashmap (MainMenu) and switches to it
        Menu.subMenus.get(0).switchToMenu();

    }

}
