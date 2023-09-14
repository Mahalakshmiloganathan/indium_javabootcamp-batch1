import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceHashMapImpl;

import java.util.*;

public class BankingAppMain<T extends Account> {
    private AccountService<T> accountService;
    private Scanner scanner;

    public BankingAppMain(AccountService<T> accountService) {
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
    }

    public void run() throws Exception {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nBanking App Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. View Account");
            System.out.println("4. Update Account");
            System.out.println("5. Delete Account");
            System.out.println("6. Print Statistics");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAllAccounts();
                    break;
                case 3:
                    viewAccount();
                    break;
                case 4:
                    updateAccount();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 6:
                    printStatistics();
                    break;

                case 7:
                    exit = true;
                    System.out.println("Exiting the Banking App !");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void createAccount() throws Exception {
        System.out.println("Enter Account Number: ");
        String accountNumber = scanner.next();
        System.out.println("Enter Account Holder Name: ");
        String accountHolderName = scanner.next();
        System.out.println("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.println("Enter Account Type: ");
        String type = scanner.next();
        T newAccount = (T) new Account(0, accountNumber, accountHolderName, balance, type);
        boolean created = accountService.createAccount(newAccount);
        if (created) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Failed to create account. Account number already exists.");
        }
    }

    private void viewAccount() throws Exception {
        System.out.print("Enter Account ID to get details: ");
        int idToGet = scanner.nextInt();
        T account = accountService.getAccount(idToGet);
        if (account != null) {
            account.displayAccountInfo();
        } else {
            System.out.println("Account not found.");
        }
    }

    private void viewAllAccounts() throws Exception {
        Collection<T> allAccounts = accountService.getAllAccounts();
        if (!allAccounts.isEmpty()) {
            System.out.println("All Accounts:");
            for (T acc : allAccounts) {
                acc.displayAccountInfo();
            }
        } else {
            System.out.println("No accounts found.");
        }
    }

    private void updateAccount() throws Exception {
        System.out.println("Enter Account ID to update:");
        int updateAccountId = scanner.nextInt();
        T updateAccount = accountService.getAccount(updateAccountId);

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

    private void deleteAccount() throws Exception {
        System.out.println("Enter Account ID to delete: ");
        int deleteAccountId = scanner.nextInt();
        T deleteAccount = accountService.getAccount(deleteAccountId);

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

    private void printStatistics() throws Exception {
        System.out.println("Printing Statistics:");

        // a] No of accounts which has a balance more than 1 lac
        long accountsWithBalanceMoreThanOneLac = accountService.countAccountsWithBalanceMoreThanOneLac();
        System.out.println("a] No of accounts which have a balance more than 1 lac: " + accountsWithBalanceMoreThanOneLac);

        // b] Show no of accounts by account type
        Map<String, String> accountTypeCount = accountService.countAccountsByAccountType();
        System.out.println("b] Show no of accounts by account type: " + accountTypeCount);

        // c] Show no of accounts by account type with sorting
        Map<String, Long> sortedAccountTypeCount = accountService.countAccountsByAccountTypeSorted();
        System.out.println("c] Show no of accounts by account type with sorting: " + sortedAccountTypeCount);

        // d] Show avg balance by account type
        Map<String, Double> avgBalanceByAccountType = accountService.averageBalanceByAccountType();
        System.out.println("d] Show avg balance by account type: " + avgBalanceByAccountType);

        // e] List account ids whose account name contains given name
        System.out.print("Enter the account name to search: ");
        Scanner sc = new Scanner(System.in);
        String searchName = sc.next();
        List<String> accountIdsWithName = accountService.getAccountIdsByAccountNameContains(searchName);

        if (accountIdsWithName.isEmpty()) {
            System.out.println("No matching account names found.");
        } else {
            System.out.println("e] List account ids whose account name contains '" + searchName + "': " + accountIdsWithName);
        }

    }

    public static void main(String[] args) throws Exception {
        AccountService<Account> accountService = new AccountServiceHashMapImpl<>();
        BankingAppMain<Account> bankingApp = new BankingAppMain<>(accountService);
        bankingApp.run();
    }
}
