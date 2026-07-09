package submenus;

import base.*;
import transaction.Transaction;

public class DisplayCurrentTransactionListMenu extends SubMenu { // Class that extends SubMenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public DisplayCurrentTransactionListMenu() {
        // Passes menuNameString to parent class
        super("Display current transaction list");
    }

    // -- == [[ METHODS ]] == -- \\

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Prints current TransactionList

        Transaction.displayCurrentTransactionList();
        System.out.println();

        // Prompts user to switch to MainMenu

        promptToSwitchToMainMenu();

    }

}