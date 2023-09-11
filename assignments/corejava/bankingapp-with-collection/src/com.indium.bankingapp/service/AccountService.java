package com.indium.bankingapp.service;


import com.indium.bankingapp.model.Account;

import java.util.Collection;

public interface AccountService {
     boolean createAccount(Account account);
     boolean updateAccount(int id, Account account);
     boolean deleteAccount(Account account);
     Account getAccount(int id);
     Collection<Account> getAllAccounts();
}
