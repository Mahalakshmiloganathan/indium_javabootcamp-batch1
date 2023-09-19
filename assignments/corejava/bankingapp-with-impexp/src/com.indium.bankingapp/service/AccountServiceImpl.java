package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.io.*;
import java.util.*;

public class AccountServiceImpl<T extends Account> implements AccountService<T> {

    private Map<Integer, T> accounts = new HashMap<>();
    private int nextAccountId = 1;

    @Override
    public boolean createAccount(T account) throws Exception {
        try {
            if (account == null || accounts.containsValue(account)) {
                return false;
            }
            account.setAccountId(nextAccountId++);
            accounts.put(account.getAccountId(), account);
            exportAccounts();
            return true;
        } catch (NullPointerException | IllegalStateException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean updateAccount(int id, T updatedAccount) throws Exception {
        try {
            if (!accounts.containsKey(id) || updatedAccount == null) {
                return false;
            }
            updatedAccount.setAccountId(id);
            accounts.put(id, updatedAccount);
            return true;
        } catch (NullPointerException | IllegalStateException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean deleteAccount(T account) throws Exception {
        try {
            if (account == null || !accounts.containsValue(account)) {
                return false;
            }
            accounts.remove(account.getAccountId());
            return true;
        } catch (NullPointerException | UnsupportedOperationException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public T getAccount(int id) throws Exception {
        try {
            return accounts.get(id);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Collection<T> getAllAccounts() throws Exception {
        try {
            return accounts.values();
        } catch (NullPointerException | UnsupportedOperationException e) {
            throw new Exception(e.getMessage());
        }
    }


    @Override
    // a] No of accounts which has balance more than 1 lac
    public long countAccountsWithBalanceMoreThanOneLac() throws Exception {
        long count = 0;
        try {
            for (T account : accounts.values()) {
                if (account.getBalance() > 100000) {
                    count++;
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return count;
    }

    // b] Show no of account by account type
    public Map<String, String> countAccountsByAccountType() throws Exception {
        Map<String, String> accountTypeCount = new HashMap<>();
        try {
            for (T account : accounts.values()) {
                String accountType = account.getAccountType();
                String count = accountTypeCount.getOrDefault(accountType, "0");
                accountTypeCount.put(accountType, String.valueOf(Integer.parseInt(count) + 1));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return accountTypeCount;
    }


    // c] Show no of accounts by account type with sorting
    public Map<String, Long> countAccountsByAccountTypeSorted() throws Exception {
        Map<String, Long> accountTypeCount = new HashMap<>();
        try {
            for (T account : accounts.values()) {
                String accountType = account.getAccountType();
                accountTypeCount.put(accountType, accountTypeCount.getOrDefault(accountType, 0L) + 1);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return accountTypeCount;
    }



    // d] Show avg balance by account type
    public Map<String, Double> averageBalanceByAccountType() throws Exception {
        Map<String, Double> avgBalanceByType = new HashMap<>();
        Map<String, Double> sumBalanceByType = new HashMap<>();
        Map<String, Long> countByType = new HashMap<>();

        try {
            for (T account : accounts.values()) {
                String accountType = account.getAccountType();
                double balance = account.getBalance();

                sumBalanceByType.put(accountType, sumBalanceByType.getOrDefault(accountType, 0.0) + balance);
                countByType.put(accountType, countByType.getOrDefault(accountType, 0L) + 1);
            }

            for (String accountType : sumBalanceByType.keySet()) {
                double sum = sumBalanceByType.get(accountType);
                long count = countByType.get(accountType);
                avgBalanceByType.put(accountType, sum / count);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return avgBalanceByType;
    }

    // e] List account ids whose account name contains given name
    public List<String> getAccountIdsByAccountNameContains(String name) throws Exception {
        List<String> accountIds = new ArrayList<>();
        try {
            for (T account : accounts.values()) {
                if (account.getAccountHolderName().contains(name)) {
                    accountIds.add(account.getAccountNumber());
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return accountIds;
    }

    @Override
    public void importAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader("./input/input.txt"))) {
            String line;
            int accountId = 1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accountNumber = parts[0];
                    String accountHolderName = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    T account = (T) new Account(accountId, accountNumber, accountHolderName, balance, type);
                    accounts.put(accountId, account);
                    accountId++;
                }
            }
            System.out.println("Accounts imported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while importing accounts.");
        }
    }


    @Override
    public void exportAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./output/output.txt"))) {
            for (Map.Entry<Integer, T> entry : accounts.entrySet()) {
                Account account = entry.getValue();
                writer.println(account.getAccountId() + "," +
                        account.getAccountNumber() + "," +
                        account.getAccountHolderName() + "," +
                        account.getBalance() + "," +
                        account.getAccountType());
            }

            System.out.println("Export completed successfully."); // Debug statement
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


