package dev.bmtech.libraryexpensetracker.controllers;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring Boot REST Controller that handles requests to the transaction
 * endpoints
 */
@RestController
public class TransactionController {

    @Autowired
    TransactionService service;

    /**
     * 
     * <b>GET /api/transaction</b>
     * 
     * <p>
     * Gets transactions list from TransactionService and returns transactions as an
     * array of transaction objects
     * </p>
     * 
     * @return <b>List transactions:</b> The list of transactions
     * @see TransactionService
     */
    @GetMapping("/api/transaction")
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = service.getTransactions();
        return transactions;
    }

    /**
     * 
     * <b>POST /api/transaction</b>
     * 
     * <p>
     * Creates transaction through TransactionService with provided transactionInfo
     * </p>
     * 
     * @param transactionInfo info that new transaction will be created with
     * 
     * @return <b>Transaction createdTransaction:</b> The newly created
     *         transaction's info
     * @see TransactionService
     */
    @PostMapping("/api/transaction")
    public Transaction createTransaction(@RequestBody Transaction transactionInfo) {

        // Gets transactionInfo from request's body and creates transaction through
        // TransactionService

        Transaction createdTransaction = service.createTransaction(transactionInfo);
        return createdTransaction;

    }

    /**
     * 
     * <b>DELETE /api/transaction/{transactionID}</b>
     * 
     * <p>
     * Deletes transaction through TransactionService with provided transaction ID
     * </p>
     * 
     * @param transactionID id of transaction to be deleted
     * 
     * @return <b>boolean deletedSuccessfully:</b> whether the transaction was
     *         successfully deleted or not
     * 
     * @see TransactionService
     */
    @DeleteMapping("/api/transaction/{transactionID}")
    public boolean deleteTransaction(@PathVariable int transactionID) {

        // Gets transaction ID from path and calls deleteTransaction on
        // TransactionService

        boolean deletedSuccessfully = service.deleteTransaction(transactionID);
        return deletedSuccessfully;

    }

    /**
     * 
     * <b>PUT /api/transaction/{transactionID}</b>
     * 
     * <p>
     * Edits a transaction through TransactionService with provided transaction ID
     * and transaction info
     * </p>
     * 
     * @param transactionID      id of transaction to be edited
     * @param newTransactionInfo transaction info that will overwrite current
     *                           transaction's info
     * 
     * @return <b>Transaction editedTransactionInfo:</b> the transaction's new info
     * 
     * @see TransactionService
     */
    @PutMapping("/api/transaction/{transactionID}")
    public Transaction editTransaction(@PathVariable int transactionID, @RequestBody Transaction newTransactionInfo) {

        // Gets transaction ID from path and newTransactionInfo from request body and
        // calls editTransaction on TransactionService

        Transaction editedTransactionInfo = service.editTransaction(transactionID, newTransactionInfo);
        return editedTransactionInfo;

    }

}
