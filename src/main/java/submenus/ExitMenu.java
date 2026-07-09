package submenus;

import base.*;

public class ExitMenu extends SubMenu { // Class that extends submenu

    // -- == [[ CONSTRUCTORS ]] == -- \\

    public ExitMenu() {
        // Passes menuNameString to parent class
        super("Exit Library Expense Tracker");
    }

    // -- == [[ METHODS ]] == -- \\

    public void switchToMenu() {

        // Clears console and prints menu name

        System.out.print("\033[H\033[2J");
        printMenuName();

        // Closes scanner and tells user program is exiting

        scnr.close();
        System.out.println("Exiting program, bye bye!");

    }

}