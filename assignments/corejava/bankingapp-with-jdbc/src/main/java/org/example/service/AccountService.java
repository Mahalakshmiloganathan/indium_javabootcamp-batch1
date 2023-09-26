package org.example.service;



import org.example.model.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
     boolean createAccount(Account account);

     boolean updateAccount(int id, Account account);

     boolean deleteAccount(Account account);

     Account getAccount(int id);

     List<Account> getAllAccounts();
     int countAccountsWithBalanceGreaterThan(double balance);

     Map<String, Integer> countAccountsByAccountType();

     Map<String, Integer> countAccountsByAccountTypeSorted();

     Map<String, Double> calculateAverageBalanceByAccountType();

     List<Integer> findAccountIdsByAccountNameSubstring(String accountNameSubstring);

     void importAccounts();

     void exportAccounts();
}
