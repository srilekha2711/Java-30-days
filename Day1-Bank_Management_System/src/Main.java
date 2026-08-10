import java.util.*;
class BankAccount{
    private int accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(int accountNumber, String accountHolder, double balance)
    {
        this.accountNumber=accountNumber;
        this.accountHolder=accountHolder;
        this.balance=balance;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
     public String getAccountHolder() {
        return accountHolder;
     }

     public double getBalance() {
        return balance;
     }

    

        //methods.

        public boolean deposit(double amount) {
            if (amount > 0) {
                balance +=amount;
                return true;
            }
        
            return false;
        }

        public boolean withdraw(double amount) {
              
            if (amount> 0 && amount<=balance) {
                 balance -=amount;

                return true;
            }

            return false;

        }

        
}

class Main{
      
    public static BankAccount getObjectByAccountNumber(ArrayList<BankAccount> accounts, int accountNumber) {

    
        for (BankAccount account :accounts) {
            
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
       public static void main(String args[]) {

            Scanner sc= new Scanner(System.in);
             
            System.out.println("===== BANK MANAGEMENTSYSTEM =====");
            //System.out.println(" 1.Create Account \n 2.Deposit Money \n 3.Withdraw Money \n 4. Check Balance \n 5.Display Account Details \n 6.Exit");

            int choice;
            int accountNumber;
            String accountHolder;
            double balance;
            ArrayList<BankAccount> accounts = new ArrayList<>();
            BankAccount account = null;
            do {
                System.out.println(" 1.Create Account \n 2.Deposit Money \n 3.Withdraw Money \n 4. Check Balance \n 5.Display Account Details \n 6.Exit");
                System.out.println("Enter your choice");
                choice=sc.nextInt();
                if (choice ==1){
                      System.out.println("Enter Account Number: "   );
                      accountNumber=sc.nextInt();
                      System.out.println("Enter Account Holder: "   );
                      accountHolder=sc.next();
                      sc.nextLine();
                      System.out.println("Enter Initial Balance: "   );
                      balance=sc.nextDouble();
                      if (balance <=0){
                        System.out.println("Initial balance must be greater than zero. Please try again.");
                        continue;
                      }
                      if(getObjectByAccountNumber(accounts, accountNumber) != null){
                        System.out.println("Account with this number already exists. Please try again.");
                        continue;
                      }
                      account = new BankAccount(accountNumber, accountHolder, balance);
                      accounts.add(account);
                      System.out.println("Account created successfully");
                    }
                else if (choice ==2){
                    System.out.println("Enter Account Number: "   );
                    accountNumber=sc.nextInt();
                    account= getObjectByAccountNumber(accounts, accountNumber);
                    if (account ==null ){
                        System.out.println("No account found. Please create one");

                    }
                    else{

                        System.out.println("Enter amount:");
                        
                        double amt= sc.nextDouble();
                        if (account.deposit(amt)){
                            System.out.println("Deposit successful");
                            System.out.println("Current Balance: " + account.getBalance());
                        }
                        else{
                            System.out.println("Deposit failed");
                        }
                    }

            }
            else if (choice ==3){
                    
                    System.out.println("Enter Account Number: "   );
                    accountNumber=sc.nextInt();
                    account= getObjectByAccountNumber(accounts, accountNumber);
                    if (account ==null ){
                        System.out.println("No account found. Please create one");

                    }
                   else{

                        System.out.println("Enter amount:");

                        double amt =sc.nextDouble();

                        if (account.withdraw(amt)){
                            System.out.println("Withdraw successful");
                            System.out.println("Current Balance: " + account.getBalance());
                        }
                        else{
                            System.out.println("Withdraw failed");
                        }
                   }
            }
            else if( choice ==4) {
                
                
                System.out.println("Enter Account Number: "   );
                    accountNumber=sc.nextInt();
                    account= getObjectByAccountNumber(accounts, accountNumber);
                if (account ==null){
                    System.out.println("No account found. Please create one");
                }
                else{
                    System.out.println("Current Balance: " + account.getBalance());
                }
            }
            
            else if (choice ==5){
                
                System.out.println("Enter Account Number: "   );
                    accountNumber=sc.nextInt();
                    account= getObjectByAccountNumber(accounts, accountNumber);
                if (account ==null){
                    System.out.println("No account found. Please create one");
                }
                else{
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Account Holder: " + account.getAccountHolder());
                    System.out.println("Current Balance: " + account.getBalance()); 
                }
            }
            else if (choice ==6){
                System.out.println("Exiting the program. Thank you!");
            }
            else{
                System.out.println("Invalid choice. Please try again.");
            }

       }while (choice !=6);
}
}