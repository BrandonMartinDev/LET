package transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

        return totalExpenses;

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

        return totalDonations;

    }

    public static double getNetWorth() {

        // Gets all donations summed and subtracts all expenses from it

        double totalDonations = getTotalDonations();
        double totalExpenses = getTotalExpenses();

        return totalDonations - totalExpenses;

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