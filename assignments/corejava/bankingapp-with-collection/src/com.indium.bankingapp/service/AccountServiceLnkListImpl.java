package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.LinkedList;

public class AccountServiceLnkListImpl implements AccountService {
    private LinkedList<Account> accounts = new LinkedList<>();
    private int nextAccountId = 1;

    @Override
    public boolean createAccount(Account account) {
        for (Account existingAccount : accounts) {
            if (existingAccount.getAccountNumber() == account.getAccountNumber()) {
                return false;
            }
        }
        account.setAccountId(nextAccountId++);
        accounts.add(account);
        return true;
    }

    @Override
    public boolean updateAccount(int id, Account updatedAccount) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountId() == id) {
                accounts.set(i, updatedAccount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accounts.remove(account);
    }

    @Override
    public Account getAccount(int id) {
        for (Account account : accounts) {
            if (account.getAccountId() == id) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accounts;
    }


}
