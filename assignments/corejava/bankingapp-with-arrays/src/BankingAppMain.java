import com.indium.bankingapp.model.Account;
import com.indium.bankingapp.service.AccountService;
import com.indium.bankingapp.service.AccountServiceArrImpl;


import java.util.Scanner;

public class BankingAppMain {
    public static void main(String[] args) {
        AccountService accountService = new AccountServiceArrImpl();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;  // Initialize a boolean variable to control the loop

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
                    System.out.println("Enter Account Number: ");
                    int accountNumber = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter Account Holder Name: ");
                    String accountHolderName = scanner.nextLine();

                    System.out.println("Enter Initial Balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Enter Account Type: ");
                    String type = scanner.nextLine();

                    Account newAccount = new Account(0, accountNumber, accountHolderName, balance, type);
                    boolean created = accountService.createAccount(newAccount);

                    if (created) {
                        System.out.println("Account created successfully!");
                    } else {
                        System.out.println("Failed to create account. Maximum accounts reached Or Account number already exists");
                    }
                    break;

                case 2:
                    Account[] allAccounts = accountService.getAllAccounts();
                    System.out.println("All Accounts:");

                    for (int i = 0; i < allAccounts.length; i++) {
                        Account currentAccount = allAccounts[i];
                        if (currentAccount != null) {
                            currentAccount.displayAccountInfo();
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter Account ID : ");
                    int viewAccountId = scanner.nextInt();
                    Account viewAccount = accountService.getAccount(viewAccountId);
                    if (viewAccount != null) {
                        viewAccount.displayAccountInfo();
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 4:
                    System.out.println("Enter Account ID to update:");
                    int updateAccountId = scanner.nextInt();
                    Account updateAccount = accountService.getAccount(updateAccountId);
                    if (updateAccount != null) {
                        scanner.nextLine();
                        System.out.println("Enter New Account Holder Name:");
                        String updatedName = scanner.nextLine();
                        System.out.println("Enter New Balance:");
                        double updatedBalance = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Enter New Account Type:");
                        String updatedType = scanner.nextLine();

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
                    break;


                case 5:
                    System.out.println("Enter Account ID to delete: ");
                    int deleteAccountId = scanner.nextInt();
                    Account deleteAccount = accountService.getAccount(deleteAccountId);

                    if (deleteAccount != null) {
                        boolean deleted = accountService.deleleAccount(deleteAccount);
                        if (deleted) {
                            System.out.println("Account deleted successfully!");
                        } else {
                            System.out.println("Failed to delete account.");
                        }
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting the Banking App !");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}