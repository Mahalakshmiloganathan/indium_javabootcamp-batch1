package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

public class AccountServiceArrImpl implements AccountService {
    private Account[] accounts = new Account[100];
    private int numberOfAccounts = 0;
    private int accountId = 1;

    @Override
    public boolean createAccount(Account account) {
        for (int i = 0; i < numberOfAccounts; i++) {
            if (accounts[i].getAccountNumber() == account.getAccountNumber()) {
                return false;
            }
        }
        account.setAccountId(accountId++);

        if (numberOfAccounts < accounts.length) {
            accounts[numberOfAccounts] = account;
            numberOfAccounts++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateAccount(int id, Account updatedAccount) {
        for (int i = 0; i < numberOfAccounts; i++) {
            if (accounts[i].getAccountId() == id) {
                accounts[i] = updatedAccount;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleleAccount(Account account) {
        for (int i = 0; i < numberOfAccounts; i++) {
            if (accounts[i].equals(account)) {
                for (int j = i; j < numberOfAccounts - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                numberOfAccounts--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Account getAccount(int id) {
        for (int i = 0; i < numberOfAccounts; i++) {
            if (accounts[i].getAccountId() == id) {
                return accounts[i];
            }
        }
        return null;
    }

    @Override
    public Account[] getAllAccounts() {
        Account[] nonNullAccounts = new Account[numberOfAccounts];
        System.arraycopy(accounts, 0, nonNullAccounts, 0, numberOfAccounts);
        return nonNullAccounts;
    }
}
