package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class AccountServiceTreeSetImpl implements AccountService {

    private Set<Account> accounts = new TreeSet<>();
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
            accounts.add(updatedAccount);
            return true;
        } else {
            return false;
        }
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
