package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class AccountServiceTreeMapImpl implements AccountService {

    private Map<Integer, Account> accounts = new TreeMap<>();
    private int nextAccountId = 1;

    @Override
    public boolean createAccount(Account account) {
        for (Account existingAccount : accounts.values()) {
            if (existingAccount.getAccountNumber() == account.getAccountNumber()) {
                return false;
            }
        }
        account.setAccountId(nextAccountId++);
        accounts.put(account.getAccountId(), account);
        return true;
    }

    @Override
    public boolean updateAccount(int id, Account updatedAccount) {
        Account existingAccount = accounts.get(id);
        if (existingAccount != null) {
            accounts.put(id, updatedAccount);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean deleteAccount(Account account) {
        for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
            if (entry.getValue().equals(account)) {
                accounts.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }

    @Override
    public Account getAccount(int id) {
        return accounts.get(id);
    }

    @Override
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}
