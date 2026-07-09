package submenus;

import java.time.LocalDate;
import java.util.InputMismatchException;

import base.SubMenu;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

public class AddTransactionToListMenu extends SubMenu { // Class that extends SubMenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public AddTransactionToListMenu() {
        // Passes menuNameString to parent class
        super("Add transaction to list");
    }

    // -- == [[ METHODS ]] == -- \\

    private LocalDate promptForDate() {

        // Initializes input variable

        String input;

        // Prompts user for a date

        System.out.println();
        System.out.println("Date must be in format 'YYYY-MM-DD'");
        System.out.print("Date: ");

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
            return promptForDate();

        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt
        // If it isn't, return input

        if (input.isBlank()) {
            System.out.println("\"Date\" cannot be blank!");
            return promptForDate();
        }

        if (!Transaction.isDateStringValid(input)) {
            System.out.println("\"Date\" is in an invalid format!");
            return promptForDate();
        }

        return Transaction.getDateFromString(input);

    }

    private double promptForAmount() {

        // Prompts user for transaction amount

        System.out.println();
        System.out.print("Amount: ");

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
            return promptForAmount();

        } catch (Error e) {

            // If an error occurs:
            // Print error to console
            // Reprompt user to try again

            System.out.println(e.getMessage());
            return promptForAmount();
        }

    }

    private TransactionType promptForType() {

        // Initializes input variable

        String input;

        // Prompts user for a transaction type

        System.out.println();
        System.out.println("Type must either be 'DONATION' or 'EXPENSE'");
        System.out.print("Transaction Type: ");

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
            return promptForType();

        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt
        // If it isn't, return input

        if (input.isBlank()) {
            System.out.println("\"Transaction Type\" cannot be blank!");
            return promptForType();
        }

        if (!Transaction.isTransactionTypeStringValid(input)) {
            System.out.println("\"Type\" is not 'DONATION' or 'EXPENSE'!");
            return promptForType();
        }

        return Transaction.getTransactionTypeFromString(input);

    }

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Gets transaction info from user input

        String title = promptForString("Title");
        String description = promptForString("Description");
        LocalDate date = promptForDate();
        double amount = promptForAmount();
        TransactionType type = promptForType();

        // Clears console again and creates new Transaction from user provided info

        System.out.print("\033[H\033[2J");
        Transaction newTransaction;

        try {

            newTransaction = new Transaction(title, description, date, amount,
                    type);

        } catch (Exception e) {
            System.out.println("There was an error creating transaction, please try again...");
            promptToSwitchToMainMenu();
            return;
        }

        // Tells users new transaction was created successfully

        System.out.println("\nCreated new transaction successfully!\n");
        System.out.println(newTransaction);

        // Adds transaction to list and tell user

        Transaction.addTransactionToList(newTransaction);
        System.out.println("\nAdded transaction to list\n");

        // Prompt to switch back to main menu

        promptToSwitchToMainMenu();

    }

}
