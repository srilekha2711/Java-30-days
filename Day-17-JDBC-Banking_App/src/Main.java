import java.sql.SQLException;
import java.util.*;
class Main{
    public static void main(String[] args){        Bank bank = new Bank();
        try{
            bank.getConnection();
            bank.createAccountTable();
        }
        catch(SQLException e){
            System.out.println("Error connecting to the database: " + e.getMessage());
            return;
        }
        Scanner sc= new Scanner(System.in);
        int choice ;

        do{
            System.out.println("===== BANKING SYSTEM =====\r\n" + //
                                "\r\n" + //
                                "1. Create Account\r\n" + //
                                "2. View Account\r\n" + //
                                "3. Deposit\r\n" + //
                                "4. Withdraw\r\n" + //
                                "5. View All Accounts\r\n" + //
                                "6. Delete Account\r\n" + //
                                "7. Exit");
            System.out.print("Enter your choice: ");
            double balance;
            choice = sc.nextInt();
            switch(choice){ 
                
                case 1:
                    System.out.print("Enter account number: ");
                    int accountNumber = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter account holder name: ");
                    String accountHolder = sc.nextLine();
                    System.out.print("Enter initial balance: ");
                    balance = sc.nextDouble();
                    if (balance <= 0) {
                        System.out.println("Initial balance must be greater than zero.");
                        break;
                    }

                    try {
                        bank.insertAccount(accountNumber, accountHolder, balance);
                        System.out.println("Account created successfully.");
                    } catch (SQLException e) {
                        System.out.println("Error creating account: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter account number to view: ");
                    int viewAccountNumber = sc.nextInt();
                    try {
                        bank.viewAccount(viewAccountNumber);
                    } catch (SQLException e) {
                        System.out.println("Error viewing account: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter account number to deposit into: ");
                    int depositAccountNumber = sc.nextInt();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = sc.nextDouble();

                    if (depositAmount <= 0) {
                        System.out.println("Deposit amount must be greater than zero.");
                        break;
                    }

                    try {
                        balance = bank.getBalance(depositAccountNumber);
                        double amount = balance + depositAmount;

                        bank.updateAccountBalance(depositAccountNumber, amount);

                        System.out.println("Deposit successful.");
                    } catch (SQLException e) {
                        System.out.println("Error depositing: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter account number to withdraw from: ");
                    int withdrawAccountNumber = sc.nextInt();
                    System.out.print("Enter amount to withdraw: ");
            
                    double withdrawAmount = sc.nextDouble();

                    if (withdrawAmount <= 0) {
                        System.out.println("Withdrawal amount must be greater than zero.");
                        break;
                    }

                    try {
                        balance = bank.getBalance(withdrawAccountNumber);

                        if (balance < withdrawAmount) {
                            System.out.println("Insufficient funds.");
                            break;
                        }

                        bank.updateAccountBalance(
                                withdrawAccountNumber,
                                balance - withdrawAmount
                        );

                        System.out.println("Withdrawal successful.");

                    } catch (SQLException e) {
                        System.out.println("Error withdrawing: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        bank.displayAccounts();
                    } catch (SQLException e) {
                        System.out.println("Error displaying accounts: " + e.getMessage());
                    }
                    break;
                case 6:
                     System.out.print("Enter account number to delete: ");
                     int deleteAccountNumber = sc.nextInt();
                     try {
                         bank.deleteAccount(deleteAccountNumber);
                         System.out.println("Account deleted successfully.");
                     } catch (SQLException e) {
                         System.out.println("Error deleting account: " + e.getMessage());
                     }
                    break;
                case 7:
                    try {
                        bank.closeConnection();
                    } catch (SQLException e) {
                        System.out.println("Error closing connection: " + e.getMessage());
                    }
                   
                    System.out.println("Exiting...");
            }

        }while(choice!=7);
        sc.close();

    }
}