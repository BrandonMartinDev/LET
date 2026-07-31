package dev.bmtech.libraryexpensetracker;

import dev.bmtech.libraryexpensetracker.models.Transaction;
import dev.bmtech.libraryexpensetracker.models.Transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TransactionTest {

        // -- == [[ Runs before each test ]] == -- \\

        @BeforeEach
        void setup() {

                // Resets transaction hashmap to empty
                // Later stage will use Mockito for database testing

                Transaction.TransactionList.clear();

        }

        // -- == [[ Tests for validators ]] == -- \\

        @Test
        void idValidatorOnlyAccepts7DigitInteger() {

                int negative = -1;
                int negative7Digit = -1_234_567;
                int below7Digits = 999_999;
                int above7Digits = 10_000_000;

                int valid7DigitLow = 1_000_000;
                int valid7DigitHigh = 9_999_999;

                assertEquals(false, Transaction.isIDNumValid(negative));
                System.out.println("idValidatorOnlyAccepts7DigitInteger: Negative number correctly returned false");

                assertEquals(false, Transaction.isIDNumValid(negative7Digit));
                System.out.println(
                                "idValidatorOnlyAccepts7DigitInteger: Negative 7-digit number correctly returned false");

                assertEquals(false, Transaction.isIDNumValid(below7Digits));
                System.out.println("idValidatorOnlyAccepts7DigitInteger: 6 digits or less correctly returned false");

                assertEquals(false, Transaction.isIDNumValid(above7Digits));
                System.out.println("idValidatorOnlyAccepts7DigitInteger: 8 Digits or more correctly returned false");

                assertEquals(true, Transaction.isIDNumValid(valid7DigitLow));
                System.out.println("idValidatorOnlyAccepts7DigitInteger: 1_000_000 correctly returned true");

                assertEquals(true, Transaction.isIDNumValid(valid7DigitHigh));
                System.out.println("idValidatorOnlyAccepts7DigitInteger: 9_999_999 correctly returned true");

        }

        @Test
        void titleValidatorOnlyAccepts1To150CharacterString() {

                String emptyString = "";
                String char151 = "A".repeat(151);

                String validchar1 = "A";
                String validchar150 = "A".repeat(150);

                assertEquals(false, Transaction.isTitleValid(emptyString));
                System.out.println(
                                "titleValidatorOnlyAccepts1To150CharacterString: Empty string correctly returned false");

                assertEquals(false, Transaction.isTitleValid(char151));
                System.out.println(
                                "titleValidatorOnlyAccepts1To150CharacterString: 151+ character string correctly returned false");

                assertEquals(true, Transaction.isTitleValid(validchar1));
                System.out
                                .println("titleValidatorOnlyAccepts1To150CharacterString: 1 character string correctly returned true");

                assertEquals(true, Transaction.isTitleValid(validchar150));
                System.out.println(
                                "titleValidatorOnlyAccepts1To150CharacterString: 150 character string correctly returned true");

        }

        @Test
        void descriptionValidatorOnlyAccepts1To1000CharacterString() {

                String emptyString = "";
                String char1001 = "A".repeat(1001);
                String validchar1 = "A";
                String validchar1000 = "A".repeat(1000);

                assertEquals(false, Transaction.isDescriptionValid(emptyString));
                System.out.println(
                                "descriptionValidatorOnlyAccepts1To1000CharacterString: empty string correctly returned false");

                assertEquals(false, Transaction.isDescriptionValid(char1001));
                System.out.println(
                                "descriptionValidatorOnlyAccepts1To1000CharacterString: 1001+ character string correctly returned false");

                assertEquals(true, Transaction.isDescriptionValid(validchar1));
                System.out.println(
                                "descriptionValidatorOnlyAccepts1To1000CharactfalseerString: 1 character string correctly returned true");

                assertEquals(true, Transaction.isDescriptionValid(validchar1000));
                System.out.println(
                                "descriptionValidatorOnlyAccepts1To1000CharacterString: 1000 character string correctly returned true");

        }

        @Test
        void dateStringValidatorOnlyAcceptsValidFormat() {

                String emptyString = "";
                String randomString = "something";

                String yearLow = "999-01-01";
                String yearHigh = "10000-01-01";

                String monthLow = "1970-00-01";
                String monthHigh = "1970-13-01";

                String dayLow = "1970-01-00";
                String dayHigh = "1970-01-32";

                String validFormat = "1970-01-01";

                assertEquals(false, Transaction.isDateStringValid(emptyString));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: empty string correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(randomString));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: random string correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(yearLow));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '999-01-01' correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(yearHigh));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '10000-01-01' correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(monthLow));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '1970-00-01' correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(monthHigh));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '1970-13-01' correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(dayLow));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '1970-01-00' correctly returns false");

                assertEquals(false, Transaction.isDateStringValid(dayHigh));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '1970-01-32' correctly returns false");

                assertEquals(true, Transaction.isDateStringValid(validFormat));
                System.out.println("dateStringValidatorOnlyAcceptsValidFormat: '1970-01-01' correctly returns true");

        }

        @Test
        void amountValidatorOnlyAcceptsPositiveNumbers() {

                double negative = -1;

                double validZero = 0;
                double validHigh = 2_147_483_647;

                assertEquals(false, Transaction.isAmountValid(negative));
                System.out.println(
                                "amountValidatorOnlyAcceptsPositiveNumbers: negative number correctly returns false");

                assertEquals(true, Transaction.isAmountValid(validZero));
                System.out.println("amountValidatorOnlyAcceptsPositiveNumbers: 0 correctly returns true");

                assertEquals(true, Transaction.isAmountValid(validHigh));
                System.out.println("amountValidatorOnlyAcceptsPositiveNumbers: max number correctly returns true");

        }

        @Test
        void typeValidatorOnlyAcceptsDepositOrExpense() {

                String emptyString = "";
                String randomString = "w876gaHSDFuiy";

                String validDonationPartial = "d";
                String validDonationCap = "DONATION";
                String validDonationLower = "donation";

                String validExpensePartial = "e";
                String validExpenseCap = "EXPENSE";
                String validExpenseLower = "expense";

                assertEquals(false, Transaction.isTransactionTypeStringValid(emptyString));
                System.out.println("typeValidatorOnlyAcceptsDepositOrExpense: empty string correctly returns false");

                assertEquals(false, Transaction.isTransactionTypeStringValid(randomString));
                System.out.println("typeValidatorOnlyAcceptsDepositOrExpense: random string correctly returns false");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validDonationPartial));
                System.out.println(
                                "typeValidatorOnlyAcceptsDepositOrExpense: donation partial string correctly returns true");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validDonationCap));
                System.out.println(
                                "typeValidatorOnlyAcceptsDepositOrExpense: donation capitalized string correctly returns true");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validDonationLower));
                System.out
                                .println("typeValidatorOnlyAcceptsDepositOrExpense: donation lowercased string correctly returns true");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validExpensePartial));
                System.out.println(
                                "typeValidatorOnlyAcceptsDepositOrExpense: expense partial string correctly returns true");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validExpenseCap));
                System.out
                                .println("typeValidatorOnlyAcceptsDepositOrExpense: expense capitalized string correctly returns true");

                assertEquals(true, Transaction.isTransactionTypeStringValid(validExpenseLower));
                System.out
                                .println("typeValidatorOnlyAcceptsDepositOrExpense: expense lowercased string correctly returns true");

        }

        @Test
        void getDateFromStringReturnsLocalDate() {

                String invalidString = "12345678";
                LocalDate unixEpoch = LocalDate.of(1970, 1, 1);

                assertEquals(null, Transaction.getDateFromString(invalidString));
                System.out.println("getDateFromStringReturnsLocalDate: invalid string correctly returns null");

                assertEquals(unixEpoch, Transaction.getDateFromString("1970-01-01"));
                System.out.println(
                                "getDateFromStringReturnsLocalDate: unix epoch correctly returns LocalDate '1970-01-01'");

        }

        @Test
        void getTransactionTypeFromStringReturnsTransactionTypeEnum() {

                String invalidString = "deposit";

                String donationPartial = "d";
                String donationCap = "DONATION";
                String donationLower = "donation";

                String expensePartial = "e";
                String expenseCap = "EXPENSE";
                String expenseLower = "expense";

                assertEquals(null, Transaction.getTransactionTypeFromString(invalidString));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: invalid string correctly returns null");

                assertEquals(TransactionType.DONATION, Transaction.getTransactionTypeFromString(donationPartial));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: donation partial string correctly returns TransactionType.DONATION");

                assertEquals(TransactionType.DONATION, Transaction.getTransactionTypeFromString(donationCap));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: donation capitalized string correctly returns TransactionType.DONATION");

                assertEquals(TransactionType.DONATION, Transaction.getTransactionTypeFromString(donationLower));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: donation lowercased string correctly returns TransactionType.DONATION");

                assertEquals(TransactionType.EXPENSE, Transaction.getTransactionTypeFromString(expensePartial));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: expense partial string correctly returns TransactionType.EXPENSE");

                assertEquals(TransactionType.EXPENSE, Transaction.getTransactionTypeFromString(expenseCap));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: expense capitalized string correctly returns TransactionType.EXPENSE");

                assertEquals(TransactionType.EXPENSE, Transaction.getTransactionTypeFromString(expenseLower));
                System.out.println(
                                "getTransactionTypeFromStringReturnsTransactionTypeEnum: expense lowercased string correctly returns TransactionType.EXPENSE");

        }

        // -- == [[ Tests for custom actions ]] == -- \\

        @Test
        void getTotalExpensesReturnsValidTotal() {

                /*
                 *
                 * Creates 3 dummy transactions
                 * Sets all 3 transaction amounts to a random double between 0-100;
                 * Sets transaction A to donation and B/C to expense
                 *
                 */

                Transaction transactionA;
                Transaction transactionB;
                Transaction transactionC;

                double transactionAAmount = Math.floor(Math.random() * 100);
                double transactionBAmount = Math.floor(Math.random() * 100);
                double transactionCAmount = Math.floor(Math.random() * 100);

                TransactionType transactionAType = TransactionType.DONATION;
                TransactionType transactionBType = TransactionType.EXPENSE;
                TransactionType transactionCType = TransactionType.EXPENSE;

                try {

                        // Creates 3 dummy transactions

                        transactionA = new Transaction("Dummy transaction A", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionAAmount,
                                        transactionAType);

                        transactionB = new Transaction("Dummy transaction B", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionBAmount,
                                        transactionBType);

                        transactionC = new Transaction("Dummy transaction C", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionCAmount,
                                        transactionCType);

                        transactionA.setId(1);
                        transactionB.setId(2);
                        transactionC.setId(3);

                        // Adds transactions to list

                        Transaction.TransactionList.put(transactionA.getId(), transactionA);
                        Transaction.TransactionList.put(transactionB.getId(), transactionB);
                        Transaction.TransactionList.put(transactionC.getId(), transactionC);

                        // Gets calculations and asserts correct value

                        double sumOfExpenses = (transactionB.getAmount() + transactionC.getAmount());
                        double totalExpenses = Transaction.getTotalExpenses();

                        System.out.println(
                                        "getTotalExpensesReturnsValidTotal: Transaction A (donation) amount: "
                                                        + transactionA.getAmount());

                        System.out.println(
                                        "getTotalExpensesReturnsValidTotal: Transaction B (expense) amount: "
                                                        + transactionB.getAmount());

                        System.out.println(
                                        "getTotalExpensesReturnsValidTotal: Transaction C (expense) amount: "
                                                        + transactionC.getAmount());

                        System.out.println("getTotalExpensesReturnsValidTotal: Sum of B and C (expense) amounts: "
                                        + sumOfExpenses);
                        System.out.println("getTotalExpensesReturnsValidTotal: getTotalExpenses return value: "
                                        + totalExpenses);

                        assertEquals(sumOfExpenses, totalExpenses);
                        System.out.println("getTotalExpensesReturnsValidTotal: correctly returns expenses amount");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("getTotalExpensesReturnsValidTotal: Creating transactions resulted in an exception");
                }

        }

        @Test
        void getTotalDonationsReturnsValidTotal() {

                /*
                 *
                 * Creates 3 dummy transactions
                 * Sets all 3 transaction amounts to a random double between 0-100;
                 * Sets transaction A to donation and B/C to expense
                 *
                 */

                Transaction transactionA;
                Transaction transactionB;
                Transaction transactionC;

                double transactionAAmount = Math.floor(Math.random() * 100);
                double transactionBAmount = Math.floor(Math.random() * 100);
                double transactionCAmount = Math.floor(Math.random() * 100);

                TransactionType transactionAType = TransactionType.DONATION;
                TransactionType transactionBType = TransactionType.EXPENSE;
                TransactionType transactionCType = TransactionType.EXPENSE;

                try {

                        // Creates 3 dummy transactions

                        transactionA = new Transaction("Dummy transaction A", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionAAmount,
                                        transactionAType);

                        transactionB = new Transaction("Dummy transaction B", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionBAmount,
                                        transactionBType);

                        transactionC = new Transaction("Dummy transaction C", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionCAmount,
                                        transactionCType);

                        transactionA.setId(1);
                        transactionB.setId(2);
                        transactionC.setId(3);

                        // Adds transactions to list

                        Transaction.TransactionList.put(transactionA.getId(), transactionA);
                        Transaction.TransactionList.put(transactionB.getId(), transactionB);
                        Transaction.TransactionList.put(transactionC.getId(), transactionC);

                        // Gets calculations and asserts correct value

                        double sumOfDonations = transactionA.getAmount();
                        double totalDonations = Transaction.getTotalDonations();

                        System.out.println(
                                        "getTotalDonationsReturnsValidTotal: Transaction A (donation) amount: "
                                                        + transactionA.getAmount());

                        System.out.println(
                                        "getTotalDonationsReturnsValidTotal: Transaction B (expense) amount: "
                                                        + transactionB.getAmount());

                        System.out.println(
                                        "getTotalDonationsReturnsValidTotal: Transaction C (expense) amount: "
                                                        + transactionC.getAmount());

                        System.out.println("getTotalDonationsReturnsValidTotal: Total donations amount: "
                                        + sumOfDonations);

                        System.out.println("getTotalDonationsReturnsValidTotal: getTotalDonations return value: "
                                        + totalDonations);

                        assertEquals(sumOfDonations, totalDonations);
                        System.out.println("getTotalDonationsReturnsValidTotal: correctly returns donations amount");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("getTotalDonationsReturnsValidTotal: Creating transactions resulted in an exception");
                }

        }

        @Test
        void getNetWorthReturnsValidNetWorth() {

                /*
                 *
                 * Creates 3 dummy transactions
                 * Sets all 3 transaction amounts to a random double between 0-100;
                 * Sets transaction A to donation and B/C to expense
                 *
                 */

                Transaction transactionA;
                Transaction transactionB;
                Transaction transactionC;

                double transactionAAmount = Math.floor(Math.random() * 100);
                double transactionBAmount = Math.floor(Math.random() * 100);
                double transactionCAmount = Math.floor(Math.random() * 100);

                TransactionType transactionAType = TransactionType.DONATION;
                TransactionType transactionBType = TransactionType.EXPENSE;
                TransactionType transactionCType = TransactionType.EXPENSE;

                try {

                        // Creates 3 dummy transactions

                        transactionA = new Transaction("Dummy transaction A", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionAAmount,
                                        transactionAType);

                        transactionB = new Transaction("Dummy transaction B", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionBAmount,
                                        transactionBType);

                        transactionC = new Transaction("Dummy transaction C", "N/A", LocalDate.of(1970, 1, 1),
                                        transactionCAmount,
                                        transactionCType);

                        // Adds transactions to list

                        Transaction.TransactionList.put(transactionA.getId(), transactionA);
                        Transaction.TransactionList.put(transactionB.getId(), transactionB);
                        Transaction.TransactionList.put(transactionC.getId(), transactionC);

                        // Gets calculations and asserts correct value

                        double sumOfDonations = Transaction.getTotalDonations();
                        double sumOfExpenses = Transaction.getTotalExpenses();
                        double donationsMinusExpenses = (sumOfDonations - sumOfExpenses);
                        double netWorth = Transaction.getNetWorth();

                        System.out.println(
                                        "getNetWorthReturnsValidNetWorth: Transaction A (donation) amount: "
                                                        + transactionA.getAmount());

                        System.out.println(
                                        "getNetWorthReturnsValidNetWorth: Transaction B (expense) amount: "
                                                        + transactionB.getAmount());

                        System.out.println(
                                        "getNetWorthReturnsValidNetWorth: Transaction C (expense) amount: "
                                                        + transactionC.getAmount());

                        System.out.println("getNetWorthReturnsValidNetWorth: Sum of donations: " + sumOfDonations);
                        System.out.println("getNetWorthReturnsValidNetWorth: Sum of expenses: " + sumOfExpenses);
                        System.out.println("getNetWorthReturnsValidNetWorth: getNetWorth return value: " + netWorth);

                        assertEquals(donationsMinusExpenses, netWorth);
                        System.out.println("getNetWorthReturnsValidNetWorth: correctly returns net worth amount");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("getTotalDonationsReturnsValidTotal: Creating transactions resulted in an exception");
                }

        }

        // -- == [[ Tests for CRUD operations ]] == -- \\

        @Test
        void getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile() {

                try {

                        // File paths that do not exist

                        String randomString = "98ahdf9uhsa";
                        String randomFilePath = "src/test/resources/randomfilepath.png";
                        String randomTextFilePath = "src/test/resources/randomtextfilepath.txt";

                        // Path that does exist, but not a text file

                        String randomValidFilePath = "src/test/resources/validpath.abcd";

                        // Text file that does exist with invalid formatted transactions

                        String invalidTextFilePath = "src/test/resources/invalid.txt";

                        // Text file that does exist with 20 valid formatted transactions

                        String validTextFilePath = "src/test/resources/valid.txt";

                        assertEquals(null, Transaction.getTransactionsFromTextFile(randomString));
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: random string correctly returned null");

                        assertEquals(null, Transaction.getTransactionsFromTextFile(randomFilePath));
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: random non-existing file path correctly returned null");

                        assertEquals(null, Transaction.getTransactionsFromTextFile(randomTextFilePath));
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: random non-existing text file path correctly returned null");

                        assertEquals(null, Transaction.getTransactionsFromTextFile(randomValidFilePath));
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: random existing file path correctly returned null");

                        ArrayList<Transaction> invalidTextFileTransactions = Transaction
                                        .getTransactionsFromTextFile(invalidTextFilePath);

                        assertEquals(new ArrayList<Transaction>(), invalidTextFileTransactions);
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: invalid text file path correctly returned empty array list");

                        ArrayList<Transaction> validTextFileTransactions = Transaction
                                        .getTransactionsFromTextFile(validTextFilePath);

                        assertEquals(20, validTextFileTransactions.size());
                        System.out.println(
                                        "getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: valid text file path correctly returned an arraylist with length 20");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("getTransactionsFromTextFileGetsTransactionsOnlyFromValidTextFile: Resulted in an exception");
                }

        }

        @Test
        void addTransactionToListAddsTransactionToList() {

                try {

                        // Creates dummy transaction object;

                        Transaction dummyTransaction = new Transaction(
                                        "Dummy transaction",
                                        "Dummy desc",
                                        LocalDate.of(1970, 1, 1),
                                        33.33,
                                        TransactionType.DONATION);

                        // Takes size of transaction list before adding transaction

                        int beforeSize = Transaction.TransactionList.size();

                        // Adds dummy transaction to list via addTransactionToList method

                        Transaction.addTransactionToList(dummyTransaction);

                        // Takes size of transaction list after adding transaction and
                        // checks to make sure afterSize is 1 more than before size

                        int afterSize = Transaction.TransactionList.size();

                        if ((afterSize - 1) != beforeSize) {
                                throw new Exception("Transaction was not added to list");
                        }

                        // Loops through every entry in TransactionList hashmap

                        boolean dummyTransactionExistsInTransactionList = false;

                        for (Map.Entry<Integer, Transaction> entry : Transaction.TransactionList.entrySet()) {

                                // Gets current transaction from entry and changes
                                // dummyTransactionExistsInTransactionList based on whether currentTransaction
                                // is dummyTransaction

                                Transaction currentTransaction = entry.getValue();

                                if (dummyTransaction == currentTransaction) {
                                        dummyTransactionExistsInTransactionList = true;
                                }

                        }

                        // Asserts that dummy transaction exists in transaction list

                        assertEquals(true, dummyTransactionExistsInTransactionList);

                        System.out.println(
                                        "addTransactionToListAddsTransactionToList: Dummy transaction was found in list successfully");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("addTransactionToListAddsTransactionToList: Resulted in an exception");
                }

        }

        @Test
        void getTransactionByIDGetsCorrectTransaction() {

                try {

                        // Creates dummy transaction object

                        Transaction dummyTransaction = new Transaction(
                                        "Dummy transaction",
                                        "Dummy desc",
                                        LocalDate.of(1970, 1, 1),
                                        33.33,
                                        TransactionType.DONATION);

                        // Adds dummy transaction to list via addTransactionToList method

                        Transaction.addTransactionToList(dummyTransaction);

                        // Gets transaction from list via id

                        Transaction transactionFromList = Transaction.getTransactionByID(dummyTransaction.getId());

                        // Asserts that dummy transaction object is the same as the transaction
                        // retrieved from list

                        assertEquals(true, (dummyTransaction == transactionFromList));

                        System.out.println(
                                        "getTransactionByIDGetsCorrectTransaction: Dummy transaction was retrieved from the list successfully");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("getTransactionByIDGetsCorrectTransaction: Resulted in an exception");
                }

        }

        @Test
        void removeTransactionFromListRemovesCorrectTransaction() {

                try {

                        // Creates dummy transaction object

                        Transaction dummyTransaction = new Transaction(
                                        "Dummy transaction",
                                        "Dummy desc",
                                        LocalDate.of(1970, 1, 1),
                                        33.33,
                                        TransactionType.DONATION);

                        // Adds dummy transaction to list via addTransactionToList method

                        Transaction.addTransactionToList(dummyTransaction);

                        // Takes size of transaction list before removing transaction

                        int beforeSize = Transaction.TransactionList.size();

                        // Removes transaction from list via id

                        boolean removedSuccessfully = Transaction.removeTransactionFromList(dummyTransaction.getId());

                        // Asserts that dummy transaction object is the same as the transaction
                        // retrieved from list

                        assertEquals(true, removedSuccessfully);

                        System.out.println(
                                        "removeTransactionFromListRemovesCorrectTransaction: removeTransactionFromList correctly returned true");

                        // Takes size of transaction list after removing transaction
                        // checks to make sure afterSize is 1 less than before size

                        int afterSize = Transaction.TransactionList.size();

                        if ((afterSize + 1) != beforeSize) {
                                throw new Exception("Transaction was not removed from list");
                        }

                        // Loops through every entry in TransactionList hashmap

                        boolean dummyTransactionExistsInTransactionList = false;

                        for (Map.Entry<Integer, Transaction> entry : Transaction.TransactionList.entrySet()) {

                                // Gets current transaction from entry and changes
                                // dummyTransactionExistsInTransactionList based on whether currentTransaction
                                // is dummyTransaction

                                Transaction currentTransaction = entry.getValue();

                                if (dummyTransaction == currentTransaction) {
                                        dummyTransactionExistsInTransactionList = true;
                                }

                        }

                        // Asserts that dummy transaction does NOT exist in transaction list

                        assertEquals(false, dummyTransactionExistsInTransactionList);

                        System.out.println(
                                        "removeTransactionFromListRemovesCorrectTransaction: Dummy transaction was NOT found in list successfully");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("removeTransactionFromListRemovesCorrectTransaction: Resulted in an exception");
                }

        }

        @Test
        void editTransactionInListEditsCorrectTransactionInfo() {

                try {

                        // Creates 2 dummy transaction objects with different info

                        Transaction dummyTransactionA = new Transaction(
                                        "Dummy transaction A",
                                        "Dummy desc A",
                                        LocalDate.of(1970, 1, 1),
                                        33.33,
                                        TransactionType.DONATION);

                        Transaction dummyTransactionB = new Transaction(
                                        "Dummy transaction B",
                                        "Dummy desc B",
                                        LocalDate.of(2026, 7, 9),
                                        66.66,
                                        TransactionType.EXPENSE);

                        // Adds dummy transaction A to list via addTransactionToList method

                        Transaction.addTransactionToList(dummyTransactionA);

                        // Edits transaction in list via id

                        Transaction editedTransactionInfo = Transaction.editTransactionInList(dummyTransactionA.getId(),
                                        dummyTransactionB);

                        // Asserts editTransactionInList returns transaction B's info, not A

                        assertEquals(true, editedTransactionInfo == dummyTransactionB);

                        System.out.println(
                                        "editTransactionInListEditsCorrectTransactionInfo: editTransactionInfo returns transaction B's info successfully");

                        // Loops through every entry in TransactionList hashmap

                        boolean dummyTransactionAExistsInTransactionList = false;
                        boolean dummyTransactionBExistsInTransactionList = false;

                        for (Map.Entry<Integer, Transaction> entry : Transaction.TransactionList.entrySet()) {

                                // Gets current transaction from entry and changes
                                // dummyTransactionAExistsInTransactionList or
                                // dummyTransactionBExistsInTransactionList based on whether currentTransaction
                                // is either

                                Transaction currentTransaction = entry.getValue();

                                if (dummyTransactionA == currentTransaction) {
                                        dummyTransactionAExistsInTransactionList = true;
                                }

                                if (dummyTransactionB == currentTransaction) {
                                        dummyTransactionBExistsInTransactionList = true;
                                }

                        }

                        // Asserts that dummy transaction A does NOT exist in transaction list and
                        // dummy transadction B does exist in transaction list

                        assertEquals(false, dummyTransactionAExistsInTransactionList);

                        System.out.println(
                                        "editTransactionInListEditsCorrectTransactionInfo: Dummy transaction A was NOT found in list successfully");

                        assertEquals(true, dummyTransactionBExistsInTransactionList);

                        System.out.println(
                                        "editTransactionInListEditsCorrectTransactionInfo: Dummy transaction B was found in list successfully");

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                        fail("editTransactionInListEditsCorrectTransactionInfo: Resulted in an exception");
                }

        }

}