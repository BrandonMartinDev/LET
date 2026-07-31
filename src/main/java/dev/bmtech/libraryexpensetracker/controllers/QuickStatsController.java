package dev.bmtech.libraryexpensetracker.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bmtech.libraryexpensetracker.services.TransactionService;

/**
 * Spring Boot REST Controller that handles requests to the quick stats
 * endpoints
 */
@RestController
public class QuickStatsController {

    @Autowired
    TransactionService service;

    /**
     * <b>GET /api/stats</b>
     * 
     * <p>
     * Gets the library's net worth, donations and expenses totals via
     * TransactionService and returns it as a JSON object to the requester
     * </p>
     * 
     * @return HashMap quickStats
     * 
     * @see TransactionService
     */
    @GetMapping("/api/stats")
    public HashMap<String, Double> getQuickStats() {

        double networth = service.getNetWorth();
        double donations = service.getDonations();
        double expenses = service.getExpenses();

        HashMap<String, Double> quickStats = new HashMap<>();

        quickStats.put("networth", networth);
        quickStats.put("donations", donations);
        quickStats.put("expenses", expenses);

        return quickStats;

    }

}
