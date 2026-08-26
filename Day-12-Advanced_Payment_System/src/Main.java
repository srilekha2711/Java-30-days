import java.time.LocalDateTime;
import java.util.*;
interface PaymentProcessor{
        PaymentResult process(double amount, PaymentMethod paymentMethod);
}
interface PaymentMethod {

    boolean validate();

    String getName();

}
class PaymentResult{
    private String transactionId;
    private double amount;
    private String status;
    private LocalDateTime timestamp;
    private PaymentMethod paymentMethod;
    PaymentResult(String transactionId, double amount, String status, LocalDateTime timestamp, PaymentMethod paymentMethod){
            this.transactionId=transactionId;
            this.amount=amount;
            this.status=status;
            this.timestamp=timestamp;
            this.paymentMethod=paymentMethod;

    }
    public String getTransactionId() {
    return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setStatus(String status){
        this.status= status;
    }
    @Override
    public String toString(){
        return "Transaction ID: " + transactionId+ "\n"+ 
                        "Amount: " + amount + "\n"+
                        "Method: " + paymentMethod.getName()+ "\n"+ 
                        "Status: " + status+ "\n"+ 
                        "Time: " + timestamp + "\n";
    }
}
class OnlinePaymentProcessor
        implements PaymentProcessor {
        private int transId=1000;
         
    public PaymentResult process(double amount, PaymentMethod paymentMethod) {
        boolean valid = paymentMethod.validate();  
        String transactionId = "TXN" + (++transId);
        if(amount<=0) valid=false;
        LocalDateTime timestamp=LocalDateTime.now();
        if(valid)
        return new PaymentResult(transactionId, amount, "SUCCESS", timestamp, paymentMethod);
        return new PaymentResult(transactionId, amount, "FAILED", timestamp, paymentMethod);
    }
}
class PaymentService {
    private List<PaymentResult> transactions = new ArrayList<>();
    private PaymentProcessor processor;
    PaymentService(PaymentProcessor processor) {
        this.processor = processor;
    }
    public PaymentResult makePayment(
            double amount,
            PaymentMethod method) {

        PaymentResult result = processor.process(amount, method);
        transactions.add(result);

        return result;
    }
    public PaymentResult searchTransaction(String id){
        for(PaymentResult p: transactions){
            if(p.getTransactionId().equals(id)){
                return p;
            }
        }
        System.out.println("Not found");
        return null;

    }
    public void displayTransactions() {
    for(PaymentResult p : transactions) {
        System.out.println(p);
    }
}
    public void getStatistic(){
        long cnt=transactions.stream().count();
        long successful = transactions.stream()
                         .filter( t -> t.getStatus().equals("SUCCESS"))
                         .count();
        long failed = cnt-successful;
        double total = transactions.stream()
                      .mapToDouble( t -> t.getAmount() ).sum();//whole addition
        double successAmount = transactions.stream()
                               .filter(t -> t.getStatus().equals("SUCCESS"))
                               .mapToDouble(t-> t.getAmount())
                               .sum();
    System.out.println("\n===== PAYMENT STATISTICS =====");
    System.out.println("Total transactions: " + cnt);
    System.out.println("Successful: " + successful);
    System.out.println("Failed: " + failed);
    System.out.println("Total amount: " + total);
    System.out.println("Successful amount: " + successAmount);
    }

}
class UPI implements PaymentMethod{
     private String upiId;
     UPI(String upiId){
        this.upiId=upiId;
     }
     public String getName(){
        return "UPI";
     }
     public boolean validate(){
        if(upiId != null && upiId.contains("@") && upiId.indexOf("@")>0)
            return true;
        return false;
     }
}
class CreditCard implements PaymentMethod{
    private String creditNo;
    private String holder;
    private String cvv;
     CreditCard(String creditNo, String holder, String cvv){
        this.creditNo=creditNo;
        this.holder=holder;
        this.cvv=cvv;
     }
     public String getName(){
        return "CreditCard";
     }

     public boolean validate(){
          if (creditNo.length()== 16 && cvv.length() == 3 && !holder.equals("")){
                if(creditNo.matches("\\d{16}") && cvv.matches("\\d{3}"))
                return true;
            return false;
          }
          return false;
     }

}
class DebitCard implements PaymentMethod{
    private String debitNo;
    private String holder;
    private String cvv;
     DebitCard(String debitNo, String holder, String cvv){
        this.debitNo=debitNo;
        this.holder=holder;
        this.cvv=cvv;
     }
     public String getName(){
        return "DebitCard";
     }

     public boolean validate(){
          if (debitNo.length()== 16 && cvv.length() == 3 && !holder.equals("")){
                if(debitNo.matches("\\d{16}") && cvv.matches("\\d{3}"))
                return true;
            return false;
          }
          return false;
     }

}
class Wallet implements PaymentMethod{
    private String walletId;
    Wallet(String walletId){
        this.walletId = walletId;
    }

    public String getName(){
        return "Wallet";
     }
     public boolean validate(){
         if(walletId.length() >= 5)
            return true;
        return false;
     }

}
public class Main {
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int choice;
        PaymentProcessor processor =
        new OnlinePaymentProcessor();
        PaymentService service =
        new PaymentService(processor);
        do{
            
            System.out.println("========== PAYMENT SYSTEM ==========\r\n" + //
                                "\r\n" + //
                                "1. Make Payment\r\n" + //
                                "2. View Transaction History\r\n" + //
                                "3. Search Transaction\r\n" + //
                                "4. Payment Statistics\r\n" + //
                                "5. Refund Payment\r\n" + //
                                "6. Exit\r\n" + //
                                "\r\n" + //
                                "Enter choice:");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1){
                System.out.println("Enter amount:\r\n" + //
                                        "Select payment method:\r\n" + //
                                        "\r\n" + //
                                        "1. UPI\r\n" + //
                                        "2. Credit Card\r\n" + //
                                        "3. Debit Card\r\n" + //
                                        "4. Wallet");
                    double amount= sc.nextDouble();
                    sc.nextLine();
                    int paymentChoice=sc.nextInt();
                    sc.nextLine();
                    PaymentMethod paymentMethod;
                    if( paymentChoice == 1){
                        System.out.println("Enter upiId: ");
                        String upiId=sc.nextLine();
                        paymentMethod = new UPI(upiId);
                        
                    }
                    else if(paymentChoice == 2){
                        System.out.println("Enter Credit card number: ");
                        String cno=sc.nextLine();
                        System.out.println("Enter cardHolder name: ");
                        String holder=sc.nextLine();
                        System.out.println("Enter cvv");
                        String cvv=sc.nextLine();
                        paymentMethod =
                                        new CreditCard(cno, holder, cvv);

                    }
                    else if(paymentChoice == 3){
                        System.out.println("Enter Debit card number: ");
                        String cno=sc.nextLine();
                        System.out.println("Enter cardHolder name: ");
                        String holder=sc.nextLine();
                        System.out.println("Enter cvv");
                        String cvv=sc.nextLine();
                        paymentMethod =
                                        new DebitCard(cno, holder, cvv);
                        

                    }
                    else if(paymentChoice == 4){
                        System.out.println("Enter Wallet id: ");
                        String walletId=sc.nextLine();
                        paymentMethod= new Wallet(walletId);
                        
                    }
                    else{
                        System.out.println("Invalid choice");
                        continue;
                    }
                    PaymentResult result =
                              service.makePayment(amount, paymentMethod);
                        System.out.println(result);


            }

            else if(choice == 2){
                service.displayTransactions();
            }
            else if(choice == 3){
                System.out.println("Enter transaction id to search: ");
                String id=sc.nextLine();
                System.out.println(service.searchTransaction(id));
                
            }
            else if( choice == 4){
                service.getStatistic();
            }
            else if(choice == 5){
                System.out.println("Enter the transaction Id for refund: ");
                String transId=sc.nextLine();
                PaymentResult t = service.searchTransaction(transId);
                if( t == null){
                    System.out.println("There is no such transaction");
        
                }
                else if(t.getStatus().equals("FAILED")){
                    System.out.println("Cannot refund as the transaction is failed ");
                }
                else if(t.getStatus().equals("REFUNDED")){
                    System.out.println("Transaction is already refunded.");
                }
                else{
                t.setStatus("REFUNDED");
                System.out.println(t);
                }
            }
            else if(choice == 6){
                System.out.println("Thankyou. Exited");
            }
            
            else{
                System.out.println("Invalid choice");
            }
        }while(choice != 6);
        sc.close();

    }
}
