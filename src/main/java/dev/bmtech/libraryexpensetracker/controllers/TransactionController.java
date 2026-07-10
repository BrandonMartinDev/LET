package dev.bmtech.libraryexpensetracker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @RequestMapping("/")
    public String view() {
        return "Test";
    }

}
