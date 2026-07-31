package dev.bmtech.libraryexpensetracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.models.TransactionRepository;
import dev.bmtech.libraryexpensetracker.models.Transaction.TransactionType;

/**
 * Spring Boot Service that handles the business logic for transactions.
 * However,
 * this service does <b>NOT</b> directly interact with the database
 */
@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    // -- == [[ QUICK STATS ]] == -- \\

    /**
     * <p>
     * Adds up all transactions that have a type of "EXPENSE" and returns the sum
     * </p>
     * 
     * @return <b>double expensesTotal:</b> The total amount of money the library
     *         has spent on expenses
     */
    public double getExpenses() {

        // Gets transaction list from database

        List<Transaction> transactions = getTransactions();

        // Loops through every transaction in db

        double expensesTotal = 0;

        for (Transaction currentTransaction : transactions) {

            // If the current transaction's type is expense, add the current transaction's
            // amount to expensesTotal

            if (currentTransaction.getType() == TransactionType.EXPENSE) {
                expensesTotal += currentTransaction.getAmount();
            }

        }

        return expensesTotal;

    }

    /**
     * <p>
     * Adds up all transactions that have a type of "DONATION" and returns the sum
     * </p>
     * 
     * @return <b>double expensesTotal:</b> The total amount of money the library
     *         has received in donations
     */
    public double getDonations() {

        // Gets transaction list from database

        List<Transaction> transactions = getTransactions();

        // Loops through every transaction in db

        double donationTotal = 0;

        for (Transaction currentTransaction : transactions) {

            // If the current transaction's type is donation, add the current transaction's
            // amount to donationTotal

            if (currentTransaction.getType() == TransactionType.DONATION) {
                donationTotal += currentTransaction.getAmount();
            }

        }

        return donationTotal;

    }

    /**
     * <p>
     * Returns the difference of donations total minus expenses total
     * </p>
     * 
     * @return <b>double networth:</b> The net worth of the library
     */
    public double getNetWorth() {

        double donations = getDonations();
        double expenses = getExpenses();

        double networth = donations - expenses;

        return networth;

    }

    // -- == [[ CRUD OPERATIONS ]] == -- \\

    /**
     * <p>
     * Gets all transactions from the database through TransactionRepository and
     * returns them as a List of Transaction objects
     * </p>
     * 
     * @return <b>List transactionList:</b> All transactions in the
     *         database
     * @see TransactionRepository
     */
    public List<Transaction> getTransactions() {

        // Gets all transactions from database and returns them

        System.out.println("GETTING TRANSACTIONS FROM REPO");
        List<Transaction> transactionList = repository.findAll();

        return transactionList;

    }

    /**
     * <p>
     * Creates a new transaction in the database through the TransactionRepository
     * with the provided transactionInfo
     * </p>
     * 
     * @param transactionInfo Transaction info that will be used to create the new
     *                        transaction
     * 
     * @return <b>Transaction savedTransaction:</b> The newly created transaction
     * @see TransactionRepository
     */
    public Transaction createTransaction(Transaction transactionInfo) {

        // Validates data

        boolean isValidData = Transaction.isTransactionInfoValid(
                transactionInfo.getId(),
                transactionInfo.getTitle(),
                transactionInfo.getDescription(),
                transactionInfo.getDate(),
                transactionInfo.getAmount(),
                transactionInfo.getType());

        if (!isValidData) {
            System.out.println("Transaction info provided was invalid:\n");
            System.out.println(transactionInfo);
            return null;
        }

        // Adds transaction to transaction database and returns newly created
        // transaction

        Transaction savedTransaction = repository.save(transactionInfo);
        return savedTransaction;

    }

    /**
     * <p>
     * Removes a transaction from the database through the TransactionRepository
     * with the provided transactionID
     * </p>
     * 
     * @param transactionID The ID of the transaction to be deleted
     * 
     * @return <b>boolean successfullyDeleted:</b> Whether the transaction was
     *         successfully deleted or not
     * @see TransactionRepository
     */
    public boolean deleteTransaction(int transactionID) {

        // Attempts to remove the transaction in the database and returns
        // true if was successful
        // If it cannot delete the transaction or errors, will return false

        try {
            repository.deleteById(transactionID);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * <p>
     * Edits a transaction in the database through the TransactionRepository
     * with the provided transactionID and newTransactionInfo
     * </p>
     * 
     * @param transactionID      The ID of the transaction to be edited
     * @param newTransactionInfo new transaction info that will overwrite the
     *                           current transaction's info
     * 
     * @return <b>Transaction editedTransaction:</b> The edited transaction's new
     *         info
     * @see TransactionRepository
     */
    public Transaction editTransaction(int transactionID, Transaction newTransactionInfo) {

        // Validates new data

        boolean isValidData = Transaction.isTransactionInfoValid(
                newTransactionInfo.getId(),
                newTransactionInfo.getTitle(),
                newTransactionInfo.getDescription(),
                newTransactionInfo.getDate(),
                newTransactionInfo.getAmount(),
                newTransactionInfo.getType());

        if (!isValidData) {
            System.out.println("New transaction info provided was invalid:\n");
            System.out.println(newTransactionInfo);
            return null;
        }

        // Gets transaction in database

        Transaction transactionFromDB = repository.findById(transactionID).get();

        if (transactionFromDB == null) {
            System.out.println("Could not find transaction with id '" + transactionID + "'");
            return null;
        }

        // Overwrites transaction data with new transaction info and returns edited
        // transaction info

        newTransactionInfo.setId(transactionID);
        Transaction editedTransaction = repository.save(newTransactionInfo);

        return editedTransaction;

    }

}
