import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Bank {
    Connection connection;

    public void getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/bank";
        String username = "root";
        String password = "root";

        connection =
                DriverManager.getConnection(url, username, password);

    }
    public void createAccountTable() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
        "CREATE TABLE IF NOT EXISTS accounts (" +
                "account_number INT PRIMARY KEY," +
                "account_holder VARCHAR(100) NOT NULL," +
                "balance DOUBLE NOT NULL" +
                ")")) {

        ps.executeUpdate();
}
    }
    public double getBalance(int accountNumber) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT balance FROM accounts WHERE account_number = ?")) {

            ps.setInt(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }

    throw new SQLException("Account not found.");
}
    public void viewAccount(int accountNumber) throws SQLException {
    try (PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM accounts WHERE account_number = ?")) {

        ps.setInt(1, accountNumber);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String accountHolder = rs.getString("account_holder");
                double balance = rs.getDouble("balance");

                System.out.println(
                        "Account Number: " + accountNumber +
                        ", Account Holder: " + accountHolder +
                        ", Balance: " + balance
                );
            } else {
                System.out.println("Account not found.");
            }
        }
    }
}
public void displayAccounts() throws SQLException {
    try (PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM accounts")) {

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int accountNumber = rs.getInt("account_number");
                String accountHolder = rs.getString("account_holder");
                double balance = rs.getDouble("balance");

                System.out.println(
                        "Account Number: " + accountNumber +
                        ", Account Holder: " + accountHolder +
                        ", Balance: " + balance
                );
            }
        }
    }
}
    public void insertAccount(int accountNumber, String accountHolder, double balance) throws SQLException {
       try (PreparedStatement ps = connection.prepareStatement(
        "INSERT INTO accounts (account_number, account_holder, balance) VALUES (?, ?, ?)")) {

    ps.setInt(1, accountNumber);
    ps.setString(2, accountHolder);
    ps.setDouble(3, balance);

    ps.executeUpdate();
}
        
}
  public void updateAccountBalance(int accountNumber, double newBalance) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
        "UPDATE accounts SET balance = ? WHERE account_number = ?")) {

    ps.setDouble(1, newBalance);
    ps.setInt(2, accountNumber);

    ps.executeUpdate();
}
    }
    
    public void deleteAccount(int accountNumber) throws SQLException {
    try (PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM accounts WHERE account_number = ?")) {

        ps.setInt(1, accountNumber);

        ps.executeUpdate();
    }
}

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}