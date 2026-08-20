import java.util.*;
class InvalidPinException extends Exception{
    public InvalidPinException(String s){
        super(s);
    }
}
class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(String s){
        super(s);
    }
}
class InvalidWithdrawalAmountException extends Exception{
    public InvalidWithdrawalAmountException(String s){
        super(s);
    }
}
class DepositAmountException extends Exception{
    public DepositAmountException(String s){
        super(s);
    }
}
class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String s){
        super(s);
    }
}
class Account {
    private int accountNumber;
    private String name;
    private String pin;
    private double balance;
    Account(int accountNumber, String name, String pin, double balance){
        this.accountNumber=accountNumber;
        this.name=name;
        this.pin=pin;
        this.balance=balance;
    }
    public int getAccountNumber(){
        return this.accountNumber;
    }
    public String getName(){
         return this.name;
    }
    public String getPin(){
        return this.pin;
    }
    public double getBalance(){
        return this.balance;
    }

    public void setPin(String pin){
        this.pin=pin;
    }
    public void setBalance(double balance){
        this.balance=balance;
    }
    
    public void depositAmount(double deposit){
             System.out.println("Amount deposited: "+deposit);
             this.balance+=deposit;
             System.out.println("Current balance "+ getBalance() );
    }
    public void withdrawAmount(double withdraw){
           System.out.println("Amount withdrawn: "+withdraw);
           this.balance-=withdraw;
           System.out.println("Current balance: "+getBalance());
    }
    
}
class Transaction {
        private int accountNumber;
        private String type;
        private double amount;
        private String description;

        Transaction(int accountNumber, String type, double amount, String description) {
            this.accountNumber = accountNumber;
            this.type = type;
            this.amount = amount;
            this.description = description;
        }

        public int getAccountNumber() {
            return accountNumber;
        }

        @Override
        public String toString() {
            return type + " : ₹" + amount + " - " + description;
        }
}
class Main{
    public static void withdraw(double amount, Account obj,
        List<Transaction> transactions)
        throws InvalidWithdrawalAmountException,
               InsufficientBalanceException {

            if(amount <= 0) {
                throw new InvalidWithdrawalAmountException(
                        "Invalid withdrawal amount");
            }
            else if(amount > obj.getBalance()) {
                throw new InsufficientBalanceException(
                        "Insufficient balance");
            }
            else if(amount % 100 != 0) {
                throw new InvalidWithdrawalAmountException(
                        "Withdrawal must be in multiples of ₹100.");
            }
            else {
                obj.withdrawAmount(amount);

                transactions.add(
                    new Transaction(
                        obj.getAccountNumber(),
                        "WITHDRAW",
                        amount,
                        "Cash withdrawal"
                    )
                );
            }
}
    public static void depositAmt(double amount,
        Account obj,
        List<Transaction> transactions)
        throws DepositAmountException {

            if(amount <= 0) {
                throw new DepositAmountException(
                        "Invalid amount to deposit");
            }

            obj.depositAmount(amount);

            transactions.add(
                new Transaction(
                    obj.getAccountNumber(),
                    "DEPOSIT",
                    amount,
                    "Cash deposit"
                )
            );
}
public static void transfer(double amount,
        Account sender,
        Account target,
        List<Transaction> transactions)
        throws InvalidWithdrawalAmountException,
               InsufficientBalanceException {

    if(amount <= 0) {
        throw new InvalidWithdrawalAmountException(
                "Invalid transfer amount");
    }

    if(amount > sender.getBalance()) {
        throw new InsufficientBalanceException(
                "Insufficient balance");
    }

    sender.withdrawAmount(amount);
    target.depositAmount(amount);

    transactions.add(
        new Transaction(
            sender.getAccountNumber(),
            "TRANSFER",
            amount,
            "Sent to " + target.getName()
        )
    );

    transactions.add(
        new Transaction(
            target.getAccountNumber(),
            "TRANSFER",
            amount,
            "Received from " + sender.getName()
        )
    );
}
    public static  void main(String[] args){
           Scanner sc=new Scanner(System.in);
           int accountNumber;
           int choice;
           double amount;
           String pin;
           boolean exit = false;
           List<Account> accounts=new ArrayList<>();
           List<Transaction> transactions=new ArrayList<>();
           accounts.add(new Account(101, "Ammu", "1234", 25000));
           accounts.add(new Account(102, "Rahul", "5678", 15000));
           Account obj=null;
           do{

            int cnt=0;
            boolean found=false;
            boolean found_ac=false;
            while(cnt < 3){
                    try {
                        System.out.println("Enter Account Number: ");
                        accountNumber = sc.nextInt();

                        sc.nextLine();  // consume newline

                        System.out.println("Enter pin: ");
                        pin = sc.nextLine();

                        found_ac = false;
                        found = false;

                        for(Account act : accounts){
                            if(act.getAccountNumber() == accountNumber){
                                found_ac = true;
                                obj = act;

                                if(act.getPin().equals(pin)){
                                    found = true;
                                }
                                break;
                            }
                        }

                        if(!found_ac){
                            throw new AccountNotFoundException("There is no such account");
                        }

                        if(found){
                            break;
                        }

                        cnt++;

                        throw new InvalidPinException("Invalid pin");

                    }
                    catch(AccountNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    catch(InvalidPinException e){
                        System.out.println(e.getMessage());

                        if(cnt < 3){
                            System.out.println((3-cnt) + " attempts left!");
                        }
                    }
                    
                }
            
            if(found_ac && !found){
                System.out.println("Account locked!!");
                continue;
            }
            boolean logout = false;
            while(!logout){
            System.out.println("===== ATM =====\r\n" +
                                "1. Check Balance\r\n" + 
                                "2. Deposit\r\n" + 
                                "3. Withdraw\r\n" + 
                                "4. Transfer Money\r\n" +
                                "5. Change PIN\r\n" + 
                                "6. Mini Statement\r\n" + 
                                "7. Logout\r\n" + 
                                "8. Exit");

            System.out.println("Enter your choice: ");
            choice=sc.nextInt();
            if(choice == 1){
                System.out.println("Balance: "+ obj.getBalance());
            }
            else if(choice == 2){
                    System.out.println("Enter amount: ");
                    amount=sc.nextDouble();

                    try {
                        depositAmt(amount, obj, transactions);
                    }
                    catch(DepositAmountException e) {
                        System.out.println(e.getMessage());
                    }
                }
            else if(choice == 3){
                        System.out.println("Enter amount: ");
                        amount=sc.nextDouble();

                        try {
                            withdraw(amount, obj, transactions);
                        }
                        catch(InvalidWithdrawalAmountException e) {
                            System.out.println(e.getMessage());
                        }
                        catch(InsufficientBalanceException e) {
                            System.out.println(e.getMessage());
                        }
                        
                    }
            else if(choice == 4) {
                    try {
                        System.out.println("Enter target account: ");
                        accountNumber = sc.nextInt();

                        found = false;
                        Account target = null;

                        for(Account ac : accounts) {
                            if(ac.getAccountNumber() == accountNumber) {
                                found = true;
                                target = ac;
                                break;
                            }
                        }

                        if(!found) {
                            throw new AccountNotFoundException(
                                    "Target account not found");
                        }

                        if(target == obj) {
                            System.out.println(
                                    "Target cannot be the same account.");
                            continue;
                        }

                        System.out.println("Enter amount: ");
                        amount = sc.nextDouble();

                        transfer(amount, obj, target, transactions);

                        System.out.println("Transfer successful!");

                    }
                    catch(AccountNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    catch(InvalidWithdrawalAmountException e) {
                        System.out.println(e.getMessage());
                    }
                    catch(InsufficientBalanceException e) {
                        System.out.println(e.getMessage());
                    }
                }
           else if(choice == 5){

                try{
                    System.out.print("Enter current pin: ");
                    sc.nextLine();
                    pin = sc.nextLine();

                    if(!obj.getPin().equals(pin)){
                        throw new InvalidPinException("Current PIN is incorrect");
                    }

                    System.out.println("Enter New Pin: ");
                    String newPin = sc.nextLine();

                    System.out.println("Confirm Pin: ");
                    String confirmPin = sc.nextLine();

                    boolean validPin = newPin.length() == 4;

                    for(char c : newPin.toCharArray()){
                        if(!Character.isDigit(c)){
                            validPin = false;
                            break;
                        }
                    }

                    if(validPin && newPin.equals(confirmPin)){
                        obj.setPin(newPin);
                        System.out.println("PIN changed successfully!");
                    }
                    else{
                        System.out.println("PIN must contain exactly 4 digits and confirmation must match.");
                    }
                }
                catch(InvalidPinException e){
                    System.out.println(e.getMessage());
                }
            }
                   
                
            
            else if(choice == 6) {

                    System.out.println("\n===== MINI STATEMENT =====");

                    boolean hasTransactions = false;

                    for(Transaction t : transactions) {

                        if(t.getAccountNumber() == obj.getAccountNumber()) {
                            System.out.println(t);
                            hasTransactions = true;
                        }
                    }

                    if(!hasTransactions) {
                        System.out.println("No transactions available.");
                    }

                    System.out.println(
                        "Current Balance: ₹" + obj.getBalance()
                    );
                }
            else if(choice ==7){
                System.out.println(" Logging out");
                logout=true;
            }
            else if(choice == 8){
                System.out.println("Exited.");
                logout = true;
                exit = true;
            }
            else{
                System.out.println(" Invalid choice");
            }}
            if(exit) {
                    break;
                }
           }while(!exit);
           sc.close();
    }
} 
