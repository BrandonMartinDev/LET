package submenus;

import java.time.LocalDate;
import java.util.InputMismatchException;

import base.SubMenu;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

public class EditTransactionMenu extends SubMenu { // Class that extends SubMenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public EditTransactionMenu() {
        // Passes menuNameString to parent class
        super("Edit transaction");
    }

    // -- == [[ METHODS ]] == -- \\

    private int promptForTransactionIDToEdit() {

        // Prompts user for transaction id to remove

        System.out.println();
        System.out.print("Transaction ID to edit: ");

        try {

            // Reads integer from scanner and discards rest of tokens

            int input = scnr.nextInt();
            scnr.nextLine();

            // Verifies number is 7 digits long and is a valid transaction id

            if (input < 1000000 || input > 9999999) {
                throw new Error("Please provide a valid 7 digit number!");
            }

            if (Transaction.TransactionList.getOrDefault(input, null) == null) {
                throw new Error("Transaction ID does not exist!");
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
            return promptForTransactionIDToEdit();

        } catch (Error e) {

            // If an error occurs
            // Warns user and reprompts for user to try again

            System.out.println(e.getMessage());
            return promptForTransactionIDToEdit();

        }

    }

    private LocalDate promptForNewDate() {

        // Initializes input variable

        String input;

        // Prompts user for a date

        System.out.println();
        System.out.println("Date must be in format 'YYYY-MM-DD'");
        System.out.print("New Date: ");

        // Attempts to read input from scanner

        try {
            input = scnr.nextLine();
        } catch (Error e) {

            // If an error occurs:
            // Warns user to enter the date as text in the valid format
            // Discard tokens from scanner
            // Reprompt user for string

            System.out.println("Please enter the date as text in the valid format!");
            scnr.nextLine();
            return promptForNewDate();

        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt
        // If it isn't, return input

        if (input.isBlank()) {
            System.out.println("\"Date\" cannot be blank!");
            return promptForNewDate();
        }

        if (!Transaction.isDateStringValid(input)) {
            System.out.println("\"Date\" is in an invalid format!");
            return promptForNewDate();
        }

        return Transaction.getDateFromString(input);

    }

    private double promptForNewAmount() {

        // Prompts user for transaction amount

        System.out.println();
        System.out.print("New Amount: ");

        try {

            // Reads double from scanner and discards rest of tokens from scanner

            double input = scnr.nextDouble();
            scnr.nextLine();

            // Verifies that double is a number greater than or equal to 0

            if (input <= 0) {
                throw new Error(
                        String.format("'%s' is not a valid number greater than or equal to 0! Please try again...",
                                input));
            }

            // Returns input after verification
            return input;

        } catch (InputMismatchException e) {

            // If user enters anything other than a double:
            // Warn user
            // Discard tokens from scanner
            // Reprompt user to try again

            System.out.println("Please provide a valid number greater than or equal to 0!");
            scnr.nextLine();
            return promptForNewAmount();

        } catch (Error e) {

            // If an error occurs:
            // Print error to console
            // Reprompt user to try again

            System.out.println(e.getMessage());
            return promptForNewAmount();
        }

    }

    private TransactionType promptForNewType() {

        // Initializes input variable

        String input;

        // Prompts user for a transaction type

        System.out.println();
        System.out.println("Type must either be 'DONATION' or 'EXPENSE'");
        System.out.print("New Transaction Type: ");

        // Attempts to read input from scanner

        try {
            input = scnr.nextLine();
        } catch (Error e) {

            // If an error occurs:
            // Warns user to enter the type as text
            // Discard tokens from scanner
            // Reprompt user for string

            System.out.println("Please enter the type as text!");
            scnr.nextLine();
            return promptForNewType();

        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt
        // If it isn't, return input

        if (input.isBlank()) {
            System.out.println("\"Transaction Type\" cannot be blank!");
            return promptForNewType();
        }

        if (!Transaction.isTransactionTypeStringValid(input)) {
            System.out.println("\"Type\" is not 'DONATION' or 'EXPENSE'!");
            return promptForNewType();
        }

        return Transaction.getTransactionTypeFromString(input);

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

        // Gets transaction id from user input

        int idToEdit = promptForTransactionIDToEdit();
        Transaction transactionToEdit = Transaction.TransactionList.getOrDefault(idToEdit, null);

        // Guard clause that protects against invalid transaction id provided

        if (transactionToEdit == null) {
            System.out.println("Transaction ID does not exist!");
            promptToSwitchToMainMenu();
            return;
        }

        // Prompts user to confirm editing transaction

        System.out.println("Editing '" + transactionToEdit.getTitle() + "'.");
        boolean confirmEditing = promptToConfirmOption();

        // If user denied editing transaction
        // Tell user editing was canceled and re-prompt to go to main menu

        System.out.print("\033[H\033[2J");
        printMenuName();

        if (confirmEditing != true) {

            System.out.println(
                    "Canceled editing '" + transactionToEdit.getTitle() + "' (" + transactionToEdit.getId() + ")");
            System.out.println();

            promptToSwitchToMainMenu();
            return;

        }

        // Gets new transaction info from user input

        System.out.println("Current Title: " + transactionToEdit.getTitle());
        String newTitle = promptForString("New Title");

        System.out.print("\033[H\033[2J");
        printMenuName();
        System.out.println("Current Description: " + transactionToEdit.getDescription());
        String newDescription = promptForString("New Description");

        System.out.print("\033[H\033[2J");
        printMenuName();
        System.out.println("Current Date: " + transactionToEdit.getDate());
        LocalDate newDate = promptForNewDate();

        System.out.print("\033[H\033[2J");
        printMenuName();
        System.out.println("Current Amount: " + transactionToEdit.getAmount());
        double newAmount = promptForNewAmount();

        System.out.print("\033[H\033[2J");
        printMenuName();
        System.out.println("Current Type: " + transactionToEdit.getType());
        TransactionType newType = promptForNewType();

        // Clears console again and creates new Transaction from user provided info

        System.out.print("\033[H\033[2J");
        printMenuName();

        Transaction newTransaction;

        try {
            newTransaction = new Transaction(newTitle, newDescription, newDate, newAmount, newType);
            newTransaction.setId(idToEdit);
        } catch (Exception e) {
            System.out.println("There was an error editing transaction, please try again...");
            promptToSwitchToMainMenu();
            return;
        }

        // Tells users new transaction was created successfully

        System.out.println("New Transaction Info:");
        System.out.println();
        System.out.println(newTransaction);
        System.out.println();

        // Ask for user confirmation to edit transaction info

        boolean confirmNewInfo = promptToConfirmOption();

        if (confirmNewInfo != true) {

            System.out.println(
                    "Canceled editing '" + transactionToEdit.getTitle() + "' (" + transactionToEdit.getId() + ")");
            System.out.println();

            promptToSwitchToMainMenu();
            return;

        }

        // Updates transaction in TransactionList hashmap and tells user transaction was
        // updated successfully

        Transaction.TransactionList.put(idToEdit, newTransaction);
        System.out.println(
                "Edited '" + transactionToEdit.getTitle() + "' (" + transactionToEdit.getId() + ") successfully!");

        // Prompt to switch back to main menu

        promptToSwitchToMainMenu();

    }

}
