package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public long countAccountsWithBalanceMoreThanOneLac() throws Exception {
        try {
            return accounts.values().stream()
                    .filter(account -> account.getBalance() > 100000)
                    .count();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, String> countAccountsByAccountType() throws Exception {
        try {
            return accounts.values().stream()
                    .collect(Collectors.groupingBy(
                            Account::getAccountType,
                            Collectors.counting()
                    ))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().toString()
                    ));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Long> countAccountsByAccountTypeSorted() throws Exception {
        try {
            return accounts.values().stream()
                    .collect(Collectors.groupingBy(
                            Account::getAccountType,
                            TreeMap::new,
                            Collectors.counting()
                    ));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Map<String, Double> averageBalanceByAccountType() throws Exception {
        try {
            return accounts.values().stream()
                    .collect(Collectors.groupingBy(
                            Account::getAccountType,
                            Collectors.averagingDouble(Account::getBalance)
                    ));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<String> getAccountIdsByAccountNameContains(String name) throws Exception {
        try {
            return accounts.values().stream()
                    .filter(account -> account.getAccountHolderName().contains(name))
                    .map(Account::getAccountNumber)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    }
