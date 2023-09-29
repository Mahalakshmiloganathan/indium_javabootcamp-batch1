import org.example.dao.AccountDao;
import org.example.db.DatabaseConnection;
import org.example.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;


public class AccountDaoTest {

    private Connection connection;
    private AccountDao accountDAO;

    @Before
    public void setUp() {
        connection = DatabaseConnection.getConnection();
        accountDAO = new AccountDao(connection);
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account(31, "ICI890", "Kumar", 1000.0, "Savings");
        assertTrue(accountDAO.createAccount(account));
    }

    @Test
    public void testUpdateAccount() {
        Account account = new Account(31, "IOB23", "Maha", 1000.0, "Savings");
        account.setAccountHolderName("Renu");
        assertTrue(accountDAO.updateAccount(account.getAccountId(), account));
    }

    @Test
    public void testGetAccount() {
        Account retrievedAccount = accountDAO.getAccount(31);
        assertNotNull(retrievedAccount);
        assertEquals(retrievedAccount.getAccountHolderName(), retrievedAccount.getAccountHolderName());
    }


    @Test
    public void testDeleteAccount() {
        Account account = new Account(31, "SBI123", "Maha", 1000.0, "Savings");
        assertTrue(accountDAO.deleteAccount(account));
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = accountDAO.getAllAccounts();
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }

}
