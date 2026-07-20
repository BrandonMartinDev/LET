package dev.bmtech.libraryexpensetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Spring Boot Controller that handles requests for the LET's UI (index.html
 * file)
 */
@Controller
public class UIController {

    /**
     * 
     * <b>GET /</b>
     * 
     * <p>
     * Returns the main index.html page
     * </p>
     * 
     * @return index.html
     */
    @GetMapping("/")
    public String mainPage() {
        // Returns index html page
        return "index";
    }

}
