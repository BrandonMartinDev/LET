package dev.bmtech.libraryexpensetracker.models;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Transaction {

    // -- == [[ ENUMS ]] == -- \\

    public enum TransactionType {
        DONATION,
        EXPENSE
    }

    // -- == [[ VALIDATORS ]] == -- \\

    public static boolean isIDNumValid(int idNum) {

        // Makes sure id num is 7 digits long

        return idNum >= 1000000 && idNum <= 9999999;

    }

    public static boolean isTitleValid(String title) {

        // Makes sure title is 1 or more characters and less than 150 characters

        return title.length() >= 1 && title.length() < 151;

    }

    public static boolean isDescriptionValid(String description) {

        // Makes sure desc is 1 or more characters and less than 1000 characters

        return description.length() >= 1 && description.length() < 1001;

    }

    public static boolean isDateStringValid(String dateString) {

        // Attempts to parse date from dateString provided
        // If it succeeds, it will not error and will return true
        // If it fails, it will error and return false

        try {
            LocalDate.parse(dateString);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isAmountValid(double amount) {

        // Makes sure amount is greater than or equal to 0

        return amount >= 0;

    }

    public static boolean isTransactionTypeStringValid(String typeString) {

        if (typeString.isBlank())
            return false;

        // Lowercases string provided and
        // checks if either "donation" or "expense" contains the string provided

        typeString = typeString.toLowerCase();

        return ("donation".contains(typeString) || "expense".contains(typeString));

    }

    public static boolean isTransactionInfoValid(
            int id,
            String title,
            String description,
            LocalDate date,
            double amount,
            TransactionType type) {

        // If any values are null, fails validation immediately

        if (title == null || description == null || date == null || type == null)
            return false;

        // Checks each field and makes sure they are valid

        boolean validID = isIDNumValid(id);

        if (!validID)
            return false;

        boolean validTitle = isTitleValid(title);

        if (!validTitle)
            return false;

        boolean validDesc = isDescriptionValid(description);

        if (!validDesc)
            return false;

        boolean validAmount = isAmountValid(amount);

        if (!validAmount)
            return false;

        return true;

    }

    // -- == [[ STATIC VARIABLES ]] == -- \\

    private static DecimalFormat df = new DecimalFormat("#.##");

    public static HashMap<Integer, Transaction> TransactionList = new HashMap<Integer, Transaction>(); // TransactionList
                                                                                                       // hashmap, this
                                                                                                       // is
    // where all transactions are stored directly in the application

    // -- == [[ STATIC METHODS ]] == -- \\

    public static void displayCurrentTransactionList() {

        // Loops through every entry in TransactionList hashmap

        for (Map.Entry<Integer, Transaction> entry : TransactionList.entrySet()) {

            // Gets current transaction from entry and prints out TransactionInfo on new
            // line

            Transaction currentTransaction = entry.getValue();
            System.out.println(currentTransaction.toString());
            System.out.println();

        }

    }

    public static LocalDate getDateFromString(String dateString) {

        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            return null;
        }

    }

    public static TransactionType getTransactionTypeFromString(String typeString) {

        // Lowercases string provided and
        // checks if either "donation" or "expense" contains the string provided

        typeString = typeString.toLowerCase();

        if ("donation".contains(typeString)) {
            return TransactionType.DONATION;
        } else if ("expense".contains(typeString)) {
            return TransactionType.EXPENSE;
        } else {
            return null;
        }

    }

    public static double getTotalExpenses() {

        double totalExpenses = 0;

        // Loops through TransactionList hashmap

        for (Map.Entry<Integer, Transaction> entry : TransactionList.entrySet()) {

            // Gets current transaction from entry and checks if the type is expense
            // If it is, add current transaction amount to totalExpenses

            Transaction currentTransaction = entry.getValue();

            if (currentTransaction.type == TransactionType.EXPENSE) {
                totalExpenses += currentTransaction.amount;
            }

        }

        return Double.parseDouble(df.format(totalExpenses));

    }

    public static double getTotalDonations() {

        double totalDonations = 0;

        // Loops through TransactionList hashmap

        for (Map.Entry<Integer, Transaction> entry : TransactionList.entrySet()) {

            // Gets current transaction from entry and checks if the type is donation
            // If it is, add current transaction amount to totalDonations

            Transaction currentTransaction = entry.getValue();

            if (currentTransaction.type == TransactionType.DONATION) {
                totalDonations += currentTransaction.amount;
            }

        }

        return Double.parseDouble(df.format(totalDonations));

    }

    public static double getNetWorth() {

        // Gets all donations summed and subtracts all expenses from it

        double totalDonations = getTotalDonations();
        double totalExpenses = getTotalExpenses();

        return Double.parseDouble(df.format(totalDonations - totalExpenses));

    }

    private static Transaction createTransactionFromTextLine(String line) {

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

    private static String getFileExtension(String filePath) {

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

    public static boolean isFilePathAValidTextFile(String filePath) {

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

    // -- == [[ STATIC CRUD METHODS ]] == -- \\

    public static ArrayList<Transaction> getTransactionsFromTextFile(String filePath) {

        // Initializes empty arraylist of transactions and text file scanner

        ArrayList<Transaction> transactions = new ArrayList<>();
        Scanner textFileScnr = null;

        try {

            boolean validTextFile = isFilePathAValidTextFile(filePath);

            if (!validTextFile) {
                throw new Exception("'" + filePath + "' is not a valid text file path!");
            }

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

    public static Transaction addTransactionToList(Transaction transactionToAdd) {

        // Adds transaction provided to list
        // Gets transaction from list and returns it

        TransactionList.put(transactionToAdd.getId(), transactionToAdd);
        return TransactionList.getOrDefault(transactionToAdd.getId(), null);

    }

    public static Transaction getTransactionByID(int id) {

        // Gets transaction from list and returns it (or null if no such transaction
        // exists)

        return TransactionList.getOrDefault(id, null);

    }

    public static boolean removeTransactionFromList(int id) {

        // Attempts to remove transaction from transaction list hashmap
        // If successful, will return true
        // If not, will return false

        try {
            TransactionList.remove(id);
            return true;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to remove transaction '" + id + "' from list");
            return false;
        }

    }

    public static Transaction editTransactionInList(int id, Transaction newTransactionInfo) {

        try {

            // Gets transaction from list, if transaction does not exist then
            // throw exception

            Transaction transactionToEdit = getTransactionByID(id);

            if (transactionToEdit == null) {
                throw new Exception("Transaction with ID '" + id + "' does not exist!");
            }

            // Overwrite transaction info with new transaction info
            // Get transaction from list and return it

            TransactionList.put(id, newTransactionInfo);
            return TransactionList.getOrDefault(id, null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    // -- == [[ VARIABLES ]] == -- \\

    private int id; // 7 Digit unique ID number for transaction
    private String title; // Title of the transaction
    private String description; // Description of the transaction
    private LocalDate date; // Date the transaction took place
    private double amount = 0; // Amount the transaction involved, default is 0
    private TransactionType type; // Type of the transaction, either "DONATION" or "EXPENSE"

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public Transaction(String title, String description, LocalDate date, double amount, TransactionType type)
            throws Exception {

        if (title == null || description == null || date == null || amount < 0 || type == null) {
            throw new Exception();
        }

        // Generates unique id
        // sets values to provided values

        this.id = generateId();
        this.title = title;
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.type = type;

    }

    // -- == [[ GETTERS/SETTERS ]] == -- \\

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    // -- == [[ METHODS ]] == -- \\

    @Override
    public String toString() {

        // Overrides default toString method and replaces it with the format:

        // ID#: 1234567
        // Title: Title String
        // Description: Description String
        // Date: 1970-01-01
        // Amount: 403.23
        // Type: DONATION

        return String.format("ID#: %s\nTitle: %s\nDescription: %s\nDate: %s\nAmount: $%s\nType: %s", this.id,
                this.title, this.description,
                this.date, this.amount, this.type);

    }

    private int generateId() {

        // Generates a random 7 digit integer

        return 1000000 + (new Random().nextInt(9000000));

    }

}