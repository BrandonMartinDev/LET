package submenus;

import base.*;

public class MainMenu extends Menu { // Class that extends menu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public MainMenu() {
        // Passes menuNameString to parent class
        super("Library Expense Tracker - Main Menu");
    }

    // -- == [[ METHODS ]] == -- \\

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Prints sub menus and prompts user to switch to one

        printSubMenus();
        promptToSwitchToSubmenu();

    }

}