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

import dev.bmtech.libraryexpensetracker.services.TransactionService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Main transaction class. Represents a transaction in the LET
 * 
 * @apiNote
 *          <p>
 *          Uses JPA annotations to map class to transaction table in database
 *          </p>
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    // -- == [[ ENUMS ]] == -- \\

    /**
     * Enum that differentiates a transaction between a type of expense or donation
     */
    public enum TransactionType {
        DONATION,
        EXPENSE
    }

    // -- == [[ VALIDATORS ]] == -- \\

    /**
     * Validates "id" field
     * 
     * <p>
     * A valid transaction id is:
     * </p>
     * 
     * <ol>
     * <li>An integer</li>
     * <li>7 Digits long</li>
     * </ol>
     * 
     * @param idNum Integer that represents the id to be validated
     * @return <b>boolean idNumIsValid:</b> Whether integer provided is a valid id
     */
    public static boolean isIDNumValid(int idNum) {

        // Makes sure id num is 7 digits long

        return idNum >= 1000000 && idNum <= 9999999;

    }

    /**
     * Validates "title" field
     * 
     * <p>
     * A valid title is:
     * </p>
     * 
     * <ol>
     * <li>A string</li>
     * <li>1 or more characters</li>
     * <li>Less than 151 characters</li>
     * </ol>
     * 
     * @param title String that represents the title to be validated
     * @return <b>boolean titleIsValid:</b> Whether string provided is a valid title
     */
    public static boolean isTitleValid(String title) {

        // Makes sure title is 1 or more characters and less than 150 characters

        return title.length() >= 1 && title.length() < 151;

    }

    /**
     * Validates "description" field
     * 
     * <p>
     * A valid description is:
     * </p>
     * 
     * <ol>
     * <li>A string</li>
     * <li>1 or more characters</li>
     * <li>Less than 1001 characters</li>
     * </ol>
     * 
     * @param description String that represents the description to be validated
     * @return <b>boolean descriptionIsValid:</b> Whether string provided is a valid
     *         description
     */
    public static boolean isDescriptionValid(String description) {

        // Makes sure desc is 1 or more characters and less than 1000 characters

        return description.length() >= 1 && description.length() < 1001;

    }

    /**
     * Validates "date" field
     * 
     * <p>
     * A valid date string is:
     * </p>
     * 
     * <ol>
     * <li>A string or LocalDate</li>
     * <li>In the format: YYYY-MM-DD</li>
     * </ol>
     * 
     * @param dateString String that represents the date string to be validated
     * @return <b>boolean dateIsValid:</b> Whether string provided is a valid
     *         date string
     */
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

    /**
     * Validates "amount" field
     * 
     * <p>
     * A valid amount is:
     * </p>
     * 
     * <ol>
     * <li>A double</li>
     * <li>Not negative</li>
     * </ol>
     * 
     * @param amount Double that represents the amount to be validated
     * @return <b>boolean amountIsValid:</b> Whether double provided is a valid
     *         amount
     */
    public static boolean isAmountValid(double amount) {

        // Makes sure amount is greater than or equal to 0

        return amount >= 0;

    }

    /**
     * Validates "type" field
     * 
     * <p>
     * A valid type string is:
     * </p>
     * 
     * <ol>
     * <li>A string</li>
     * <li>Either "DONATION" or "EXPENSE" (not case sensitive, and can also- be a
     * partial string)</li>
     * </ol>
     * 
     * @param typeString String that represents the type to be validated
     * @return <b>boolean stringIsValid:</b> Whether string provided is a valid
     *         type
     */
    public static boolean isTransactionTypeStringValid(String typeString) {

        if (typeString.isBlank())
            return false;

        // Lowercases string provided and
        // checks if either "donation" or "expense" contains the string provided

        typeString = typeString.toLowerCase();

        return ("donation".contains(typeString) || "expense".contains(typeString));

    }

    /**
     * Validates all fields
     * 
     * <p>
     * Goes through each piece field provided and validates them
     * </p>
     * 
     * @param id          Integer that represents the id to be validated
     * @param title       String that represents the title to be validated
     * @param description String that represents the description to be validated
     * @param date        LocalDate that represents the date to be validated
     * @param amount      double that represents the amount to be validated
     * @param type        TransactionType that represents the type to be validated
     * @return <b>boolean transactionInfoIsValid:</b> Whether transaction info
     *         provided is valid or not. If even just 1 field is invalid, will
     *         return false
     */
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

        // Skips validating id because id is automatically handled by spring JPA

        // boolean validID = isIDNumValid(id);

        // if (!validID)
        // return false;

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

    /**
     * <s>The transaction list hashmap - this is where all transactions are stored
     * directly in the application</s>
     *
     * <p>
     * TransactionList has been deprecated in favor of adding a database to track
     * transactions
     * </p>
     *
     * @deprecated
     */
    public static HashMap<Integer, Transaction> TransactionList = new HashMap<Integer, Transaction>(); // TransactionList
                                                                                                       // hashmap, this
                                                                                                       // is
    // where all transactions are stored directly in the application

    // -- == [[ STATIC METHODS ]] == -- \\

    /**
     * <p>
     * Loops through every transaction in the TransactionList hashmap and prints
     * each transaction to the console
     * </p>
     * 
     * @deprecated
     */
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

    /**
     * <p>
     * Takes the string provided and returns a LocalDate object from it. If string
     * provided is not a valid date string, will return null
     * </p>
     * 
     * @param dateString The string to be converted into a LocalDate
     * @return <b>LocalDate date:</b> The LocalDate object from the string
     */
    public static LocalDate getDateFromString(String dateString) {

        try {
            return LocalDate.parse(dateString);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * <p>
     * Takes the string provided and returns a TransactionType enum from it. If
     * string provided is not a valid TransactionType string, will return null
     * </p>
     * 
     * @param typeString The string to be converted into a TransactionType
     * @return <b>TransactionType date:</b> The TransactionType enum from the
     *         string
     */
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

    /**
     * <p>
     * Adds up all transactions from the TransactionList hashmap that have a type of
     * "EXPENSE" and returns the sum
     * </p>
     * 
     * @return <b>double expensesTotal:</b> The total amount of money the library
     *         has spent on expenses
     * @deprecated
     * @see TransactionService
     */
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

    /**
     * <p>
     * Adds up all transactions from the TransactionList hashmap that have a type of
     * "DONATIONS" and returns the sum
     * </p>
     * 
     * @return <b>double donationsTotal:</b> The total amount of money the library
     *         has received in donations
     * @deprecated
     * @see TransactionService
     */
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

    /**
     * <p>
     * Returns the difference of donations total minus expenses total
     * </p>
     * 
     * @return <b>double networth:</b> The net worth of the librarys
     * @deprecated
     * @see TransactionService
     */
    public static double getNetWorth() {

        // Gets all donations summed and subtracts all expenses from it

        double totalDonations = getTotalDonations();
        double totalExpenses = getTotalExpenses();

        return Double.parseDouble(df.format(totalDonations - totalExpenses));

    }

    /**
     * <p>
     * Takes the string provided and converts it into a transaction object
     * </p>
     * 
     * <p>
     * String provided must be in the format:
     * '<b>
     * ID#,Title,Description,Date,Amount,Type
     * </b>'
     * </p>
     * 
     * @param line The line to be converted into a transaction
     * @return <b>Transaction transaction:</b> The transaction object created from
     *         the string
     * @deprecated
     */
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

    /**
     * <p>
     * Gets the file extension from a file path string
     * </p>
     * 
     * @param filePath The String of the file path
     * @return <b>String fileExtension:</b> The file extension of the path
     * @deprecated
     */
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

    /**
     * <p>
     * Checks if a file path is a valid text file
     * </p>
     * 
     * @param filePath The String of the file path
     * @return <b>boolean filePathIsAValidTextFile:</b> Whether the file path
     *         provided leads to a valid text file
     * @deprecated
     */
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

    /**
     * <p>
     * Reads a text file line by line and creates an ArrayList of transaction
     * objects from each line
     * </p>
     * 
     * @param filePath The String of the text file path
     * @return <b>ArrayList transactionsFromTextFile:</b> The list of
     *         transaction objects
     * @deprecated
     */
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

    /**
     * <p>
     * Adds the transaction provided to the transaction list hashmap
     * </p>
     * 
     * @param transactionToAdd the transaction object to add
     * @return <b>Transaction transaction:</b> The transaction that was added to the
     *         transaction list hashmap
     * @deprecated
     */
    public static Transaction addTransactionToList(Transaction transactionToAdd) {

        // Adds transaction provided to list
        // Gets transaction from list and returns it

        TransactionList.put(transactionToAdd.getId(), transactionToAdd);
        return TransactionList.getOrDefault(transactionToAdd.getId(), null);

    }

    /**
     * <p>
     * Gets the specified transaction from the transaction list hashmap by id
     * </p>
     * 
     * @param id The id of the transaction to get from the transaction list
     * @return <b>Transaction transaction:</b> the transaction from the transaction
     *         list hashmap
     * @deprecated
     */
    public static Transaction getTransactionByID(int id) {

        // Gets transaction from list and returns it (or null if no such transaction
        // exists)

        return TransactionList.getOrDefault(id, null);

    }

    /**
     * <p>
     * Removes the specified transaction from the transaction list hashmap by id
     * </p>
     * 
     * @param id The id of the transaction to remove from the transaction list
     * @return <b>boolean success:</b> Whether the transaction was successfully
     *         removed from the transaction list hashmap
     * @deprecated
     */
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

    /**
     * <p>
     * Overwrites the specified transaction in the transaction list hashmap with new
     * transaction info provided
     * </p>
     * 
     * @param id                 The id of the transaction to edit in the
     *                           transaction list
     * @param newTransactionInfo the new info to overwrite the current transaction's
     *                           info
     * @return <b>Transaction editedTransactionInfo:</b> The edited transaction's
     *         new info
     * @deprecated
     */
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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "id_sequence", allocationSize = 1, initialValue = 1_000_000)
    private int id; // 7 Digit unique ID number for transaction

    @Column(name = "title", nullable = false, length = 100)
    private String title; // Title of the transaction

    @Column(name = "description", nullable = false, length = 1000)
    private String description; // Description of the transaction

    @Column(name = "date", nullable = false)
    private LocalDate date; // Date the transaction took place

    @Column(name = "amount", nullable = false)
    private double amount = 0; // Amount the transaction involved, default is 0

    @Column(name = "type", nullable = false)
    private TransactionType type; // Type of the transaction, either "DONATION" or "EXPENSE"

    // -- == [[ CONSTRUCTORS ]] == -- \\

    /**
     * Empty constructor needed by TransactionRepository
     * 
     * @see TransactionRepository
     */
    public Transaction() {

    }

    /**
     * Main Transaction constructor
     * 
     * <p>
     * Takes field data provided and creates a transaction object from them
     * </p>
     * 
     * @param title       Title of the transaction
     * @param description Description of the transaction
     * @param date        Date the transaction took place
     * @param amount      Amount the transaction involved, default is 0
     * @param type        Type of the transaction, either "DONATION" or "EXPENSE"
     * @throws Exception If there was an error creating the transaction object, will
     *                   throw an exception that needs to be handled outside of the
     *                   constructor
     */
    public Transaction(String title, String description, LocalDate date, double amount, TransactionType type)
            throws Exception {

        if (title == null || description == null || date == null || amount < 0 || type == null) {
            throw new Exception();
        }

        // Generates unique id
        // this.id = generateId();

        // NO LONGER USES generateID(), spring data JPA will auto generate ids for
        // database table

        // sets values to provided values

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

    /**
     * <p>
     * Overrides default toString method and replaces it with the format:
     * </p>
     * 
     * <br/>
     * <b>ID#:</b> 1234567
     * <br/>
     * <b>Title:</b> Title String
     * <br/>
     * <b>Description:</b> Description String
     * <br/>
     * <b>Date:</b> 1970-01-01
     * <br/>
     * <b>Amount:</b> 403.23
     * <br/>
     * <b>Type:</b> DONATION
     * <br/>
     * 
     */
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

    /**
     * <p>
     * Generates a random 7 digit integer
     * </p>
     * 
     * @return <b>int id:</b> The randomly generated id
     * @deprecated
     */
    private int generateId() {

        // Generates a random 7 digit integer

        return 1000000 + (new Random().nextInt(9000000));

    }

}