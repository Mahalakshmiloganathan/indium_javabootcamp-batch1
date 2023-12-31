package org.example.service;



import org.example.db.AccountDAO;
import org.example.db.DatabaseConnection;
import org.example.model.Account;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountServiceImpl implements AccountService {
    private Connection connection;
    private Map<Integer, Account> accounts = new HashMap<>();
    private AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public AccountServiceImpl(Connection connection) {
        this.connection = DatabaseConnection.getConnection();
    }


    @Override
    public boolean createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    @Override
    public boolean updateAccount(int id, Account account) {
        return accountDAO.updateAccount(id, account);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return accountDAO.deleteAccount(account);
    }

    @Override
    public Account getAccount(int id) {
        return accountDAO.getAccount(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    @Override
    public int countAccountsWithBalanceGreaterThan(double balance) {
        try {
            String sql = "SELECT COUNT(*) FROM bankaccount WHERE balance > ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setDouble(1, balance);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Map<String, Integer> countAccountsByAccountType() {
        Map<String, Integer> accountTypeCounts = new HashMap<>();

        try {
            String sql = "SELECT account_type, COUNT(*) FROM bankaccount GROUP BY account_type";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String accountType = rs.getString("account_type");
                    int count = rs.getInt("count");
                    accountTypeCounts.put(accountType, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountTypeCounts;
    }

    @Override
    public Map<String, Integer> countAccountsByAccountTypeSorted() {
        Map<String, Integer> accountTypeCounts = new HashMap<>();

        try {
            String sql = "SELECT account_type, COUNT(*) AS count FROM bankaccount GROUP BY account_type ORDER BY count DESC";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String accountType = rs.getString("account_type");
                    int count = rs.getInt("count");
                    accountTypeCounts.put(accountType, count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountTypeCounts;
    }

    @Override
    public Map<String, Double> calculateAverageBalanceByAccountType() {
        Map<String, Double> avgBalanceByAccountType = new HashMap<>();

        try {
            String sql = "SELECT account_type, AVG(balance) AS avg_balance FROM bankaccount GROUP BY account_type";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String accountType = rs.getString("account_type");
                    double avgBalance = rs.getDouble("avg_balance");
                    avgBalanceByAccountType.put(accountType, avgBalance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avgBalanceByAccountType;
    }

    @Override
    public List<Integer> findAccountIdsByAccountNameSubstring(String accountNameSubstring) {
        List<Integer> accountIds = new ArrayList<>();

        try {
            String sql = "SELECT account_id FROM bankaccount WHERE account_name LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, "%" + accountNameSubstring + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    accountIds.add(accountId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountIds;
    }

    @Override
    public void importAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader("./input/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accountNumber = parts[0];
                    String accountHolderName = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String type = parts[3];

                    int accountId = generateNewAccountId();

                    createAccount(new Account(accountId, accountNumber, accountHolderName, balance, type));
                }
            }
            System.out.println("Accounts imported successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while importing accounts.");
        }
    }


    private int generateNewAccountId() {
        int newAccountId = 1;

        try {
            String sql = "SELECT MAX(account_id) FROM bankaccount";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    newAccountId = rs.getInt(1) + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newAccountId;
    }

    @Override
    public void exportAccounts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("./output/output.txt"))) {
            List<Account> accounts = getAllAccounts();
            for (Account account : accounts) {
                writer.println(account.getAccountId() + "," +
                        account.getAccountNumber() + "," +
                        account.getAccountHolderName() + "," +
                        account.getBalance() + "," +
                        account.getAccountType());
            }
            System.out.println("Export completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

