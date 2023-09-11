package com.indium.bankingapp;

import com.indium.bankingapp.model.Account;


import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceLnkListImpl;
import com.indium.bankingapp.service.AccountServiceTreeMapImpl;
import com.indium.bankingapp.service.AccountServiceTreeSetImpl;

import java.util.Collection;
import java.util.Scanner;

public class BankingAppMain {

    public static void main(String[] args) {
//        AccountService accountService = new AccountxServiceArrListImpl();
//        AccountService accountService = new AccountServiceHashMapImpl();
//        AccountService accountService = new AccountServiceHashSetImpl();
//        AccountService accountService = new AccountServiceLnkListImpl();
//        AccountService accountService = new AccountServiceTreeMapImpl();
        AccountService accountService = new AccountServiceTreeSetImpl();



        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n Banking App Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. View Account");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("6. Exit");
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
                    exit = true;
                    System.out.println("Exiting the Banking App !");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }



    private static void createAccount(AccountService accountService, Scanner scanner) {
        System.out.println("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        System.out.println("Enter Account Holder Name: ");
        String accountHolderName = scanner.next();
        System.out.println("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.println("Enter Account Type: ");
        String type = scanner.next();
        Account newAccount = new Account(0, accountNumber, accountHolderName, balance, type);
        boolean created = accountService.createAccount(newAccount);
        if (created) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Failed to create account. Account number already exists.");
        }
    }

    private static void viewAccount(AccountService accountService, Scanner scanner) {
        System.out.print("Enter Account ID to get details: ");
        int idToGet = scanner.nextInt();
        Account account = accountService.getAccount(idToGet);
        if (account != null) {
            account.displayAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewAllAccounts(AccountService accountService) {
        Collection<Account> allAccounts = accountService.getAllAccounts();
        if (!allAccounts.isEmpty()) {
            System.out.println("All Accounts:");
            for (Account acc : allAccounts) {
                acc.displayAccountInfo();
            }
        } else {
            System.out.println("No accounts found.");
        }
    }


    private static void updateAccount(AccountService accountService, Scanner scanner) {
        System.out.println("Enter Account ID to update:");
        int updateAccountId = scanner.nextInt();
        Account updateAccount = accountService.getAccount(updateAccountId);

        if (updateAccount != null) {
            System.out.println("Enter New Account Holder Name:");
            String updatedName = scanner.next();
            System.out.println("Enter New Balance:");
            double updatedBalance = scanner.nextDouble();
            System.out.println("Enter New Account Type:");
            String updatedType = scanner.next();

            updateAccount.setAccountHolderName(updatedName);
            updateAccount.setBalance(updatedBalance);
            updateAccount.setType(updatedType);

            boolean updated = accountService.updateAccount(updateAccountId, updateAccount);
            if (updated) {
                System.out.println("Account updated successfully.");
            } else {
                System.out.println("Failed to update account.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deleteAccount(AccountService accountService, Scanner scanner) {
        System.out.println("Enter Account ID to delete: ");
        int deleteAccountId = scanner.nextInt();
        Account deleteAccount = accountService.getAccount(deleteAccountId);

        if (deleteAccount != null) {
            boolean deleted = accountService.deleteAccount(deleteAccount);
            if (deleted) {
                System.out.println("Account deleted successfully!");
            } else {
                System.out.println("Failed to delete account.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}
