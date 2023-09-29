package org.example;


import org.example.dao.AccountDao;
import org.example.db.DatabaseConnection;
import org.example.model.Account;
import org.example.service.AccountService;
import org.example.service.AccountServiceImpl;

import java.sql.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BankingAppMain {
    private static Connection connection;

    public static void main(String[] args) {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database. Exiting...");
            return;
        }else{
            DatabaseConnection dbConnection = new DatabaseConnection(connection);
            dbConnection.createTable();
        }
        AccountDao accountDAO = new AccountDao(connection);
        AccountService accountService = new AccountServiceImpl(accountDAO);
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("Banking App Menu:");
            System.out.println("1] Add Account");
            System.out.println("2] View All Accounts");
            System.out.println("3] View Account");
            System.out.println("4] Update Account");
            System.out.println("5] Delete Account");
            System.out.println("6] Print Statistics");
            System.out.println("7] Import");
            System.out.println("8] Export");
            System.out.println("9] Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount(accountService, scanner);
                    break;
                case 2:
                    viewAllAccounts(accountService);
                    break;
                case 3:
                    viewAccount(accountService, scanner);
                    break;
                case 4:
                    updateAccount(accountService, scanner);
                    break;
                case 5:
                    deleteAccount(accountService, scanner);
                    break;
                case 6:
                    printStatistics(accountService);
                    break;
                case 7:
                    importAccounts();
                    break;

                case 8:
                    exportAccounts();
                    break;
                case 9:
                    System.out.println("Exiting the application.");
                    exit = true;
                    scanner.close();
                    closeConnection();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private static void createAccount(AccountService accountService, Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter account name: ");
        String accountName = scanner.next();
        System.out.print("Enter account type: ");
        String accountType = scanner.next();
        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();

        Account account = new Account(0, accountNumber, accountName, balance, accountType);

        boolean success = accountService.createAccount(account);

        if (success) {
            System.out.println("Account added successfully!");
        } else {
            System.out.println("Account could not be added.");
        }
    }


    private static void viewAllAccounts(AccountService accountService) {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("List of Accounts:");
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
    }

    private static void viewAccount(AccountService accountService, Scanner scanner) {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        Account account = accountService.getAccount(accountId);

        if (account != null) {
            System.out.println("Account Details:");
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void updateAccount(AccountService accountService, Scanner scanner) {
        System.out.print("Enter account ID to update: ");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter new account name: ");
        String newAccountName = scanner.nextLine();
        System.out.print("Enter new account type: ");
        String newAccountType = scanner.nextLine();
        System.out.print("Enter new balance: ");
        double newBalance = scanner.nextDouble();

        Account updatedAccount = new Account(accountId, "", newAccountName, newBalance, newAccountType);

        boolean success = accountService.updateAccount(accountId, updatedAccount);

        if (success) {
            System.out.println("Account updated successfully!");
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deleteAccount(AccountService accountService, Scanner scanner) {
        System.out.print("Enter account ID to delete: ");
        int accountId = scanner.nextInt();

        boolean success = accountService.deleteAccount(new Account(accountId, "", "", 0, ""));

        if (success) {
            System.out.println("Account deleted successfully!");
        } else {
            System.out.println("Account not found.");
        }
    }
    private static void printStatistics(AccountService accountService) {
        System.out.println("Statistics Menu:");

        int count = accountService.countAccountsWithBalanceGreaterThan(100000.0);
        System.out.println("a] No of accounts which has balance more than 1 lac: " + count);

        Map<String, Integer> accountTypeCounts = accountService.countAccountsByAccountType();
        System.out.println("b] Show no of account by account type:");
        for (Map.Entry<String, Integer> entry : accountTypeCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Map<String, Integer> sortedAccountTypeCounts = accountService.countAccountsByAccountTypeSorted();
        System.out.println("c] Show no of accounts by account type with sorting:");
        for (Map.Entry<String, Integer> entry : sortedAccountTypeCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Map<String, Double> avgBalanceByAccountType = accountService.calculateAverageBalanceByAccountType();
        System.out.println("d] Show avg balance by account type:");
        for (Map.Entry<String, Double> entry : avgBalanceByAccountType.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("e] List account ids whose account name contains given name. Enter account name substring: ");
        String accountNameSubstring = scanner.next();
        List<Integer> accountIds = accountService.findAccountIdsByAccountNameSubstring(accountNameSubstring);
        System.out.println("Account IDs with account name containing '" + accountNameSubstring + "':");
        for (int accountId : accountIds) {
            System.out.println(accountId);
        }
    }
    private static void importAccounts() {
        AccountServiceImpl accountService = new AccountServiceImpl(connection);
        accountService.importAccounts();
        System.out.println("Accounts imported successfully!");
    }

    private static void exportAccounts() {
        AccountServiceImpl accountService = new AccountServiceImpl(connection);
        accountService.exportAccounts();
        System.out.println("Accounts exported successfully!");
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




