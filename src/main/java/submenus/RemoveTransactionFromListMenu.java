package submenus;

import java.util.InputMismatchException;

import base.SubMenu;
import transaction.Transaction;

public class RemoveTransactionFromListMenu extends SubMenu { // Class that extends submenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public RemoveTransactionFromListMenu() {
        // Passes menuNameString to parent class
        super("Remove transaction from list");
    }

    // -- == [[ METHODS ]] == -- \\

    private int promptForTransactionIDToRemove() {

        // Prompts user for transaction id to remove

        System.out.println();
        System.out.print("Transaction ID to remove: ");

        try {

            // Reads integer from scanner and discards rest of tokens

            int input = scnr.nextInt();
            scnr.nextLine();

            // Verifies number is 7 digits long

            if (input < 1000000 || input > 9999999) {
                throw new Error("Please provide a valid 7 digit number!");
            }

            // Returns input

            return input;

        } catch (InputMismatchException e) {

            // If user inputs anything other than an integer
            // Warns user
            // Discards tokens
            // Reprompts for user to try again

            System.out.println("Please provide a valid 7 digit number!");
            scnr.nextLine();
            return promptForTransactionIDToRemove();

        } catch (Error e) {

            // If an error occurs
            // Warns user and reprompts for user to try again

            System.out.println(e.getMessage());
            return promptForTransactionIDToRemove();

        }

    }

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Prompt user whether to display the transaction list or not

        boolean displayTransactionList = promptToDisplayTransactionList();

        if (displayTransactionList) {
            System.out.println();
            Transaction.displayCurrentTransactionList();
        } else {
            System.out.println("Skipping displaying transaction list");
        }

        // Gets id to remove from user

        int idToRemove = promptForTransactionIDToRemove();
        Transaction transactionToRemove = Transaction.TransactionList.getOrDefault(idToRemove, null);

        // Verifies that transaction exists

        if (transactionToRemove == null) {
            System.out.println(String.format("\nCould not find transaction with id '%s'\n", idToRemove));
            promptToSwitchToMainMenu();
            return;
        }

        // Tells user which transaction is to be removed

        System.out.println(String.format("\nRemoving '%s' from transaction list.", transactionToRemove.getTitle()));

        // Asks user for confirmation before deleting

        boolean confirmDeletion = promptToConfirmOption();

        if (confirmDeletion) {

            // If user confirms deletion:
            // Removes transaction from transaction list
            // Tell user who was removed from list and new list size

            System.out.println();
            Transaction.removeTransactionFromList(idToRemove);
            System.out.println(String.format("Removed '%s' from transaction list", transactionToRemove.getTitle()));
            System.out.println(String.format("New transaction list size: %s", Transaction.TransactionList.size()));
            System.out.println();

        } else {
            // If user denies deletion tell user system did not delete transaction
            System.out.println(String.format("\nDid not delete '%s'\n", transactionToRemove.getTitle()));
        }

        // Prompt to switch to main menu

        promptToSwitchToMainMenu();

    }

}