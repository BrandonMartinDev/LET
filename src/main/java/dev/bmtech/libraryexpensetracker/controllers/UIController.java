package dev.bmtech.libraryexpensetracker.controllers;

import dev.bmtech.libraryexpensetracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

    @Autowired
    TransactionService service;

    @GetMapping("/")
    public String mainPage(Model model) {
        // Returns index html page
        return "index";
    }

}
