package dev.bmtech.libraryexpensetracker.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bmtech.libraryexpensetracker.services.TransactionService;

@RestController
public class QuickStatsController {

    @Autowired
    TransactionService service;

    @GetMapping("/api/stats/expenses")
    public double getExpenses(Model model) {

        // Gets expenses from transaction service

        double expenses = service.getExpenses();

        // Adds attribute to model and returns it to controller

        model.addAttribute("expenses", expenses);
        return expenses;

    }

    @GetMapping("/api/stats/donations")
    public double getDonations(Model model) {

        // Gets donations from transaction service

        double donations = service.getDonations();

        // Adds attribute to model and returns it to controller

        model.addAttribute("donations", donations);
        return donations;

    }

    @GetMapping("/api/stats/networth")
    public double getNetWorth(Model model) {

        // Gets networth from transaction service

        double networth = service.getNetWorth();

        // Adds attribute to model and returns it to controller

        model.addAttribute("networth", networth);
        return networth;

    }

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
