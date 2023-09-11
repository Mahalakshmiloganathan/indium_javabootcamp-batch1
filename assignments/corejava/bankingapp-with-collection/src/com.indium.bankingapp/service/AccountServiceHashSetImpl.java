package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AccountServiceHashSetImpl implements AccountService {

    private Set<Account> accounts = new HashSet<>();
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
        Account existingAccount = getAccount(id);
        if (existingAccount != null) {
            accounts.remove(existingAccount);
            accounts.add(updatedAccount);
            return true;
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
