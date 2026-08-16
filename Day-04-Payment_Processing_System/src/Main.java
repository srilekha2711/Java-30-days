import java.util.*;

interface PaymentMethod{
    boolean pay(double amount);
    String getDisplayName();
}

class PaymentRecord {
    private PaymentMethod method;
    private double amount;
    private boolean status;

    PaymentRecord(PaymentMethod method, double amount, boolean status) {
        this.method = method;
        this.amount = amount;
        this.status = status;
    }

    public String getSummary() {
        return method.getDisplayName().trim() +"amount: " + amount + "  --  " + (status ? "SUCCESS" : "FAILURE");
    }
}


class CreditCard implements PaymentMethod{
        private String cardNumber;
        private String cardHolder;
        CreditCard(String cardNumber, String cardHolder){
            this.cardNumber = cardNumber;
            this.cardHolder = cardHolder;
        }
        public boolean pay(double amount){
                if (amount <= 0) {
                    System.out.println("Invalid payment amount. Amount must be greater than zero.");
                    return false;
                }
                System.out.println("Payment method verified.\n" + //
                                            "Processing Credit Card payment...\n" + //
                                            "Payment of "+amount+" successful!");
                return true;
        }
        public String getDisplayName() {
    return "Credit Card ****" + cardNumber.substring(12);
}
       public String getCardNumber() {
    return cardNumber;
}


    }

class UPI implements PaymentMethod{
        private String upiId;

        UPI(String upiId){
            this.upiId = upiId;
        }
        public boolean pay(double amount){
                if (amount <= 0) {
                    System.out.println("Invalid payment amount. Amount must be greater than zero.");
                    return false;
                }
                System.out.println("Payment method verified.\n" + //
                                            "Processing UPI payment...\n" + //
                                            "Payment of "+amount+" successful!");

                return true;
        }
        public String getDisplayName() {
    int atIndex = upiId.indexOf("@");
    String masked = upiId.substring(0, 2) + "***" + upiId.substring(atIndex);
    return "UPI: " + masked;
        }
       public String getUpiId() {
    return upiId;
}

}

class PayPal implements PaymentMethod{
       private String email;

       PayPal(String email){
            this.email = email;
        }
        public boolean pay(double amount){
              if (amount <= 0) {
                    System.out.println("Invalid payment amount. Amount must be greater than zero.");
                    return false;
                }
                System.out.println("Payment method verified.\n" + //
                                            "Processing PayPal payment...\n" + //
                                            "Payment of "+amount+" successful!");
                return true;
                
                                        }
        public String getDisplayName() {
            int atIndex = email.indexOf("@");
    String masked = email.charAt(0) + "***" + email.substring(atIndex);
    return "PayPal: " + masked;
}
      public String getEmail() {
    return email;
}


}
public class Main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      
        List<PaymentRecord> paymentHistory = new ArrayList<>();

        List<PaymentMethod> paymentMethods = new ArrayList<>();
        String cardNumber;
        String cardHolder;
        int choice;
        String upiId;
        String email;
        PaymentMethod payment;
        do{
            System.out.println("========== PAYMENT SYSTEM ==========");

            System.out.println("1. Add Credit Card");
            System.out.println("2. Add UPI");
            System.out.println("3. Add PayPal");
            System.out.println("4. Make Payment");
            System.out.println("5. View Payment History");
            System.out.println("6. Exit");

            System.out.println("Enter your choice");
            choice = sc.nextInt();
            if(choice ==1){
                System.out.println("Enter card number");
                cardNumber = sc.next();
                System.out.println("Enter card holder name");
                sc.nextLine();
                cardHolder = sc.nextLine();
                if (cardNumber.length() !=16 || !cardNumber.matches("\\d+")) {
                    System.out.println("Card number must be 16 digits long and numeric.");
                    continue;
                }
                boolean exists = false;
                for (PaymentMethod pm : paymentMethods) {
                    if (pm instanceof CreditCard && ((CreditCard) pm).getCardNumber().equals(cardNumber)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("This Credit Card is already saved.");
                } else {
                    paymentMethods.add(new CreditCard(cardNumber, cardHolder));
                    System.out.println("Credit Card added successfully!");
                }

                
            }
            else if(choice ==2){
                System.out.println("Enter UPI ID");
                upiId = sc.next();
                
                if (!upiId.contains("@")) {
                    System.out.println("Invalid UPI ID. It must contain '@'.");
                    continue;
                }
                boolean exists = false;
                for (PaymentMethod pm : paymentMethods) {
                    if (pm instanceof UPI && ((UPI) pm).getUpiId().equals(upiId)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("This UPI ID is already saved.");
                } else {
                    paymentMethods.add(new UPI(upiId));
                    System.out.println("UPI added successfully!");
                }

            }
            else if(choice ==3){
                System.out.println("Enter PayPal email");
                email = sc.next();
                if (!email.contains("@")) {
                    System.out.println("Invalid PayPal email. It must contain '@'.");
                    continue;
                }
                boolean exists = false;
                for (PaymentMethod pm : paymentMethods) {
                    if (pm instanceof PayPal && ((PayPal) pm).getEmail().equals(email)) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    System.out.println("This PayPal email is already saved.");
                } else {
                    paymentMethods.add(new PayPal(email));
                    System.out.println("PayPal added successfully!");
                }

                
            }
            else if(choice ==4){

                System.out.println("Select payment method:");
                System.out.println("1. Credit Card");
                System.out.println("2. UPI");
                System.out.println("3. PayPal");
                int paymentChoice = sc.nextInt();
                System.out.println("Enter payment amount");
                sc.nextLine(); // consume the newline

                double amount = sc.nextDouble();
                sc.nextLine(); // consume the newline

                if (paymentChoice == 1){
                    System.out.println("Enter card number");
                    cardNumber = sc.next();
                    boolean found = false;
                    boolean status = false;
                    for (PaymentMethod pm : paymentMethods) {
                        if (pm instanceof CreditCard && ((CreditCard) pm).getCardNumber().equals(cardNumber)) {
                            status=pm.pay(amount);
                            found = true;
                            paymentHistory.add(new PaymentRecord(pm, amount, status));
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Credit Card not found!");
                    }
                }
                else if (paymentChoice == 2){
                    System.out.println("Enter UPI ID");
                    upiId = sc.next();
                    boolean found = false;
                    for (PaymentMethod pm : paymentMethods) {
                        if (pm instanceof UPI && ((UPI) pm).getUpiId().equals(upiId)) {
                            boolean status = pm.pay(amount);
                            found = true;
                            paymentHistory.add(new PaymentRecord(pm, amount, true));
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("UPI not found!");
                    }
                }
                else if (paymentChoice == 3){
                    System.out.println("Enter PayPal email");
                    email = sc.next();
                    boolean found = false;
                    boolean status = false;
                    for (PaymentMethod pm : paymentMethods) {
                        if (pm instanceof PayPal && ((PayPal) pm).getEmail().equals(email)) {
                            status=pm.pay(amount);
                            found = true;
                            paymentHistory.add(new PaymentRecord(pm, amount, status));
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("PayPal not found!");
                    }
                }

            }
            else if(choice ==5){
                System.out.println("Payment History:");
                for (PaymentRecord record : paymentHistory) {
                    System.out.println(record.getSummary());
                }
            }
            else if (choice ==6){
                System.out.println("Exiting...");
            }
            else{
                System.out.println("Invalid choice! Please try again.");
            }


        }while (choice !=6);
        sc.close();
}
}
