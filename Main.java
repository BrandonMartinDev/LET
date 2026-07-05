import base.*;
import submenus.*;

public class Main {

    public static void main(String args[]) {

        // Create menus and sub menus

        MainMenu mm = new MainMenu();

        DisplayCurrentTransactionListMenu dctlm = new DisplayCurrentTransactionListMenu();
        QuickStatsMenu qsm = new QuickStatsMenu();
        AddTransactionToListMenu attlm = new AddTransactionToListMenu();
        AddTransactionsFromTextFileMenu atftfm = new AddTransactionsFromTextFileMenu();
        EditTransactionMenu etm = new EditTransactionMenu();
        RemoveTransactionFromListMenu rtflm = new RemoveTransactionFromListMenu();
        ExitMenu em = new ExitMenu();

        // Add created menus to submenus hashmap

        Menu.subMenus.put(0, mm);
        Menu.subMenus.put(1, dctlm);
        Menu.subMenus.put(2, qsm);
        Menu.subMenus.put(3, attlm);
        Menu.subMenus.put(4, atftfm);
        Menu.subMenus.put(5, etm);
        Menu.subMenus.put(6, rtflm);
        Menu.subMenus.put(7, em);

        // Switch to MainMenu

        mm.switchToMenu();

    }

}

// TODO:
// Edit Transaction