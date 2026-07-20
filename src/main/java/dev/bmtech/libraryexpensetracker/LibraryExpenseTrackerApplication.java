package dev.bmtech.libraryexpensetracker;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Library Expense Tracker (LET).
 */
@SpringBootApplication
public class LibraryExpenseTrackerApplication {

    /**
     * Gets database connection string, username, and password from user and then
     * sets system properties based on the provided values
     */
    private static void setDBInfoFromUser() {

        try (Scanner scnr = new Scanner(System.in)) {

            System.out.print("Please input database connection string: ");
            String db_connection_string = scnr.nextLine();

            System.out.print("Please input database username: ");
            String db_username = scnr.nextLine();

            System.out.print("Please input database password: ");
            String db_password = scnr.nextLine();

            System.out.println("Set DB Connection String: '" + db_connection_string + "'");
            System.out.println("Set DB Username: '" + db_username + "'");
            System.out.println("Set DB Password: '" + db_password + "'");

            System.setProperty("DB_URL", db_connection_string);
            System.setProperty("DB_USERNAME", db_username);
            System.setProperty("DB_PASSWORD", db_password);

        } catch (Exception e) {
            System.out.println("\n\n- Could not set database system variables - \nError Message: ");
            System.out.println(e.getMessage());
        }

    }

    /**
     * Sets system properties based on user input and then starts the spring boot
     * server
     * 
     * @param args
     */
    public static void main(String[] args) {

        setDBInfoFromUser();

        SpringApplication.run(LibraryExpenseTrackerApplication.class, args);

    }

}
