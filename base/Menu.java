package base;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {

    // -- == [[ STATIC VARIABLES ]] == -- \\

    // Initialize static scanner that will be used throughout the project.
    // This scanner will be closed when the exit menu is selected and the program
    // terminates.

    public static Scanner scnr = new Scanner(System.in);

    public static HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>(); // The submenus hashmap.

    // -- == [[ VARIABLES ]] == -- \\

    private String menuName; // The name of the menu/submenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public Menu(String menuNameString) {
        // Set menuName to string provided
        menuName = menuNameString;
    }

    // -- == [[ GETTERS/SETTERS ]] == -- \\

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    // -- == [[ METHODS ]] == -- \\

    public void printMenuName() {
        // Print the menu's name
        System.out.println(String.format("\n%s\n", menuName));
    }

    public void printSubMenus() {

        // Loops through subMenus hashmap and prints the menu number and menu name

        for (int i = 1; i < subMenus.size(); i++) { // Start at 1 to skip main menu from printing
            Menu menu = subMenus.get(i);
            System.out.println(String.format("\t%s: %s", i, menu.getMenuName()));
        }

    }

    public static void promptToSwitchToSubmenu() {

        // Prompts user to switch to sub menu

        System.out.println();
        System.out.print("Please select a menu to switch to: ");

        try {

            // Reads menuNum from input

            int menuNum = scnr.nextInt();
            scnr.nextLine();

            // Validates menu number

            if (menuNum <= 0 || menuNum > (subMenus.size() - 1)) {
                throw new Error(
                        String.format("Please provide a valid number between 1 and %s!", (subMenus.size() - 1)));
            }

            // Gets menu from subMenus hashmap using menuNum and switches to it
            subMenus.get(menuNum).switchToMenu();

        } catch (InputMismatchException e) {

            // If user provides anything other than an integer then:
            // warn them of their error
            // throw away next tokens from scanner
            // Reprompt user to switch to submenu

            System.out.println(String.format("Please provide a valid number between 1 and %s!", (subMenus.size() - 1)));
            scnr.nextLine();
            promptToSwitchToSubmenu();

        } catch (Error e) {

            // If user provides an invalid integer (between 1 and subMenus.size() - 1) then:
            // Print the error
            // Reprompt user to switch to submenu

            System.out.println(e.getMessage());
            promptToSwitchToSubmenu();

        }

    }

    // Method that all menus/submenus must implement
    public abstract void switchToMenu();

}
