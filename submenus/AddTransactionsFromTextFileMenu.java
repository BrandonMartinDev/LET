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

    private String getFileExtension(String filePath) {

        // Gets last index of "." and checks to make sure it exists

        int indexOfDot = filePath.lastIndexOf(".");

        // If it doesnt exist, throws error

        if (indexOfDot == -1) {
            throw new Error("String is not a valid file path");
        }

        // Returns every character after period (includes period)
        // Example: "something.txt" becomes ".txt"

        return filePath.substring(indexOfDot);

    }

    private boolean isFilePathAValidTextFile(String filePath) {

        try {

            // Gets file extension from filePath provided
            // checks to make sure its a txt file

            String fileExtension = getFileExtension(filePath);
            if (!fileExtension.equals(".txt"))
                return false;

        } catch (Error e) {
            // If an error occurs getting file extension, returns false
            return false;
        }

        // Creates path from filePath after its a text file has been confirmed
        // Makes sure path is valid and file exists

        Path path = Paths.get(filePath);
        boolean exists = Files.exists(path);

        return exists;

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

        if (!isFilePathAValidTextFile(input)) {
            System.out.println(String.format("'%s' is not a valid text file path!", input));
            return promptForTransactionsTextFile();
        }

        return input;

    }

    private Transaction createTransactionFromTextLine(String line) {

        // Initializes newTransaction variable

        Transaction newTransaction = null;

        try {

            // Splits line by comma delimiter

            String[] splitString = line.split(",");

            if (splitString.length != 6)
                throw new Exception();

            // Gets transaction info from split string array

            int id = Integer.parseInt(splitString[0]);
            String title = splitString[1];
            String description = splitString[2];
            LocalDate date = Transaction.getDateFromString(splitString[3]);
            double amount = Double.parseDouble(splitString[4]);
            TransactionType type = Transaction.getTransactionTypeFromString(splitString[5]);

            boolean validInfo = Transaction.isTransactionInfoValid(id, title, description, date, amount, type);

            if (!validInfo)
                throw new Exception("Transaction info was invalid");

            // Creates new transaction with transaction info
            // Overwrites transaction id with id provided from line

            newTransaction = new Transaction(title, description, date, amount, type);
            newTransaction.setId(id);

        } catch (Exception e) {

            // If error occurs, warns user system is skipping line (newTransaction will be
            // null)

            System.out.println(
                    String.format("Warning, skipping '%s' because transaction info is not in the correct format",
                            line));
        }

        // Returns newly created transaction or null based on success or not

        return newTransaction;
    }

    private ArrayList<Transaction> loadTransactionsFromTextFile(String filePath) {

        // Initializes empty arraylist of transactions and text file scanner

        ArrayList<Transaction> transactions = new ArrayList<>();
        Scanner textFileScnr = null;

        try {

            // Creates new scanner that reads text file

            textFileScnr = new Scanner(new File(filePath));

            // Loops through every line in text file

            while (textFileScnr.hasNextLine()) {

                // Gets current line in text file and attempts to create transaction from it

                String currentLine = textFileScnr.nextLine();
                Transaction newTransaction = createTransactionFromTextLine(currentLine);

                // If the transaction was successfully created from text file line
                // System adds it to temporary transactions arraylist (NOT
                // Transaction.TransactionList)

                if (newTransaction != null) {
                    transactions.add(newTransaction);
                }

            }

        } catch (Exception e) {

            // If an error occurs (most likely from attempting to read file)
            // then return nothing

            return null;

        } finally {

            // Close file scanner

            if (textFileScnr != null) {
                textFileScnr.close();
            }

        }

        return transactions;

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

        ArrayList<Transaction> transactions = loadTransactionsFromTextFile(filePath);

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
                Transaction.TransactionList.put(transaction.getId(), transaction);
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