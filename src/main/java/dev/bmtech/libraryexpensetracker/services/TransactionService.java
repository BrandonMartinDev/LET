package dev.bmtech.libraryexpensetracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.models.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    // Quick Stats

    public double getExpenses() {
        return Transaction.getTotalExpenses();
    }

    public double getDonations() {
        return Transaction.getTotalDonations();
    }

    public double getNetWorth() {
        return Transaction.getNetWorth();
    }

    // CRUD operations

    public List<Transaction> getTransactions() {

        // Gets all transactions from database and returns them

        System.out.println("GETTING TRANSACTIONS FROM REPO");
        List<Transaction> transactionList = repository.findAll();
        System.out.println(transactionList);

        return transactionList;

    }

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
