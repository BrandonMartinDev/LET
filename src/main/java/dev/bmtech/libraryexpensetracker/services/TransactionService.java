package dev.bmtech.libraryexpensetracker.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.models.Transaction.TransactionType;

@Service
public class TransactionService {

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

    public ArrayList<Transaction> getTransactions() {

        // Converts the transaction list hashmap to an array list and returns it

        ArrayList<Transaction> transactionList = new ArrayList<Transaction>(Transaction.TransactionList.values());
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

        // Adds transaction to transaction list and returns newly created transaction

        Transaction newTransaction = Transaction.addTransactionToList(transactionInfo);
        return newTransaction;

    }

    public boolean deleteTransaction(int transactionID) {

        // Validate id num provided is a valid transaction id

        if (!Transaction.isIDNumValid(transactionID)) {
            return false;
        }

        // Removes transaction from transaction list

        boolean deletedSuccessfully = Transaction.removeTransactionFromList(transactionID);
        return deletedSuccessfully;

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

        Transaction newTransaction = Transaction.editTransactionInList(transactionID, newTransactionInfo);
        return newTransaction;

    }

}
