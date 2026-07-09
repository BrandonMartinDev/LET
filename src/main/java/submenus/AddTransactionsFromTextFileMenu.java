package submenus;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import base.*;
import transaction.Transaction;
import transaction.Transaction.TransactionType;

public class AddTransactionsFromTextFileMenu extends SubMenu { // Class that extends sub menu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public AddTransactionsFromTextFileMenu() {
        // Passes menuNameString to parent class
        super("Add new transactions from text file");
    }

    // -- == [[ METHODS ]] == -- \\

    private void printTransactionInfoFormat() {

        // Tells user what format transaction info must be in

        System.out.println("Transaction information must be in the format:");
        System.out.println("\nID#,Title,Description,Date,Amount,TransactionType");

    }

    private String promptForTransactionsTextFile() {

        // Initializes input variable

        String input;

        // Prompts user for overdue text file path

        System.out.println();
        System.out.print("Path to Text File: ");

        // Attempts to read input from scanner

        try {
            input = scnr.nextLine();
        } catch (Error e) {

            // If an error occurs:
            // Warns user to enter the file path as text
            // Discard tokens from scanner
            // Reprompt user for file path

            System.out.println("Please enter the file path as text!");
            scnr.nextLine();
            return promptForTransactionsTextFile();
        }

        // Trims input
        input = input.trim();

        // Checks if input is blank
        // If it is, warn user and reprompt

        if (input.isBlank()) {
            System.out.println("\"File path\" cannot be blank!");
            return promptForTransactionsTextFile();
        }

        // Checks if input is a valid text file path
        // If it isn't, warns user and reprompts

        if (!Transaction.isFilePathAValidTextFile(input)) {
            System.out.println(String.format("'%s' is not a valid text file path!", input));
            return promptForTransactionsTextFile();
        }

        return input;

    }

    private boolean promptToConfirmAdding() {

        // Prompts user whether to add transactions to TransactionList

        System.out.print("\nAdd these transactions to current list? (Y/n): ");

        try {

            // Reads input from scanner and discards rest of tokens

            String input = scnr.next().toLowerCase();
            scnr.nextLine();

            // Returns whether input is "y" or not

            return input.equals("y");

        } catch (Error e) {

            // If an error occurs
            // Warns user and reprompts for user to try again

            System.out.println(e.getMessage());
            return promptToConfirmAdding();

        }

    }

    public void switchToMenu() {

        // Clears console and prints menu name and prints transaction info format

        System.out.print("\033[H\033[2J");
        printMenuName();
        printTransactionInfoFormat();

        // Gets file path to text file from user

        String filePath = promptForTransactionsTextFile();

        // Clears console and prints menu name again

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Gets array list of transactions from text file

        ArrayList<Transaction> transactions = Transaction.getTransactionsFromTextFile(filePath);

        // Verifies temp transactions list exists and has transactions

        if (transactions == null || transactions.size() == 0) {

            // If no transactions were able to be created from text file then
            // Warn user and prompt to return to main menu

            System.out.println("\nNo valid transaction info was found in text file\n");
            promptToSwitchToMainMenu();

            return;

        }

        // Tells user how many transactions were successfully loaded from text file

        System.out.println(String.format("Loaded '%s' transactions from text file:\n", transactions.size()));

        // Loops through temp transaction arraylist and prints out each transaction's
        // info

        for (Transaction transaction : transactions) {
            System.out.println(transaction.toString());
            System.out.println();
        }

        // Asks user to confirm adding users to TransactionList

        boolean confirmAdding = promptToConfirmAdding();

        if (confirmAdding) {

            // If user confirms then:
            // Loop through temp transaction arraylist and add each transaction to
            // TransactionList hashmap

            for (Transaction transaction : transactions) {
                Transaction.addTransactionToList(transaction);
            }

            // Tell user how many transactions were added to hashmap
            // and tell user new TransactionList hashmap size

            System.out.println(String.format("\n'%s' transactions were added to list", transactions.size()));
            System.out.println(String.format("New transaction list size: %s", Transaction.TransactionList.size()));

        } else {

            // If user denies then tell user the system did not add
            // any transactions to TransactionList hashmap

            System.out.println("\nDid not add transactions to list");

        }

        System.out.println();

        promptToSwitchToMainMenu();

    }

}