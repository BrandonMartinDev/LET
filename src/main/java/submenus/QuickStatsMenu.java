package submenus;

import base.*;
import transaction.Transaction;

public class QuickStatsMenu extends SubMenu { // Class that extends SubMenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public QuickStatsMenu() {
        // Passes menuNameString to parent class
        super("Display quick stats");
    }

    // -- == [[ METHODS ]] == -- \\

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Gets quick stats from Transaction class and prints 'em out

        System.out.println(String.format("Total Expenses: $%.2f", Transaction.getTotalExpenses()));
        System.out.println(String.format("Total Donations: $%.2f", Transaction.getTotalDonations()));
        System.out.println(String.format("Net Worth: $%.2f", Transaction.getNetWorth()));
        System.out.println();

        // Prompts user to switch to MainMenu

        promptToSwitchToMainMenu();

    }

}