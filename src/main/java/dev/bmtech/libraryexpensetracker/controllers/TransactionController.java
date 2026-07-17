package dev.bmtech.libraryexpensetracker.controllers;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/api/transaction")
    public List<Transaction> getTransactions() {

        // Gets transactions list from TransactionService and returns transactions;

        List<Transaction> transactions = service.getTransactions();
        return transactions;

    }

    @PostMapping("/api/transaction")
    public Transaction createTransaction(@RequestBody Transaction transactionInfo) {

        // Gets transactionInfo from request's body and creates transaction through
        // TransactionService

        Transaction createdTransaction = service.createTransaction(transactionInfo);
        return createdTransaction;

    }

    @DeleteMapping("/api/transaction/{transactionID}")
    public boolean deleteTransaction(@PathVariable int transactionID) {

        // Gets transaction ID from path and calls deleteTransaction on
        // TransactionService

        boolean deletedSuccessfully = service.deleteTransaction(transactionID);
        return deletedSuccessfully;

    }

    @PutMapping("/api/transaction/{transactionID}")
    public Transaction editTransaction(@PathVariable int transactionID, @RequestBody Transaction newTransactionInfo) {

        // Gets transaction ID from path and newTransactionInfo from request body and
        // calls editTransaction on TransactionService

        Transaction editedTransactionInfo = service.editTransaction(transactionID, newTransactionInfo);
        return editedTransactionInfo;

    }

}
