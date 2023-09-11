package com.indium.bankingapp.model;

public class Account implements Comparable<Account> {

    private int accountId;
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private String type;

    public Account(int accountId, int accountNumber, String accountHolderName, double balance, String type) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.type = type;
    }
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void displayAccountInfo() {
        System.out.println("Account ID: " + accountId);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
        System.out.println("Account Type: " + type);
    }


    @Override
    public int compareTo(Account otherAccount) {
        return Integer.compare(this.accountId, otherAccount.accountId);
    }
}

