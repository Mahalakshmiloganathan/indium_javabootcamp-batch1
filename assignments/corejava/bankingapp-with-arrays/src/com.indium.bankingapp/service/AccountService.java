package com.indium.bankingapp.service;


import com.indium.bankingapp.model.Account;

public interface AccountService {
     boolean createAccount(Account account);
     boolean updateAccount(int id, Account account);
     boolean deleleAccount(Account account);
     Account getAccount(int id);
     Account[] getAllAccounts();
}
