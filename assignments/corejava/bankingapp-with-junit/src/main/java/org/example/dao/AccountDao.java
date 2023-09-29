package org.example.dao;

import org.example.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private Connection connection;

    public AccountDao(Connection connection) {
        this.connection = connection;
    }

    public boolean createAccount(Account account) {
        try {
            String sql = "INSERT INTO bankaccount (account_number, account_name, account_type, balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, account.getAccountNumber());
                pstmt.setString(2, account.getAccountHolderName());
                pstmt.setString(3, account.getAccountType());
                pstmt.setDouble(4, account.getBalance());

                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(int id, Account account) {
        try {
            String sql = "UPDATE bankaccount SET account_name = ?, account_type = ?, balance = ? WHERE account_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, account.getAccountHolderName());
                pstmt.setString(2, account.getAccountType());
                pstmt.setDouble(3, account.getBalance());
                pstmt.setInt(4, id);

                int rowsUpdated = pstmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(Account account) {
        try {
            String sql = "DELETE FROM bankaccount WHERE account_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, account.getAccountId());

                int rowsDeleted = pstmt.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccount(int id) {
        try {
            String sql = "SELECT * FROM bankaccount WHERE account_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    String accountNumber = rs.getString("account_number");
                    String accountName = rs.getString("account_name");
                    String accountType = rs.getString("account_type");
                    double balance = rs.getDouble("balance");

                    return new Account(accountId, accountNumber, accountName, balance, accountType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        try {
            String sql = "SELECT * FROM bankaccount";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int accountId = rs.getInt("account_id");
                    String accountNumber = rs.getString("account_number");
                    String accountName = rs.getString("account_name");
                    String accountType = rs.getString("account_type");
                    double balance = rs.getDouble("balance");

                    Account account = new Account(accountId, accountNumber, accountName, balance, accountType);
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

}
