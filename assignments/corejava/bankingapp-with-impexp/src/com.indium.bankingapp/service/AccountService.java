package com.indium.bankingapp.service;

import com.indium.bankingapp.model.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AccountService<T extends Account> {

     boolean createAccount(T account) throws Exception;

     boolean updateAccount(int id, T updatedAccount) throws Exception;

     boolean deleteAccount(T account) throws Exception;

     T getAccount(int id) throws Exception;

     Collection<T> getAllAccounts() throws Exception;

     long countAccountsWithBalanceMoreThanOneLac() throws Exception;

     Map<String, String> countAccountsByAccountType() throws Exception;

     Map<String, Long> countAccountsByAccountTypeSorted() throws Exception;

     Map<String, Double> averageBalanceByAccountType() throws Exception;

     List<String> getAccountIdsByAccountNameContains(String name) throws Exception;
     void importAccounts();

     void exportAccounts();
}
