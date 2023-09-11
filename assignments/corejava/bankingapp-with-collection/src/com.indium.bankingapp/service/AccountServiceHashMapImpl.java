package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccountServiceHashMapImpl implements AccountService {

    private Map<Integer, Account> accounts = new HashMap<>();
    private int nextAccountId = 1;

    @Override
    public boolean createAccount(Account account) {
        if (account == null || accounts.containsValue(account)) {
            return false;
        }
        account.setAccountId(nextAccountId++);
        accounts.put(account.getAccountId(), account);
        return true;
    }

    @Override
    public boolean updateAccount(int id, Account updatedAccount) {
        if (!accounts.containsKey(id) || updatedAccount == null) {
            return false;
        }
        updatedAccount.setAccountId(id);
        accounts.put(id, updatedAccount);
        return true;
    }

    @Override
    public boolean deleteAccount(Account account) {
        if (account == null || !accounts.containsValue(account)) {
            return false;
        }
        accounts.remove(account.getAccountId());
        return true;
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
